/**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 */

var myDefaultSearch = 'cn';
var recordTypeValues;
var requestTypeValues;
var isStateFilterRequired;
var isDistrictFilterRequired;
var isSchoolFilterRequired;

$(function() {
	$('#testassignment_schoolOrgsFilter').select2();
	var levelAccess = $('#accessLevel').val();
	isStateFilterRequired = levelAccess < 20;
	isDistrictFilterRequired = isStateFilterRequired || levelAccess < 50;
	isSchoolFilterRequired = isDistrictFilterRequired || levelAccess < 70;
	
	determineOrgFiltersToShow();
	intializeOrgFilters_testassignment();
	initTestAssignmentErrorMsgTabel();	
	
	var $gridAuto = $("#testAssignmentErrorTable");	
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json",
		url : 'getTestAssignmentErrorMessages.htm',
		search: false,
		postData: { "filters": "", "orgId": ""}
	}).jqGrid('sortGrid', 'date', true, 'desc').trigger("reloadGrid");
	initTestAssignmentRecordGrid(); //need to check after page displays......
});

function determineOrgFiltersToShow() {
	if (!isStateFilterRequired){
		$('#stateFilter').hide();
	}
	if (!isDistrictFilterRequired){
		$('#testassignment_districtFilter').hide();
	}
	if (!isSchoolFilterRequired){	
		$('#testassignment_schoolFilter').hide();
		$('#testassignment_searchByOrgBtn').hide();
	}
}

function intializeOrgFilters_testassignment() {
	var select;
	if (isStateFilterRequired){
		select = $('#stateOrgFilter');
	} else if (isDistrictFilterRequired){
		select = $('#testassignment_districtOrgsFilter');
	} else if (isSchoolFilterRequired){
		select = $('#testassignment_schoolOrgsFilter');
	} else {
		return;
	}
	select.select2({
		placeholder:'Select',
		multiple: false,
		allowClear: true
	});
	select.find('option:not(:first)').remove();
	$.ajax({
		url: 'getOrgsForExtractFilters.htm',
		dataType: 'json',
		type: 'GET'
	}).done(function(data) {
			if (data !== undefined && data !== null && data.length > 0){
				populateOrgsSelected(select, data);        		       		        		
        	} else {
        		// error
        	}
		});
}

function populateOrgsSelected($select, orgs){
	for (var i = 0; i < orgs.length; i++){
		$select.append($('<option>', {value: orgs[i].id, text: orgs[i].organizationName}));
	}
	if (orgs.length == 1){
		$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
		$select.trigger('change');
	} else {
		$select.prop('disabled', false);
	}
}

$(document).delegate('#stateOrgFilter','change',function(){
	$('#testassignment_schoolOrgsFilter').find('option:not(:first)').remove();	
	testassignmentPopulateDistricts($(this).val());
});

$(document).delegate('#testassignment_districtOrgsFilter','change',function(){
	testassignmentPopulateSchools($(this).val());
});

function testassignmentPopulateDistricts(stateId){
	var select = $('#testassignment_districtOrgsFilter');
	select.select2({
		placeholder:'Select',
		multiple: false,
		allowClear: true
	});
	select.val('').find('option:not(:first)').remove();
	var url = 'getDistrictsForUser.htm';
	var data = {};
	if (typeof(stateId) != 'undefined'){
		if (stateId == ''){
			return;
		}
		url = 'getDistrictsInState.htm';
		data.stateId = stateId;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET'
	}).done(function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateOrgsSelected(select, data);
			}
		});
}

function isvalid(data) {
	if(data === undefined || data === null || data.length === 0 ){
		return false;
	}
	return true;
}

function testassignmentPopulateSchools(districtId){
	var select = $('#testassignment_schoolOrgsFilter');
	select.select2({
		placeholder:'Select',
		multiple: false,
		allowClear: true
	});
	select.val('').find('option:not(:first)').remove();
	var url = 'getSchoolsForUser.htm';
	var data = {};
	if (typeof(districtId) != 'undefined'){
		if (districtId == ''){
			return;
		}
		url = 'getSchoolsInDistrict.htm';
		data.districtId = districtId;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET'
	}).done(function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateOrgsSelected(select, data);
        	}
		});
}

$(document).delegate('#testassignment_searchByOrgBtn','click',function(event){
	var valid = true;
	event.preventDefault();
	var orgId = "";
	
	if(isDistrictFilterRequired) {
		if(isvalid($('#testassignment_districtOrgsFilter').val())) {
			$('#districtErrorDis').hide();
			orgId = $('#testassignment_districtOrgsFilter').val();
		} else {
			$('#districtErrorDis').show();
			$('#districtErrorDis').empty().append('Please select District.');
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#districtErrorDis").hide(); },3000);
			valid = false;
		}
	}
	
	if(isvalid($('#testassignment_schoolOrgsFilter').val())) {
		orgId = $('#testassignment_schoolOrgsFilter').val();
	}
	
	if(valid) {
		var $gridAuto = $("#testAssignmentErrorTable");
		$gridAuto.jqGrid('setGridParam',{
			datatype:"json",
			url : 'getTestAssignmentErrorMessages.htm',
			search: false,
			postData: { "filters": "", "orgId": orgId}
		}).jqGrid('sortGrid', 'date', true, 'desc').trigger("reloadGrid");
	}
});


