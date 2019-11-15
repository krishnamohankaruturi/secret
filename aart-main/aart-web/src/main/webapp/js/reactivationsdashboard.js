var isDistrictFilterRequiredRD;
var isSchoolFilterRequiredRD;

$(function() {
	var levelAccess = $('#accessLevel').val();
	isSchoolFilterRequiredRD = levelAccess >= 70;
	isDistrictFilterRequiredRD = isSchoolFilterRequiredRD || levelAccess >= 50;
	
	var d = new Date();
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#reactivationsDate').text(shortdate);
	
	$('#reactivationsDetail').hide();
	$('#reactivationsDetailExtractButton').hide();
	
	$('#displaySummary').addClass("activeBtn");
	$('#todayReactivations').addClass("activeBtn");
	
	initReactivationsSummaryTable([]);
	initReactivationsDetailTable([]);
	getReactivationsSummary(null, "today");
	getReactivationsDetail(null, "today");
	
	$('#reactivationsSummaryExtractButton').on("click",function(){
		window.open('getReactivationsSummaryCSV.htm?timeframe');
	});
	
	$('#reactivationsDetailExtractButton').on("click",function(){
		window.open('getReactivationsDetailCSV.htm');
	});
});

$(document).delegate('#displayDetail','click',function(event){
	event.preventDefault();
	$('#reactivationsSummary').hide();
	$('#reactivationsSummaryExtractButton').hide();
	$('#reactivationsDetail').show();
	$('#reactivationsDetailExtractButton').show();
	$('#displaySummary').removeClass("activeBtn");
	$('#displayDetail').addClass("activeBtn");
});

$(document).delegate('#displaySummary','click',function(event){
	event.preventDefault();
	$('#reactivationsDetail').hide();
	$('#reactivationsDetailExtractButton').hide();
	$('#reactivationsSummary').show();
	$('#reactivationsSummaryExtractButton').show();
	$('#displayDetail').removeClass("activeBtn");
	$('#displaySummary').addClass("activeBtn");
});

$(document).delegate('#todayReactivations','click',function(event){
	event.preventDefault();
	getReactivationsSummary(null, "today");
	getReactivationsDetail(null, "today");
	var d = new Date();
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#reactivationsDate').text(shortdate);
	$('#yesterdayReactivations').removeClass("activeBtn");
	$('#yearReactivations').removeClass("activeBtn");
	$('#todayReactivations').addClass("activeBtn");
});

$(document).delegate('#yesterdayReactivations','click',function(event){
	event.preventDefault();
	getReactivationsSummary(null, "yesterday");
	getReactivationsDetail(null, "yesterday");
	
	var d = new Date();
	d.setDate(d.getDate()-1);
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#reactivationsDate').text(shortdate);
	$('#todayReactivations').removeClass("activeBtn");
	$('#yearReactivations').removeClass("activeBtn");
	$('#yesterdayReactivations').addClass("activeBtn");
});

$(document).delegate('#yearReactivations','click',function(event){
	event.preventDefault();
	getReactivationsSummary(null, "year");
	getReactivationsDetail(null, "year");
	$('#reactivationsDate').text(" this school year");
	$('#todayReactivations').removeClass("activeBtn");
	$('#yesterdayReactivations').removeClass("activeBtn");
	$('#yearReactivations').addClass("activeBtn");
});

