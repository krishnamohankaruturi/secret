var myDefaultSearch = 'cn';
var recordTypeValues;
var messageTypeValues;
var isDistrictFilterRequiredOSH;
var isSchoolFilterRequiredOSH;

$(function() {
	var levelAccess = $('#accessLevel').val();
	isSchoolFilterRequiredOSH = levelAccess >= 70;
	isDistrictFilterRequiredOSH = isSchoolFilterRequiredOSH || levelAccess >= 50;
	
	var d = new Date();
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#outsideDate').text(shortdate);
	
	$('#todayOutside').addClass('activeBtn');
	
	initOutsideHoursTable([]);
	getOutsideHours(null, "today");

	$('#testingOutsideHoursExtractButton').on("click",function(){
		window.open('getTestingOutsideHoursCSV.htm');
	});
});

$(document).delegate('#todayOutside','click',function(event){
	event.preventDefault();
	getOutsideHours(null, "today");
	
	var d = new Date();
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#outsideDate').text(shortdate);
	$('#yesterdayOutside').removeClass('activeBtn');
	$('#yearOutside').removeClass('activeBtn');
	$('#todayOutside').addClass('activeBtn');
});

$(document).delegate('#yesterdayOutside','click',function(event){
	event.preventDefault();
	getOutsideHours(null, "yesterday");
	
	var d = new Date();
	d.setDate(d.getDate()-1);
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#outsideDate').text(shortdate);
	$('#todayOutside').removeClass('activeBtn');
	$('#yearOutside').removeClass('activeBtn');
	$('#yesterdayOutside').addClass('activeBtn');
});

$(document).delegate('#yearOutside','click',function(event){
	event.preventDefault();
	getOutsideHours(null, "year");
	
	$('#outsideDate').text("this school year");
	$('#yesterdayOutside').removeClass('activeBtn');
	$('#todayOutside').removeClass('activeBtn');
	$('#yearOutside').addClass('activeBtn');
});

function initOutsideHoursTable(tableData) {
	var $outsideHoursGrid= $('#outsideHoursTable');
	var grid_width = $outsideHoursGrid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
	var cell_width =grid_width/7;
	var gridParam;
	var csvName = 'OutsideHours';	
        //Added the sortable true explicitly to work correctly in Chrome browser
	var cm=[
	    { name : 'assessmentProgram', index : 'assessmentProgram', label:'Assessment Program', width : cell_width,	        		
        		search : true, sorttype: 'string', hidden: false, sortable:true },
       
        { name : 'district', index : 'district', label:'District', width : cell_width,
        		search : true, sorttype: 'string', hidden: isDistrictFilterRequiredOSH, sortable:true},
        
        { name : 'school', index : 'school', label:'School', width : cell_width,
	       		search : true, sorttype: 'string', hidden: isSchoolFilterRequiredOSH, sortable:true },

	    { name : 'testName', index : 'testName', label:'Test Name', width : cell_width,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
		
		{ name : 'legalLastName', index : 'legalLastName', label:'Legal Last Name', width : cell_width,
			   		search : true, sorttype: 'string', hidden: true, sortable:true },
	     
		{ name : 'legalFirstName', index : 'legalFirstName', label:'Legal First Name', width : cell_width,
				   		search : true, sorttype: 'string', hidden: true, sortable:true },
				   		
		{ name : 'student', index : 'student', label:'Student', width : cell_width,
			   	search : true, sorttype: function(cellValue, rowObject){
			   		return rowObject.legalLastName;
			   	}, hidden: false, sortable:true },
		   		
        { name : 'started', index : 'started', label:'Started', width : cell_width,
			   	search : false, hidden: false, sortable:true },
			   	
		{ name : 'ended', index : 'ended', label:'Ended', width : cell_width,
			   	search : false, hidden: false, sortable:true }
	];
	
	$outsideHoursGrid.jqGrid({
		datatype: 'local',
		data: tableData,
		width: grid_width,
		shrinktofit:false,
   		colModel : cm,
   		colNames:["Assessment Program","District","School","Test Name","Legal Last Name","Legal First Name","Student","Started","Ended"],
   		rowNum : 5,
		rowList : [5, 10, 20, 30, 40, 50 ],
		pager : '#pOutsideHoursTable',
		sortname: 'assessmentProgram',
        viewrecords: true,
        emptyrecords: "No records found",
       	height:'auto',
        altRows : true,
        altclass: 'altrow',
       	columnChooser: true, 
       	multiselect: false,
		footerrow : true,
		userDataOnFooter : true,
		beforeSelectRow: function(rowid, e) {
	    	return false;
	    },
	    loadComplete:function(data){   
	    	 var tableid=$(this).attr('id'); 
	    	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');           
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
	    }
	});	
	$('#outsideHoursTable').jqGrid('filterToolbar', {searchOperators: false});
}


function getOutsideHours(orgId, timeframe){
	var params = {};
	if (orgId) {
		params.orgId = orgId;
	}
	if (timeframe) {
		params. timeframe =  timeframe;
	}
	$.ajax({
		url: 'getTestingOutsideHours.htm',
		data: params,
		dataType: 'json',
		type: 'GET'
	}).done(function(response){
			if (response == null || response === ''){
				response = [];
			}
			setOutsideHoursGridData(response.gridData);
			
			var orgText = '';
			if (response.org.stateName != null && response.org.stateName != '') orgText = "State: " + response.org.stateName;
			if (response.org.districtName != null && response.org.districtName != '') orgText = "District: " + response.org.districtName;
			if (response.org.schoolName != null && response.org.schoolName != '') orgText = "School: " + response.org.schoolName;
			$('#orgLabelOutside').text(orgText);
			if(response.org.testDays == null || response.org.testDays == ''){
				response.org.testDays = 'Mon-Fri';
			}
			if(response.org.testBeginTime == null || response.org.testBeginTime == ''){
				response.org.testBeginTime = '6:00 AM';
			}
			if(response.org.testEndTime == null || response.org.testEndTime == ''){
				response.org.testEndTime = '5:00 PM';
			}
			$('#outsideTime').text(response.org.testDays + ' ' + response.org.testBeginTime + ' - ' + 
					response.org.testEndTime);
		}).always(function(){});
}


function setOutsideHoursGridData(tableData){
	$('#outsideHoursTable').jqGrid("clearGridData", true).trigger('reloadGrid');
	$('#outsideHoursTable').jqGrid('setGridParam', {data: tableData, datatype: "local"}).trigger('reloadGrid');
	
	$('#outsideCount').text(parseInt($('#outsideHoursTable').jqGrid('getGridParam', 'records')).toLocaleString('en-US'));
}