function initTestAssignmentErrorMsgTabel() {
	var $grid= $('#testAssignmentErrorTable');
	var grid_width = $grid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
	var cell_width =180;
	var gridParam;
	var csvName = 'TestAssignmentErrorMessages';	
	var cm=[
	        { name : 'id', index : 'id',label:'idDashboard', width : cell_width,
        		search : false, hidden: true, hidedlg : true, viewable:false},
	        { name : 'date', index : 'date',label:'dateDashboard', width : cell_width,	        		
	        		search : false, hidden: false },
	        { name : 'ssid', index : 'ssid',label:'ssidDashboard', width : cell_width, 
					   	 search : true, hidden: false },
			{ name : 'message', index : 'message', label: 'messageDashboard', width : cell_width,
							search : false, hidden: false},
		    { name : 'school', index : 'school',label:'schoolDashboard', width : cell_width, 
						    search : true, hidden: false },
			{ name : 'course', index : 'course', label: 'courseDashboard', width : cell_width,
					search : true, hidden: false},
			{ name : 'calssroomId' , index: 'classroomId', label : 'classroomIdDashboard', width : cell_width,
					search : true, hidden : false },
			{ name : 'educatorId' , index: 'educatorId', label : 'educatorIdDashboard', width : cell_width,
						search : true, hidden : false },
			{ name : 'studentFirstName', index: 'studentFirstName', label : 'studentFirstName' , width: cell_width,
					search: true, hidden: true},
			{ name : 'studentLastName', index: 'studentLastName', label : 'studentLastName' , width: cell_width,
						search : true, hidden : true},
			{ name : 'educatorFirstName', index: 'educatorFirstName', label : 'educatorFirstName' , width: cell_width,
							search : true, hidden : true},
			{ name : 'educatorLastName', index: 'educatorLastName', label : 'educatorLastName' , width: cell_width,
							search : true, hidden : true}
					
	];
	
	$grid.scb({
		
		mtype: "POST",
		datatype : "local",
		dataType: "json",
		width: grid_width,
		filterstatesave:true,
		pagestatesave:true,
		colNames : [
					'ID',
   					'Date', 
   					'SSID',
   					'Message',
   					'School',
   					'Course',
   					'Classroom ID',
   					'Educator ID',
   					'Student First Name',
   					'Student Last Name',
   					'Educator First Name',
   					'Educator Last Name',
   		           ],
   		           
   		colModel : cm,
   		rowNum : 10,
		rowList : [10, 20, 30, 40, 60, 90 ],
		pager : '#pTestAssignmentErrorTable',
        sortname: 'date',
       	sortorder: 'desc',       	
       	columnChooser: true, 
       	multiselect: false,
		footerrow : true,
		userDataOnFooter : true,
		loadonce : false, 
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
		beforeRequest: function() {
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	    },
	    beforeSelectRow: function(rowid, e) {
	    	return true;
	    },
	    loadComplete: function() {    	
	    	$(this).jqGrid('filterToolbar', {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearch});
	    	$grid.find(".ui-icon.fa-download").removeClass("ui-icon");
	    	 var tableid=$(this).attr('id'); 
	    	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');            
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter'); 
                    if ( $(value).is('select')) {
	                	   $(value).removeAttr("role");
	                    };
                    });
	    	
	  }
	});	
	
	$grid.jqGrid('navButtonAdd', 'pTestAssignmentErrorTable', {
		id: 'downloadErrorMsgs',
		caption: '<span class="fa fa-download" style="color:#0E76BC">Download Error Messages</span>',
		title: "Download Messages",
		buttonicon: "none",
		onClickButton: function(ev) {
			ev.preventDefault();
			var orgId = $('#testassignment_districtOrgsFilter').val();
	        $.ajax({
	            method: 'GET',
	            data: {"orgId": orgId},
	            url: 'getAllTestAssignmentErrorMessages.htm'
	        }).done(getErrorMessagesExtract);	
		}
	});
}

function getErrorMessagesExtract(response) {
	getExtractFile(response, 'TestAssignment_Error_Messages.csv');
}


function initTestAssignmentRecordGrid() {
	var $gridAuto = $("#viewTestAssignmentRecordsPopup  #viewTestAssignmentRecordsGrid");
	$("#viewTestAssignmentRecordsPopup  #viewTestAssignmentRecordsGrid").jqGrid('clearGridData');
	$("#viewTestAssignmentRecordsGrid").jqGrid("GridUnload");
	$("#viewTestAssignmentRecordsPopup").jqGrid("GridUnload");
	var gridWidthForVR = 1020;
	var cellWidthForVR = gridWidthForVR/5;
	var cmforViewRosters = [ 
	     {name: 'date', index: 'date', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'ssid', index: 'ssid', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'message', index: 'message', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'school', index: 'school', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'course', index: 'course', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'classroomId', index: 'classroomId', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'educatorId', index: 'educatorId', width:cellWidthForVR, hidden:false, search: false, sort: false}
	];
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		dataType: "json",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
        colNames : ["Date",
   		   			"SSID",
   					"Message",
   					"School",
   					"Course",
   					"Classroom ID",
   					"Educator ID"
   		           ],				
		loadonce: false,	
		grouping: true,
		columnChooser: false,
		gridview: true
	});
}

function getLowestOrg() {
	var orgId;
	if(isDistrictFilterRequired || isvalid($('#testassignment_districtOrgsFilter').val())){
		orgId = $('#testassignment_districtOrgsFilter').val();
	}
	
	if(isvalid($('#testassignment_schoolOrgsFilter').val())) {
		orgId = $('#testassignment_schoolOrgsFilter').val();
	}
	return orgId;
}
