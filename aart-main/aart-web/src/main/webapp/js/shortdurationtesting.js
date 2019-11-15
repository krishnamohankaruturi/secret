var myDefaultSearch = 'cn';
var recordTypeValues;
var messageTypeValues;
var isDistrictFilterRequiredSDT;
var isSchoolFilterRequiredSDT;

$(function() {
	var levelAccess = $('#accessLevel').val();
	isSchoolFilterRequiredSDT = levelAccess >= 70;
	isDistrictFilterRequiredSDT = isSchoolFilterRequiredSDT || levelAccess >= 50;
	
	var d = new Date();
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#shortDurationDate').text(shortdate);
	
	$('#todayShortDuration').addClass('activeBtn');
	
	initShortDurationTable([]);
	getShortDuration(null, "today");

	$('#testingshortDurationExtractButton').on("click",function(){
		window.open('getShortDurationTestingCSV.htm');
	});
});

$(document).delegate('#todayShortDuration','click',function(event){
	event.preventDefault();
	getShortDuration(null, "today");
	
	var d = new Date();
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#shortDurationDate').text(shortdate);
	$('#yesterdayShortDuration').removeClass('activeBtn');
	$('#yearShortDuration').removeClass('activeBtn');
	$('#todayShortDuration').addClass('activeBtn');
});

$(document).delegate('#yesterdayShortDuration','click',function(event){
	event.preventDefault();
	getShortDuration(null, "yesterday");
	
	var d = new Date();
	d.setDate(d.getDate()-1);
	var days = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	var weekday = days[d.getDay()];
	var month = d.getMonth() + 1;
	var calday = d.getDate();
	var shortdate =  weekday + " " + month + "/" + calday;
	$('#shortDurationDate').text(shortdate);
	$('#todayShortDuration').removeClass('activeBtn');
	$('#yearShortDuration').removeClass('activeBtn');
	$('#yesterdayShortDuration').addClass('activeBtn');
});

$(document).delegate('#yearShortDuration','click',function(event){
	event.preventDefault();
	getShortDuration(null, "year");
	
	$('#shortDurationDate').text("this school year");
	$('#yesterdayShortDuration').removeClass('activeBtn');
	$('#todayShortDuration').removeClass('activeBtn');
	$('#yearShortDuration').addClass('activeBtn');
});

function initShortDurationTable(tableData) {
	var $shortDurationGrid= $('#shortDurationTable');
	var grid_width = $shortDurationGrid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
	var cell_width = grid_width/10;
	var gridParam;
	var csvName = 'ShortDuration';	
	var cm=[
	    { name : 'assessmentProgram', index : 'assessmentProgram', label:'Assessment Program', width : cell_width*1.5,	        		
        		search : true, sorttype: 'string', hidden: false, sortable:true },
       
        { name : 'district', index : 'district', label:'District', width : cell_width,
        		search : true, sorttype: 'string', hidden: isDistrictFilterRequiredSDT, sortable:true},
        
        { name : 'school', index : 'school', label:'School', width : cell_width,
	       		search : true, sorttype: 'string', hidden: isSchoolFilterRequiredSDT, sortable:true },
	       		
	    { name : 'teacher', index : 'teacher', label:'Teacher', width : cell_width,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
		   		
		{ name : 'subject', index : 'subject', label:'Subject', width : cell_width,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
			   		
		{ name : 'grade', index : 'grade', label:'Grade', width : cell_width,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
		   		
		{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', label:'State Student Identifier', width : cell_width,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
		
		{ name : 'studentLastName', index : 'studentLastName', label:'Student Last Name', width : cell_width,
			   		search : true, sorttype: 'string', hidden: true, sortable:true },
	     
		{ name : 'studentFirstName', index : 'studentFirstName', label:'Student First Name', width : cell_width,
				   		search : true, sorttype: 'string', hidden: true, sortable:true },
				   		
		{ name : 'student', index : 'student', label:'Student', width : cell_width,
			   	search : true, sorttype: function(cellValue, rowObject){
			   		return rowObject.studentLastName;
			   	}, hidden: false, sortable:true },
			   	
		{ name : 'testName', index : 'testName', label:'Test Name', width : cell_width*1.5,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
		   		
   		{ name : 'itemCount', index : 'itemCount', label:'Item Count', width : cell_width/1.5,
		   		search : false, sorttype: 'string', hidden: false, sortable:true },
		   		
		{ name : 'allCorrectIndicator', index : 'allCorrectIndicator', label:'All Correct', width : cell_width/1.5,
		   		search : true, sorttype: 'string', hidden: false, sortable:true },
			   		
		{ name : 'testTimeSpan', index : 'testTimeSpan', label:'Timespan', width : cell_width,
		   		search : false, hidden: false, sortable:false },
		   		
        { name : 'startedDate', index : 'startedDate', label:'Started', width : cell_width*2,
			   	search : false, hidden: false, sortable:true },
			   	
		{ name : 'endedDate', index : 'endedDate', label:'Ended', width : cell_width*2,
			   	search : false, hidden: false, sortable:true }
	];
	
	$shortDurationGrid.jqGrid({
		datatype: 'local',
		data: tableData,
		width: grid_width,
		shrinkToFit : false,
   		colModel : cm,
   		colNames:["Assessment Program","District","School","Teachers","Subject","Grade","State Student Identifier","Student Last Name","Student First Name","Student","Test Name","Item Count","All Correct","Timespan","Started","Ended"],
   		rowNum : 5,
		rowList : [5, 10, 20, 30, 40, 50 ],
		pager : '#pShortDurationTable',
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
	$('#shortDurationTable').jqGrid('filterToolbar', {searchOperators: false});
}


function getShortDuration(orgId, timeframe){
	var params = {};
	if (orgId) {
		params.orgId = orgId;
	}
	if (timeframe) {
		params. timeframe =  timeframe;
	}
	$.ajax({
		url: 'getShortDurationTesting.htm',
		data: params,
		dataType: 'json',
		type: 'GET'
	}).done(function(response){
			if (response == null || response == undefined || response === ''){
				response = [];
			}
			setShortDurationGridData(response.gridData);
			
			var orgText = '';
			if (response.org.stateName != null && response.org.stateName != '') orgText = "State: " + response.org.stateName;
			if (response.org.districtName != null && response.org.districtName != '') orgText = "District: " + response.org.districtName;
			if (response.org.schoolName != null && response.org.schoolName != '') orgText = "School: " + response.org.schoolName;
			$('#orgLabelShortDuration').text(orgText);
	});
}


function setShortDurationGridData(tableData){
	$('#shortDurationTable').jqGrid("clearGridData", true).trigger('reloadGrid');
	$('#shortDurationTable').jqGrid('setGridParam', {data: tableData, datatype: "local"}).trigger('reloadGrid');
	
	$('#shortDurationCount').text(parseInt($('#shortDurationTable').jqGrid('getGridParam', 'records')).toLocaleString('en-US'));
}
