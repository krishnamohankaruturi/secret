$("#stateStudentIdentifier").click(function() {
	//use readonly instead of disabled because disabled elements don't handle click events
	$("#studentName").prop("readonly", true); 
	$("#studentName").addClass("input-disabled");
	$("#studentName").val('');
	$("#stateStudentIdentifier").prop("readonly", false);
	$("#stateStudentIdentifier").removeClass("input-disabled");
});

$("#studentName").click(function() {
	//use readonly instead of disabled because disabled elements don't handle click events
	$("#studentName").prop("readonly", false); 
	$("#studentName").removeClass("input-disabled");
	$("#stateStudentIdentifier").prop("readonly", true);
	$("#stateStudentIdentifier").addClass("input-disabled");
	$("#stateStudentIdentifier").val('');
});

$("#searchForStudent").click(function(e) {
	e.preventDefault();
	var ssid = $("#stateStudentIdentifier").val().trim();
	var name = $("#studentName").val().trim();
	$("#allReportsStudentsByNameGridContainer").addClass("hidden");
	$("#allReportsForStudentSearchError").addClass("hidden");
	$("#allReportsForStudentSearchError").html("");
	$("#kapAllReportsForSSIDGrid").addClass("hidden");
	$("#dlmAllReportsForSSIDGrid").addClass("hidden");
	$("#cpassAllReportsForSSIDGrid").addClass("hidden");
	$("#studentInfo").addClass("hidden");
	if (name.length == 1 & ssid.length == 0) {
		$("#allReportsForStudentSearchError").html($("#enter-two-chars-error").val());
		$("#allReportsForStudentSearchError").removeClass('hidden');
	} else if (name.length == 0 & ssid.length == 0) {
		$("#allReportsForStudentSearchError").html($("#enter-input-error").val());
		$("#allReportsForStudentSearchError").removeClass('hidden')
	} else {
		if (name.length > 1){
			loadStudentsByNameData(name);
		} else if (ssid.length > 0) {
			checkSSIDThenLoadAllStudentReportData(ssid);
		}
	}
	
});

$("#stateStudentIdentifier").keypress(function (e) {
	  if (e.which == 13) {
	    $("#searchForStudent").click();
	    return false;  
	  }
});

$("#studentName").keypress(function (e) {
	  if (e.which == 13) {
	    $("#searchForStudent").click();
	    return false;  
	  }
});

function loadStudentsByNameData(name) {
	hideAllStudentsReportsTab();
	$("#allReportsStudentsByNameGridTableId").jqGrid("GridUnload");
	buildStudentByNameGrid(name);
	$("#allReportsStudentsByNameGridTableId").trigger('reloadGrid');
}

function hideAllStudentsReportsTab() {
	$("#allReportsStudentsByNameGridContainer").addClass("hidden");
	$("#kapAllReportsForSSIDGrid").addClass("hidden");
	$("#dlmAllReportsForSSIDGrid").addClass("hidden");
	$("#cpassAllReportsForSSIDGrid").addClass("hidden");
	$("#studentInfo").addClass('hidden');
	$("#allReportsForStudentSearchError").addClass('hidden');
}

function selectRowFromRadioButton(rowid) {
	$("#allReportsStudentsByNameGridTableId").setSelection(rowid, true);
}

function radioButtonFormatter(cellValue, option, rowObject) {
	return '<input type="radio" name="radio_' + option.gid +' id="radioByName_' + option.rowId + '" onclick="selectRowFromRadioButton(' + option.rowId + ')" title="Select Student"  />';
}