function initReactivationsSummaryTable(tableData) {
	var $reactivationsSummaryGrid= $('#reactivationsSummaryTable');
	var grid_width = $reactivationsSummaryGrid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
   
	var cell_width =grid_width/6;
	var gridParam;
	var csvName = 'ReactivationsSummary';	
        //Added the sortable true explicitly to work correctly in Chrome browser
	var cm=[
	        { name : 'assessmentProgram', index : 'assessmentProgram', label:'reacivateSummary_AssessmentProgram', width : cell_width,	        		
		        		search : true, hidden: false, sortable: true },
	       
	        { name : 'district', index : 'district', label:'reacivateSummary_District', width : cell_width,
	        		search : true, hidden: isDistrictFilterRequiredRD, sortable: true},
	        
	        { name : 'school', index : 'school', label:'reacivateSummary_School', width : cell_width,
		       		search : true, hidden: isSchoolFilterRequiredRD, sortable: true },

		    { name : 'testName', index : 'testName', label:'reacivateSummary_TestName', width : cell_width,
			   		search : true, hidden: false, sortable: true },
		       	
			{ name : 'count', index : 'count', label:'reacivateSummary_Count', width : cell_width,
				   	search : false, hidden: false, formatter:naOrNumberFormatter, sortable: true },
			   		
	        { name : 'reactivatedBy', index : 'reactivatedBy', label:'reacivateSummary_ReactivatedBy', width : cell_width,
				   	search : true, hidden: false, sortable: true }
	];
	
	$reactivationsSummaryGrid.jqGrid({
		datatype: 'local',
		data: tableData,
		search: false,
		width: grid_width,
		shrinktofit:false,
   		colModel : cm,
   		colNames: ["Assessment Program","District","School","Test Name","Count","Reactivated By"],
   		rowNum : 5,
		rowList : [5, 10, 20, 30, 40, 50 ],
		pager : '#pReactivationsSummaryTable',
        viewrecords: true,
        emptyrecords: "No records found",
       	height:'auto',
        altRows : true,
        altclass: 'altrow',
       	columnChooser: true, 
       	multiselect: false,
		footerrow : true,
		userDataOnFooter : true,
		loadonce : false, 
	    beforeSelectRow: function(rowid, e) {
	    	return false;
	    }, loadComplete:function(data){   
	    	 var tableid=$(this).attr('id');    
	    	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');            
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
	    }
	});	
	$reactivationsSummaryGrid.jqGrid('filterToolbar', {searchOperators: false});
	
}
function initReactivationsDetailTable(tableData) {
	var $reactivationsDetailGrid= $('#reactivationsDetailTable');
	var grid_width = $reactivationsDetailGrid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}

	var cell_width =grid_width/7;
	var gridParam;
	var csvName = 'ReactivationsDetail';	

	var cm=[
	    { name : 'assessmentProgram', index : 'assessmentProgram', label:'reacivateDetail_AssessmentProgram', width : cell_width,       		
        		hidden: false },
       
        { name : 'district', index : 'district', label:'reacivateDetail_District', width : cell_width,
        		hidden: isDistrictFilterRequiredRD},
        
        { name : 'school', index : 'school', label:'reacivateDetail_School', width : cell_width,
	       		hidden: isSchoolFilterRequiredRD },

	    { name : 'testName', index : 'testName', label:'reacivateDetail_TestName', width : cell_width,
		   		hidden: false },
	       	
		{ name : 'student', index : 'student', label:'reacivateDetail_Student', width : cell_width,
			   	hidden: false },
		   		
        { name : 'reactivatedBy', index : 'reactivatedBy', label:'reacivateDetail_ReactivatedBy', width : cell_width,
			   	hidden: false },
			   	
		{ name : 'reactivatedDate', index : 'reactivatedDate', label:'reacivateDetail_Date', width : cell_width,
				search : false, hidden: false }
	];
	
	$reactivationsDetailGrid.scb({
		url: 'getReactivationsDetail.htm',
		mtype: 'GET',
		//postData: {timeframe: "today"},
		datatype: 'json',
		jsonReader : {
		     repeatitems: false
		   },
		//data: tableData,
		width: grid_width,
		shrinktofit:false,
   		colModel : cm,
   		colNames: ["Assessment Program","District","School","Test Name","Student","Reactivated By","Date"],
   		rowNum : 5,
		rowList : [5, 10, 20, 30, 40, 50 ],
		pager : '#pReactivationsDetailTable',
        sortname:'assessmentProgram',
        viewrecords: true,
        shrinkToFit: true,
        emptyrecords: "No records found",
       	sortorder: 'asc',  
		height:'auto',
		search: false,
		columnChooser: false, 
        altRows : true,
        altclass: 'altrow',
       	multiselect: false,
		footerrow : true,
		userDataOnFooter : true,
		loadonce : false, 
		beforeSelectRow: function(rowid, e) {
	    	return false;
	    },
	    loadComplete : function () {
	    	$('#reactivationsCount').text(parseInt($('#reactivationsDetailTable').jqGrid('getGridParam', 'records')).toLocaleString('en-US'));
	    	$("#pReactivationsDetailTable").find("table.navtable").remove();
	    	 var tableid=$(this).attr('id');    
	    	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');            
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
	    } 
	});
}

function getReactivationsSummary(orgId, timeframe){
	var params = {};
	if (orgId) {
		params.orgId = orgId;
	}
	if (timeframe) {
		params.timeframe =  timeframe;
	}
	
	$.ajax({
		url: 'getReactivationsSummary.htm',
		data: params,
		dataType: 'json',
		type: 'GET'
	}).done(function(response){
			if (response == null || response === ''){
				response = [];
			}
			setReactivationsSummaryGridData(response.gridData);
			//$('#reactivationsUpdatedDate').text(response.asOfString);
			
			var orgText = '';
			if (response.org.stateName != null && response.org.stateName != '') orgText = "State: " + response.org.stateName;
			if (response.org.districtName != null && response.org.districtName != '') orgText = "District: " + response.org.districtName;
			if (response.org.schoolName != null && response.org.schoolName != '') orgText = "School: " + response.org.schoolName;
			$('#orgLabelReactivations').text(orgText);
		}).always(function(){});
}

function getReactivationsDetail(orgId, timeframe){
	var params = {};
	if (orgId) {
		params.orgId = orgId;
	}
	if (timeframe) {
		params.timeframe = timeframe;
	}
	params.filters = "";
	
	$('#reactivationsDetail input:text').val("")
	//Added true and asc to sort by that column when the user navigates away from dashboard tab and come back again.
	$('#reactivationsDetailTable').jqGrid('setGridParam', { search: false, postData: params }).jqGrid('sortGrid', 'assessmentProgram', true, 'asc').trigger('reloadGrid');
	}

function setReactivationsSummaryGridData(tableData){
	$('#reactivationsSummaryTable').jqGrid("clearGridData").trigger('reloadGrid');
	$('#reactivationsSummaryTable').jqGrid('setGridParam', {data: tableData}).trigger('reloadGrid');
}
