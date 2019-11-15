var myDefaultSearch = 'cn';
var recordTypeValues;
var requestTypeValues;
var isStateFilterRequired;
var isDistrictFilterRequired;
var isSchoolFilterRequired;

$(function() {
	$('#api_schoolOrgsFilter').select2();
	var levelAccess = $('#accessLevel').val();
	isStateFilterRequired = levelAccess < 20;
	isDistrictFilterRequired = isStateFilterRequired || levelAccess < 50;
	isSchoolFilterRequired = isDistrictFilterRequired || levelAccess < 70;
	
	determineFiltersToShow();
	intializeOrgFilters_api();
	initApiErrorMsgTabel();
	
	var $gridAuto = $("#apiErrorTable");	
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json",
		url : 'getApiErrorMessages.htm',
		search: false,
		postData: { "filters": "", "districtId":"", "schoolId":"","viewOrphanedRecords":""}
	}).jqGrid('sortGrid', 'requestDate', true, 'desc').trigger("reloadGrid");
	getSelectFilterValues();
	initApiRecordGrid();//need to check after page displays......
});

function determineFiltersToShow() {
	if (!isStateFilterRequired){
		$('#stateFilter').hide();
	}
	if (!isDistrictFilterRequired){
		$('#api_districtFilter').hide();
		$('#api_orphanedFilter').hide();
	}
	if (!isSchoolFilterRequired){	
		$('#api_schoolFilter').hide();
		$('#api_orphanedFilter').hide();
		$('#api_searchButton').hide();
	}
}

function intializeOrgFilters_api() {
	var select;
	if (isStateFilterRequired){
		select = $('#api_stateOrgFilter');
	} else if (isDistrictFilterRequired){
		select = $('#api_districtOrgsFilter');
	} else if (isSchoolFilterRequired){
		select = $('#api_schoolOrgsFilter');
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
	$('#api_schoolOrgsFilter').find('option:not(:first)').remove();	
	apiPopulateDistricts($(this).val());
});

$(document).delegate('#api_districtOrgsFilter','change',function(){
	apiPopulateSchools($(this).val());
});

