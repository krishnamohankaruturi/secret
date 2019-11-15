var myDefaultSearch = 'cn';
var recordTypeValues;
var messageTypeValues;
var isStateFilterRequired;
var isDistrictFilterRequired;
var isSchoolFilterRequired;

$(function() {
	$('#schoolOrgsFilter').select2();
	var levelAccess = $('#accessLevel').val();
	isStateFilterRequired = levelAccess < 20;
	isDistrictFilterRequired = isStateFilterRequired || levelAccess < 50;
	isSchoolFilterRequired = isDistrictFilterRequired || levelAccess < 70;
	
	determineOrgFiltersToShow();
	intializeOrgFilters();
	initKidsErrorMsgTabel();
	
	var $gridAuto = $("#kidsErrorTable");	
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json",
		url : 'getKidsErrorMessages.htm',
		search: false,
		postData: { "filters": "", "orgId": "", "stateStudentIdentifier": ""}
	}).trigger("reloadGrid");
	getSelectFilterValues();
	initKidsRecordGrid();
});

function determineOrgFiltersToShow() {
	if (!isStateFilterRequired){
		$('#stateFilter').hide();
	}
	if (!isDistrictFilterRequired){
		$('#districtFilter').hide();
	}
	if (!isSchoolFilterRequired){	
		$('#schoolFilter').hide();
	}
}

function intializeOrgFilters() {
	var select;
	if (isStateFilterRequired){
		select = $('#stateOrgFilter');
	} else if (isDistrictFilterRequired){
		select = $('#districtOrgsFilter');
	} else if (isSchoolFilterRequired){
		select = $('#schoolOrgsFilter');
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
	$('#schoolOrgsFilter').find('option:not(:first)').remove();	
	populateDistricts($(this).val());
});

$(document).delegate('#districtOrgsFilter','change',function(){
	populateSchools($(this).val());
});

