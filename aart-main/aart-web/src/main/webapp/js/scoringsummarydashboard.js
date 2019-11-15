var scoring_lastRequestedOrgId;

$(function() {
	initScoringSummaryTable([]);
	getScoringSummary(null);
	
	var dialog = $('#scoringContextSwitchPopup').dialog({
		resizable: false,
		width: 600,
		modal: true,
		autoOpen: false,
		title: 'Dashboard Organization Filter',
		create: function(event, ui){
			var widget = $(this).dialog('widget');
		},
		buttons: {
			Ok: function(){
				var val = $('#scoringOrganizationOrgId').val();
				if (val == ''){
					if (accessLevel == '20'){
						$('#scoringSummaryOrgErrorMsg').html('Please select a district to view the scoring summary data.');	
					} else if (accessLevel == '50'){
						$('#scoringSummaryOrgErrorMsg').html('Please select a school to view the scoring summary data.');
					}
				} else {
					getScoringSummary(val);
					$(this).dialog('close');
					$('#backToDefaultOrgForScoringSummary').show();
					$('#scoringSummaryOrgErrorMsg').html('');
				}
			},
			Cancel: function(){
				resetSelects();

				$('#scoringSummaryOrgErrorMsg').html('');

				$(this).dialog('close');			
			}
		},
		open: function(){},

		close: function(){
			$('#scoringSummaryOrgErrorMsg').html('');
		}

	});
	
	var accessLevel = $('#accessLevel').val();
	var userOrg = $('#userOrg').val();
	
	var buttonText = '';
	var backButtonText = 'Back';
	if (accessLevel == '50'){
		buttonText = 'View a school';
		backButtonText = 'Back to district';
	} else if (accessLevel == '20'){
		buttonText = 'View a district';
		backButtonText = 'Back to state';
	}
	
	$('#selectOrgForScoringSummary').val(buttonText).show().on("click",function(){
		var select = $('#scoringOrganizationOrgId');
		if (accessLevel == '20') {
			getDistrictsForOrganization(userOrg, select);
			$('#scoringOrganizationOrgIdLabel').html('District: ');
		} else if (accessLevel == '50'){
			getSchools(userOrg, select);
			$('#scoringOrganizationOrgIdLabel').html('School: ');
		}
		$('#scoringContextSwitchPopup').dialog('open');
	});
	
	$('#backToDefaultOrgForScoringSummary').val(backButtonText).hide().on("click",function(){
		var tableWrap = document.getElementById('scoringSummaryId');
		tableWrap.innerHTML = '';
		tableWrap.classList.add('kite-table', 'table_wrap');
		var defaultTable = document.createElement('table');
	    defaultTable.setAttribute('class', 'responsive');
	    defaultTable.setAttribute('id', 'scoringSummaryTable');
	    document.getElementById('scoringSummaryId').appendChild(defaultTable);

 		initScoringSummaryTable([]);
		getScoringSummary(null);
		$(this).hide();
	});
	
	$('#scoringSummaryExtractButton').on("click",function(){
		var url = 'getScoringSummaryCSV.htm';
		if (scoring_lastRequestedOrgId != null){
			url += '?orgId=' + encodeURIComponent(scoring_lastRequestedOrgId);
		}
		window.open(url);
	});
	
	if (accessLevel > 50){
		$('#selectOrgForScoringSummary').hide().off('click').unbind('click');
		$('#backToDefaultOrgForScoringSummary').hide().off('click').unbind('click');
	}
});