function buildStudentByNameGrid(name) {
	var studentByNameGrid = $("#allReportsStudentsByNameGridTableId");
	var gridWidth = 1025;	
	
	var cmforStudentByNameGrid = [
        { label: 'Select', name : 'select', index : 'select', width : 75, search : false, sortable : false, hidden: false, hidedlg: false, formatter: radioButtonFormatter },	                              
		{ label: 'Last Name', name : 'legalLastName', index : 'legalLastName', width : 150, search : false, sortable : false, hidden: false, hidedlg: false },
		{ label: 'First Name', name : 'legalFirstName', index : 'legalFirstName', width : 150, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Middle Name', name : 'legalMiddleName', index : 'legalMiddleName', width : 150, search : true, sortable : false, hidden : false, hidedlg : false },
		{ label: 'State ID', name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', width : 150, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Current Grade', name : 'gradeName', index : 'gradeName', width : 100, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Current School', name : 'attendanceSchoolName', index : 'attendanceSchoolName', width : 250, search : false, sortable : false, hidden : false, hidedlg : false }
	];

  	studentByNameGrid.jqGrid({
  		url : 'searchByStudentLastName.htm',
		mtype: "GET",
		datatype : "json",
		postData: { "studentLastName" : name, "filters": ""},
		width: gridWidth,
		height: 'auto',
		colNames : [ 'Select', 'Last Name', 'First Name', 'Middle Name', 'State ID', 'Current Grade', 'Current School'],
	  	colModel :cmforStudentByNameGrid,
		rowNum : 10,
		rowList : [ 5, 10, 20, 30, 40, 60, 90 ],
		pager : '#allReportsStudentsByNameGridPager',
		altclass: 'altrow',
		altRows: true,
		loadonce: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    beforeRequest: function() {
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	        $(document.body).css({'cursor' : 'wait'});
	        $("#searchForStudent").css({'cursor' : 'wait'});
	    },
	    loadComplete:function(){
	        var count = $(this).getGridParam('reccount');
			if (count === 0){
				$("#allReportsForStudentSearchError").html($("#report-not-available").val());
				$("#allReportsForStudentSearchError").removeClass('hidden')
			} else {
		    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
		    	$("#allReportsStudentsByNameGridContainer").removeClass("hidden");
			}
			 $(document.body).css({'cursor' : 'default'});
			 $("#searchForStudent").css({'cursor' : 'default'});
	    },
		onSelectRow: function(rowid){
			var rowData = studentByNameGrid.getRowData(rowid);
			var ssid = rowData['stateStudentIdentifier'];  
			checkSSIDThenLoadAllStudentReportData(ssid);
		},
		beforeSelectRow: function (rowid, e) {
		    var radio = $(e.target).closest('tr').find('input[type="radio"]');
		    radio.attr('checked', 'checked');
		    return true;
		}
	});	
}
function checkSSIDThenLoadAllStudentReportData(ssid) {
	hideAllStudentsReportsTab();
	$.ajax({
		url: 'doesSSIDExist.htm',
		dataType: 'json',
		type: "GET",
	    data: { 
	        "stateStudentIdentifier": ssid
	    },
		success: function(data) {
			if (data) {
				loadAllStudentReportData(ssid);
			} else {
				$("#allReportsForStudentSearchError").html($('#ssid-does-not-exist').val());
				$("#allReportsForStudentSearchError").removeClass('hidden');
			}
		}
	});
}

function loadAllStudentReportData(ssid) {
	var count = -1;
	$.ajax({
		url: 'countReportsForStudent.htm',
		dataType: 'json',
		type: "GET",
	    data: { 
	        "stateStudentIdentifier": ssid
	    },
		success: function(data) {
			count = data;
			if (count === 0) {
				$("#allReportsForStudentSearchError").html($('#report-not-available').val());
				$("#allReportsForStudentSearchError").removeClass('hidden');
			} else if (count > 0) {
				$(document.body).css({'cursor' : 'wait'});
				$("#searchForStudent").css({'cursor' : 'wait'});
				loadStudentInfo(ssid);
				$("#kapAllReportsForGridTableId").jqGrid("GridUnload");
				$("#dlmAllReportsForGridTableId").jqGrid("GridUnload");
				$("#cpassAllReportsForGridTableId").jqGrid("GridUnload");
				buildKAPSSIDGrid(ssid);
				buildDLMSSIDGrid(ssid);
				buildCPASSSSIDGrid(ssid);
				$("#kapAllReportsForGridTableId").trigger('reloadGrid');
				$("#dlmAllReportsForGridTableId").trigger('reloadGrid');
				$("#cpassAllReportsForGridTableId").trigger('reloadGrid');
			}
		}
	});
}

function loadStudentInfo(ssid) {
	$.ajax({
		url: 'getStudentInfoForAllStudentsReports.htm',
		dataType: 'json',
		type: "GET",
	    data: { 
	        "stateStudentIdentifier": ssid
	    },
		success: function(student) {
			$("#studentNameAndId").html(student.legalLastName+', '+student.legalFirstName+' (ID: '+student.stateStudentIdentifier+')');
			$("#studentInfo").removeClass('hidden');
		}
	});
}

function kapReportLinkFormatter(cellValue, option, rowObject, ssid) {
	var link = 'N/A';
	if (rowObject[6] !== "N/A") {
		link = '<a title="StudentReport" href="getStudentReportFileForAllStudentReports.htm?id='+option.rowId+'&ssid='+ssid+'"><img src="images/pdf.png" alt="StudentReport" style="height: 25px;width: 25px;"/></a>'; 
	}
		
	return link;
}

function externalReportLinkFormatter(cellValue, option, rowObject, ssid) {
	return '<a title="StudentReport" href="getExternalStudentReportFileForAllStudentReports.htm?id='+option.rowId+'&ssid='+ssid+'"><img src="images/pdf.png" alt="StudentReport" style="height: 25px;width: 25px;"/></a>';
}

function buildKAPSSIDGrid(ssid) {
	var kapSSIDJQGrid = $("#kapAllReportsForGridTableId");
	var gridWidth = 1025;	
	
	var cmforKAPStudentBySSIDGrid = [	                              
		{ label: 'Year', name : 'schoolYear', index : 'schoolYear', width : 75, search : false, sortable : false, hidden: false, hidedlg: false },
		{ label: 'School', name : 'schoolName', index : 'schoolName', width : 300, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Subject', name : 'contentAreaName', index : 'contentAreaName', width : 250, search : true, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Grade', name : 'gradeName', index : 'gradeName', width : 100, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Score', name : 'scaleScore', index : 'scaleScore', width : 100, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Level', name : 'level', index : 'level', width : 100, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Report', name : 'filePath', index : 'filePath', width : 75, search : false, sortable : false, hidden : false, hidedlg : false, 
					formatter: function (cellvalue, options, rowObject) {return kapReportLinkFormatter(cellvalue, options, rowObject, ssid);}}
	];

	kapSSIDJQGrid.jqGrid({
  		url : 'searchByStateStudentIdForKAP.htm',
		mtype: "GET",
		datatype : "json",
		postData: { "stateStudentIdentifier" : ssid},
		width: gridWidth,
		height: 'auto',
		colNames : [ 'Year', 'School', 'Subject', 'Grade', 'Score', 'Level', 'Report'],
	  	colModel :cmforKAPStudentBySSIDGrid,
		rowNum : 100,
		altclass: 'altrow',
		altRows: true,
		loadonce: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    loadComplete: function() {
	    	var count = $(this).getGridParam('reccount');
	    	if (count > 0) {
	    		$("#kapAllReportsForSSIDGrid").removeClass("hidden");
	    		$(document.body).css({'cursor' : 'default'});
	    		$("#searchForStudent").css({'cursor' : 'default'});
	    	}
	    }
	});	
}

function buildDLMSSIDGrid(ssid) {
	var dlmSSIDJQGrid = $("#dlmAllReportsForGridTableId");
	var gridWidth = 1025;	
	
	var cmforDLMStudentBySSIDGrid = [	                              
  		{ label: 'Year', name : 'schoolYear', index : 'schoolYear', width : 100, search : false, sortable : false, hidden: false, hidedlg: false },
		{ label: 'School', name : 'schoolName', index : 'schoolName', width : 300, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Subject', name : 'contentAreaName', index : 'contentAreaName', width : 250, search : true, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Grade', name : 'gradeName', index : 'gradeName', width : 100, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Report', name : 'filePath', index : 'filePath', width : 100, search : false, sortable : false, hidden : false, hidedlg : false, 
			formatter: function (cellvalue, options, rowObject) {return externalReportLinkFormatter(cellvalue, options, rowObject, ssid);}}
	];

	dlmSSIDJQGrid.jqGrid({
  		url : 'searchByStateStudentIdForDLM.htm',
		mtype: "GET",
		datatype : "json",
		postData: { "stateStudentIdentifier" : ssid},
		width: gridWidth,
		height: 'auto',
		colNames : [ 'Year', 'School', 'Subject', 'Grade', 'Report'],
	  	colModel :cmforDLMStudentBySSIDGrid,
		rowNum : 100,
		altclass: 'altrow',
		altRows: true,
		loadonce: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    loadComplete: function() {
	    	var count = $(this).getGridParam('reccount');
	    	if (count > 0) {
	    		$("#dlmAllReportsForSSIDGrid").removeClass("hidden");
	    		$(document.body).css({'cursor' : 'default'});
	    		$("#searchForStudent").css({'cursor' : 'default'});
	    	}
	    }
	});	
}

function buildCPASSSSIDGrid(ssid) {
	var cpassSSIDJQGrid = $("#cpassAllReportsForGridTableId");
	var gridWidth = 1025;		
	
	var cmforCPASSStudentBySSIDGrid = [                              
		{ label: 'Year', name : 'schoolYear', index : 'schoolYear', width : 100, search : false, sortable : false, hidden: false, hidedlg: false },
		{ label: 'School', name : 'schoolName', index : 'schoolName', width : 300, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Subject', name : 'contentAreaName', index : 'contentAreaName', width : 250, search : true, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Grade', name : 'gradeName', index : 'gradeName', width : 100, search : false, sortable : false, hidden : false, hidedlg : false },
		{ label: 'Report', name : 'filePath', index : 'filePath', width : 100, search : false, sortable : false, hidden : false, hidedlg : false, 
			formatter: function (cellvalue, options, rowObject) {return externalReportLinkFormatter(cellvalue, options, rowObject, ssid);}}	
	];

	cpassSSIDJQGrid.jqGrid({
  		url : 'searchByStateStudentIdForCPASS.htm',
		mtype: "GET",
		datatype : "json",
		postData: { "stateStudentIdentifier" : ssid},
		width: gridWidth,
		height: 'auto',
		colNames : [ 'Year', 'School', 'Subject', 'Grade', 'Report'],
	  	colModel :cmforCPASSStudentBySSIDGrid,
		rowNum : 100,
		altclass: 'altrow',
		altRows: true,
		loadonce: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    loadComplete: function() {
	    	var count = $(this).getGridParam('reccount');
	    	if (count > 0) {
	    		$("#cpassAllReportsForSSIDGrid").removeClass("hidden");
	    		$(document.body).css({'cursor' : 'default'});
	    		$("#searchForStudent").css({'cursor' : 'default'});
	    	}
	    }
	});	
}