function populateDistricts(stateId){
	var select = $('#districtOrgsFilter');
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

function populateSchools(districtId){
	var select = $('#schoolOrgsFilter');
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

$(document).delegate('#searchByssidBtn','click',function(event){
	var ssid = $('#ssid').val();
	event.preventDefault();
	if(isvalid(ssid)) {
		$('#ssidErrorDis').hide();
		var $gridAuto = $("#kidsErrorTable");		
		$gridAuto.jqGrid('setGridParam',{
			datatype:"json",
			url : 'getKidsErrorMessages.htm',
			search: false,
			postData: { "filters": "", "orgId": "", "stateStudentIdentifier": ssid}
		}).trigger("reloadGrid");
		getSelectFilterValues();
	} else {		
		$('#ssidErrorDis').show();
		$('#ssidErrorDis').empty().append('Please eneter State Student Identifer to search.');
		setTimeout("aart.clearMessages()", 3000);
		setTimeout(function(){ $("#ssidErrorDis").hide(); },3000);
		
	}
});


$(document).delegate('#searchByOrgBtn','click',function(event){
	var valid = true;
	event.preventDefault();
	var orgId = "";
	if(isStateFilterRequired) {
		if(isvalid($('#stateOrgFilter').val())) {
			$('#stateErrorDis').hide();
			orgId = $('#stateOrgFilter').val();
		} else {
			$('#stateErrorDis').show();
			$('#stateErrorDis').empty().append('Please select State.');
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#stateErrorDis").hide(); },3000);
			valid = false;
		}
	}
	
	if(isDistrictFilterRequired) {
		if(isvalid($('#districtOrgsFilter').val())) {
			$('#districtKidsErrorDis').hide();
			orgId = $('#districtOrgsFilter').val();
		} else {
			$('#districtKidsErrorDis').show();
			$('#districtKidsErrorDis').empty().append('Please select District.');
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#districtKidsErrorDis").hide(); },3000);
			valid = false;
		}
	}
	
	if(isvalid($('#schoolOrgsFilter').val())) {
		orgId = $('#schoolOrgsFilter').val();
	}
	
	if(valid) {
		var $gridAuto = $("#kidsErrorTable");		
		$gridAuto.jqGrid('setGridParam',{
			datatype:"json",
			url : 'getKidsErrorMessages.htm',
			search: false,
			postData: { "filters": "", "orgId": orgId, "stateStudentIdentifier": ""}
		}).trigger("reloadGrid");
		getSelectFilterValues();
	}
});

function initKidsErrorMsgTabel() {
	var $grid= $('#kidsErrorTable');
	var grid_width = $grid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
	var cell_width =180;
	var gridParam;
	var csvName = 'KidsErrorMessages';	
	getSelectFilterValues();
	var cm=[
	        { name : 'id', index : 'id',label:'idDashboard', width : cell_width,
        		search : false, hidden: true, hidedlg : true, viewable:false },
	        { name : 'processedDate', index : 'processedDate',label:'processedDateDashboard', width : cell_width,	        		
	        		search : false, hidden: false },
	       
	        { name : 'stateStudentIdentifier', index : 'stateStudentIdentifier',label:'stateStudentIdentifierDashboard', width : cell_width,
	        		search : true, hidden: false, formatter: ssidFormatter, unformat: ssidUnFormatter},
	        
	        { name : 'legalFirstName', index : 'legalFirstName',label:'legalFirstNameDashboard', width : cell_width,
		       		search : true, hidden: true },

		    { name : 'legalMiddleName', index : 'legalMiddleName',label:'legalMiddleNameDashboard', width : cell_width,
			   		search : true, hidden: true },
		       		
		    { name : 'legalLastName', index : 'legalLastName',label:'legalLastNameDashboard', width : cell_width,
			   		search : true, hidden: true },
		       	
			{ name : 'dateOfBirth', index : 'dateOfBirth',label:'dateOfBirthDashboard', width : cell_width,
				   	search : false, hidden: true },
			   		
	        { name : 'recordType', index : 'recordType',label:'recordTypeDashboard', width : cell_width, 
				   	stype : 'select', searchoptions: { value : recordTypeValues, sopt:['eq'] },hidden: false },
		        	
		    { name : 'attendanceSchoolName', index : 'attendanceSchoolName',label:'attendanceSchoolNameDashboard', width : cell_width,
		      		search : true, hidden: false },
			       	
			{ name : 'aypSchoolName', index : 'aypSchoolName',label:'aypSchoolNameDashboard', width : cell_width,
			   		search : true, hidden: true },
			
			{ name : 'subjectArea', index : 'subjectArea',label:'subjectAreaDashboard', width : cell_width,
			       	search : true, hidden: false },
			
			{ name : 'currentGradeLevel', index : 'currentGradeLevel',label:'currentGradeLevelDashboard', width : cell_width,
			      	search : true, hidden: true },
				       	
			{ name : 'educatorIdentifier', index : 'educatorIdentifier',label:'educatorIdentifierDashboard', width : cell_width,
			       	search : true, hidden: false},
			
			{ name : 'educatorFirstName', index : 'educatorFirstName',label:'educatorFirstNameDashboard', width : cell_width,
			       	search : true, hidden: true},
				    
			{ name : 'educatorLastName', index : 'educatorLastName',label:'educatorLastNameDashboard', width : cell_width,
			       	search : true, hidden: true},
				    
			{ name : 'messageType', index : 'messageType',label:'messageTypeDashboard', width : cell_width,
			       	stype : 'select', searchoptions: { value : messageTypeValues, sopt:['eq'] }, hidden: false},
					    
			{ name : 'reasons', index : 'reasons',label:'reasonsDashboard', width : cell_width,
					search : false, hidden: false},
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
   					'Processed Date', 
   					'SSID',
   					'Student First Name',
   					'Student Middle Name', 
   					'Student Last Name',
   					'Student Date of Birth',
   					'Record Type',
   					'Attendance School', 
   					'Accountability School',
   					'Subject Area', 
   					'Grade',
   					'Educator ID',
   					'Educator First Name',
   					'Educator Last Name',
   					'Message Type',
   					'Error Message'
   		           ],
   		colModel : cm,
   		rowNum : 10,
		rowList : [10, 20, 30, 40, 60, 90 ],
		pager : '#pkidsErrorTable',
        sortname: 'processedDate',
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
	
	$grid.jqGrid('navButtonAdd', 'pkidsErrorTable', {
		id: 'donwloadKidsErrorMsgs',
		caption: '<span class="fa fa-download" style="color:#0E76BC">Download Error Messages</span>',
		title: "Download Messages",
		buttonicon: "none",
		onClickButton: function(ev) {
			ev.preventDefault();
	        $.ajax({
	            type: 'GET',
	            url: 'getAllErrorMessages.htm'
	        }).done(getErrorMessageExtract);	
		}
	});
}

function getErrorMessageExtract(response) {
	getExtractFile(response, 'Kite_KIDS_Error_Messages.csv');
}

function ssidFormatter(cellvalue, options, rowObject) {    
	return '<a href="javascript:getStudentKidsRecords(\''  + cellvalue +'\');">' + cellvalue + '</a>';   
}

function ssidUnFormatter(cellvalue, options, rowObject) {
	return;
}

function getStateStudentIdentifier() {
	return $('#ssid').val();
}

function getStudentKidsRecords(stateStudentIdentifier) {
	var viewTitle = 'SSID: ' + stateStudentIdentifier;
	$('#viewKidRecordsPopup').dialog({
		autoOpen: false,
		modal: true,
		width: 1060,
		height: 640,
		title: escapeHtml(viewTitle),
		create: function(event, ui) {
		    var widget = $(this).dialog("widget");
		},
		close: function(ev, ui) {
			
		},
		open: function(ev, ui) {							    
			reloadKidsRecordGrid(stateStudentIdentifier);			
		}
	}).dialog('open');
}

function reloadKidsRecordGrid(stateStudentIdentifier) {
	var $gridAuto = $("#viewKidRecordsPopup #viewKidsRecordsGrid");
	$gridAuto.jqGrid('setGridParam',{
		url : 'geRecentKidsRecord.htm?q=1',
		search: false,
		datatype:"json",
		loadonce: false,
		postData: {'stateStudentIdentifier': stateStudentIdentifier}		
	}).trigger("reloadGrid");	
}

function initKidsRecordGrid() {
	var $gridAuto = $("#viewKidRecordsPopup  #viewKidsRecordsGrid");
	$("#viewKidRecordsPopup  #viewKidsRecordsGrid").jqGrid('clearGridData');
	$("#viewKidsRecordsGrid").jqGrid("GridUnload");
	$("#viewKidRecordsPopup").jqGrid("GridUnload");
	var gridWidthForVR = 1020;
	var cellWidthForVR = gridWidthForVR/5;
	var cmforViewRosters = [ 
	     {name: 'createdDate', index: 'createdDate', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'recordType', index: 'recordType', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'attendanceSchoolName', index: 'attendanceSchoolName', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'aypSchoolName', index: 'aypSchoolName', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'subjectArea', index: 'subjectArea', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'educatorIdentifier', index: 'educatorIdentifier', width:cellWidthForVR, hidden:false, search: false, sort: false},
	];
	$gridAuto.scb({
		//url : "getStudentsForRosters.htm?q=1",
		mtype: "POST",
		datatype : "local",
		dataType: "json",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
        colNames : ["Created Date",
   		   			"Record Type",
   					"Attendance School",
   					"Accountability School",
   					"Subject Area",
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
	if(isStateFilterRequired && isvalid($('#stateOrgFilter').val())) {
		orgId = $('#stateOrgFilter').val();
	}
	
	if(isDistrictFilterRequired || isvalid($('#districtOrgsFilter').val())){
		orgId = $('#districtOrgsFilter').val();
	}
	
	if(isvalid($('#schoolOrgsFilter').val())) {
		orgId = $('#schoolOrgsFilter').val();
	}
	return orgId;
}

function getSelectFilterValues() {
	var orgId = getLowestOrg();	
	var stateStudentIdentifier = getStateStudentIdentifier();
	return $.ajax({
        url: 'getSelectDashboardGridFilterData.htm',
        dataType: 'json',
        data: {
        	orgId: orgId,
        	stateStudentIdentifier: stateStudentIdentifier
        },
        type: "GET",
        async: false
    }).done(function(data) {
        	recordTypeValues = ":All";
       	 	for (var i=0; i<data.recordTypes.length; i++) {
       	 		recordTypeValues += ";" + data.recordTypes[i]
       	 			+ ":" + data.recordTypes[i];
       	 	}
       	 	
       	 	messageTypeValues = ":All";
    	 	for (var i=0; i<data.messageTypes.length; i++) {
    	 		messageTypeValues += ";" + data.messageTypes[i] 
    	 			+ ":" + data.messageTypes[i];
    	 	}    	 	    	 	
        });	
}