function initScoringSummaryTable(tableData){
	var $scoringGrid = $('#scoringSummaryTable');
	var grid_width = $scoringGrid.parent().width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1030;
	}
	var attrSetting = function (rowId, val, rowObject, cm) {
		var attr = rowObject.attr[cm.name], result;
		if (attr.rowspan) {
			result = ' rowspan="' + attr.rowspan + '"';
		} else if (attr.display) {
			result = ' style="display:' + attr.display + '"';
		}
		return result;
	};
	var currentSchoolYear = $('#currentSchoolYear').val();
	var schoolYearString = (currentSchoolYear - 1) + '-' + currentSchoolYear;
	var cm = [
		{ name : 'rowId', index : 'rowId', label:'rowId', search : false, sortable: false, hidden: true,key:true },
		{ name: 'assessmentProgram', index: 'assessmentProgram', label: 'Assessment Program',
				search: false, sortable: false, hidden: false, classes: 'textalignleft', cellattr: attrSetting },
		{ name: 'contentArea', index: 'contentArea', label: 'Subject',
				search: false, sortable: false, hidden: false, classes: 'textalignleft' },
		{ name: 'countSessionsAssignedThisYear', index: 'countSessionsAssignedThisYear', label: ('School Year<br/>' + schoolYearString),
				search: false, sortable: false, hidden: false, formatter:naOrNumberFormatter },
		{ name: 'countSessionsScoredTodayStr', index: 'countSessionsScoredTodayStr', label: ('Today<br/>' + $('#today').val()),
				search: false, sortable: false, hidden: false, formatter:naOrNumberFormatter },
		{ name: 'countSessionsScoredLastSchoolDay', index: 'countSessionsScoredLastSchoolDay', label: ('Prior Day<br/>' + $('#priorDay').val()),
				search: false, sortable: false, hidden: false, formatter:naOrNumberFormatter },
		{ name: 'countSessionsScoredThisYear', index: 'countSessionsScoredThisYear', label: ('School Year<br/>' + schoolYearString),
				search: false, sortable: false, hidden: false, formatter:naOrNumberFormatter },
		{ name: 'percentCompletedThisYearStr', index: 'percentCompletedThisYearStr', label: ('School Year<br/>' + schoolYearString),
				search: false, sortable: false, hidden: false},
		{ name: 'countSessionsCompletedNotScored', index: 'countSessionsCompletedNotScored', label: ('School Year<br/>' + schoolYearString),
				search: false, sortable: false, hidden: false, formatter:naOrNumberFormatter }
	];

	$scoringGrid.jqGrid({
		datatype: 'local',
		data: tableData,
		width: grid_width,
		colModel: cm,
		multiselect: false,
		loadonce: false,
		height: 'auto',
		altRows: true,
		altclass: 'altrow',
		shrinktofit: false,
		rowNum:1000,
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
	$('#scoringSummaryTable').jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders: [
			{startColumnName: 'countSessionsAssignedThisYear', numberOfColumns: 1, titleText: '<div style="text-align: center;">Sessions Assigned</div>'},
			{startColumnName: 'countSessionsScoredTodayStr', numberOfColumns: 3, titleText: '<div style="text-align: center;">Test Sessions Scored</div>'},
			{startColumnName: 'percentCompletedThisYearStr', numberOfColumns: 1, titleText: '<div style="text-align: center;">Percent Completed</div>'},
			{startColumnName: 'countSessionsCompletedNotScored', numberOfColumns: 1, titleText: '<div style="text-align: center;">Completed Not Scored</div>'}
		]
	});
}

function getScoringSummary(orgId){
	var params = {};
	if (orgId){
		params.orgId = orgId;
	}
	scoring_lastRequestedOrgId = orgId;
	$.ajax({
		url: 'getScoringSummary.htm',
		data: params,
		dataType: 'json',
		type: 'GET'
	}).done(function(response){
			if (response.gridData == null || response.gridData === ''){
				response.gridData = [];
			}
			setScoringSummaryGridData(response.gridData);
			
			var orgText = '';
			if (response.org.stateName != null && response.org.stateName != ''){
				orgText = 'State: ' + response.org.stateName;
			}
			if (response.org.districtName != null && response.org.districtName != ''){
				orgText = 'District: ' + response.org.districtName;
			}
			if (response.org.schoolName != null && response.org.schoolName != ''){
				orgText = 'School: ' + response.org.schoolName;
			}
			$('#scoring_orgLabel').text(orgText);
	});
}

function setScoringSummaryGridData(tableData){
	if (tableData.length > 0){
		$('#scoringSummaryTable').jqGrid('clearGridData');
		$('#scoringSummaryTable').jqGrid('setGridParam', {data: tableData, datatype: "local"}).trigger('reloadGrid');
	}else{
		$('tbody', $('#scoringSummaryTable')).html('<div style="width:300px;padding:10px;font-size:14px">No records found</div>');
	}
}