function apiPopulateDistricts(stateId){
	var select = $('#api_districtOrgsFilter');
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

function apiPopulateSchools(districtId){
	var select = $('#api_schoolOrgsFilter');
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


$(document).delegate('#api_searchByOrgBtn','click',function(event){
	var valid = true;
	event.preventDefault();
	var distOrgId = "";
	var schOrgId = "";
	
	if(valid) {
		var $gridAuto = $("#apiErrorTable");
		var checked = null;
		distOrgId = $('#api_districtOrgsFilter').val();
		schOrgId = $('#api_schoolOrgsFilter').val();
		
		if($("#viewOrphanedRecords").is(":checked")){
			checked = "checked";
		}
		$gridAuto.jqGrid('setGridParam',{
			datatype:"json",
			url : 'getApiErrorMessages.htm',
			search: false,
			postData: { "filters": "", "districtId": distOrgId, "schoolId": schOrgId,"viewOrphanedRecords":checked}
		}).jqGrid('sortGrid', 'requestDate', true, 'desc').trigger("reloadGrid");
		getSelectFilterValues();
	}
});


function initApiErrorMsgTabel() {
	var $grid= $('#apiErrorTable');
	var grid_width = $grid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
	var cell_width =180;
	var gridParam;
	var csvName = 'ApiErrorMessages';	
	getSelectFilterValues();
	var cm=[
	        { name : 'id', index : 'id',label:'idDashboard', width : cell_width,
        		search : false, hidden: true, hidedlg : true, sortable : true, viewable:false},
	        { name : 'requestDate', index : 'requestDate',label:'requestDateDashboard', width : cell_width,	        		
	        		search : false, sortable : true, hidden: false },
	        { name : 'recordType', index : 'recordType',label:'recordTypeDashboard', width : cell_width, 
					   	stype : 'select', searchoptions: { value : recordTypeValues, sopt:['eq'] },sortable : true, hidden: false },
			{ name : 'errorMessage', index : 'errorMessage', label: 'errorMessageDashboard', width : cell_width,
							search : true, sortable : true, hidden: false},
		    { name : 'requestType', index : 'requestType',label:'requestTypeDashboard', width : cell_width, 
							   	stype : 'select', searchoptions: { value : requestTypeValues, sopt:['eq'] }, sortable : true, hidden: false },
			{ name : 'name', index : 'name', label: 'nameDashboard', width : cell_width,
					search : true, sortable : true, hidden: false},
			{ name : 'calssroomId' , index: 'classroomId', label : 'classroomIdDashboard', width : cell_width,
					search : true, sortable : true, hidden : false },
			{ name : 'districtName', index: 'districtName', label : 'districtName' , width: cell_width,
					search: true, sortable : true, hidden: false},
			{ name : 'schoolName', index: 'schoolName', label : 'schoolName' , width: cell_width,
						search : true, sortable : true, hidden : false},
			{ name : 'stateName', index: 'stateName', label : 'stateName' , width: cell_width,
							search : true, sortable : true, hidden : false}
			
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
   					'Requested', 
   					'Record Type',
   					'Error Message',
   					'Request Type',
   					'Name',
   					'Classroom ID',
   					'District Name',
   					'School Name',
   					'State Name'
   		           ],
   		colModel : cm,
   		rowNum : 10,
		rowList : [10, 20, 30, 40, 60, 90 ],
		pager : '#pApiErrorTable',
        //sortname: 'requestDate',
       	//sortorder: 'desc',       	
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
	
	$grid.jqGrid('navButtonAdd', 'pApiErrorTable', {
		id: 'donwloadApiErrorMsgs',
		caption: '<span class="fa fa-download" style="color:#0E76BC">Download Error Messages</span>',
		title: "Download Messages",
		buttonicon: "none",
		
		onClickButton: function(ev) {
			
			var distOrgId = $('#api_districtOrgsFilter').val();
			var schOrgId = $('#api_schoolOrgsFilter').val();
			var checked = null;
			
			if($("#viewOrphanedRecords").is(":checked")){
				checked = "checked";
			}
			ev.preventDefault();
	        $.ajax({
	            method: 'GET',
	            data: {"filters": "", "districtId": distOrgId, "schoolId": schOrgId,"viewOrphanedRecords":checked},
	            url: 'getAllApiErrorMessages.htm'
	        }).done(getErrorMessageExtract);	
		}
	});
}

function getErrorMessageExtract(response) {
	getExtractFile(response, 'API_Error_Messages.csv');
}


function initApiRecordGrid() {
	var $gridAuto = $("#viewApiRecordsPopup  #viewApiRecordsGrid");
	$("#viewApiRecordsPopup  #viewApiRecordsGrid").jqGrid('clearGridData');
	$("#viewApiRecordsGrid").jqGrid("GridUnload");
	$("#viewApiRecordsPopup").jqGrid("GridUnload");
	var gridWidthForVR = 1020;
	var cellWidthForVR = gridWidthForVR/5;
	var cmforViewRosters = [ 
	     {name: 'requestDate', index: 'requestDate', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'recordType', index: 'recordType', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'errorMessage', index: 'errorMessage', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'requestType', index: 'requestType', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'name', index: 'name', width:cellWidthForVR, hidden:false, search: false, sort: false},
	     {name: 'classroomId', index: 'classroomId', width:cellWidthForVR, hidden:false, search: false, sort: false},
	];
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		dataType: "json",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
        colNames : ["Requested",
   		   			"Record Type",
   					"Error Message",
   					"Request Type",
   					"Name",
   					"classroom ID"
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
	
	if(isDistrictFilterRequired || isvalid($('#api_districtOrgsFilter').val())){
		orgId = $('#api_districtOrgsFilter').val();
	}
	
	if(isvalid($('#api_schoolOrgsFilter').val())) {
		orgId = $('#api_schoolOrgsFilter').val();
	}
	return orgId;
}

function getSelectFilterValues() {
	var orgId = getLowestOrg();	
	return $.ajax({
        url: 'getApiErrorsGridFilterData.htm',
        dataType: 'json',
        type: "GET",
        async: false
    }).done(function(data) {
        	recordTypeValues = ":All";
       	 	for (var i=0; i<data.recordTypes.length; i++) {
       	 		recordTypeValues += ";" + data.recordTypes[i]
       	 			+ ":" + data.recordTypes[i];
       	 	}
       	 	
       	 	requestTypeValues = ":All";
    	 	for (var i=0; i<data.requestTypes.length; i++) {
    	 		requestTypeValues += ";" + data.requestTypes[i] 
    	 			+ ":" + data.requestTypes[i];
    	 	}    	 	    	 	
        });	
}