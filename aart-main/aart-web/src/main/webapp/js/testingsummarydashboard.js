var myDefaultSearch = 'cn';
var recordTypeValues;
var messageTypeValues;

var orgName;
var accessLevel;
var userOrg;
var orgType;
var stateId;
var id;
var currentSchoolYear;
var schoolYearString;
var todayString;
var priorDayString;
var currentAssessment;
var asOfval;
var userStateIds=[];
var clearHtml =  false;
var getAllStateIndicator = false;

$(function() {
	accessLevel = $('#accessLevel').val();
	userOrg = $('#userOrg').val();
	orgType = $('#orgType').val();
	orgName = $('#userOrgName').val();
	stateId = $('#stateId').val();
	
	//Added for getting multiple states details
	currentSchoolYear = $('#currentSchoolYear').val();
	schoolYearString = (currentSchoolYear - 1) + '-' + currentSchoolYear;
	todayString = $('#today').val();
	priorDayString = $('#priorDay').val();
	currentAssessment = $('#currentAssessmentProgram').val();
	asOfval = $('#asOfString').val();
	if ( accessLevel =='20'){
		userStateIds = getLoggedInUserStateDetails();
	}
	// Added for PLTW check
	apName = $('#userAPName').val();
	
	switchButtonContext();
	
	//View the button only for State level user and for DML and PLTW.
	if ( accessLevel !='20'){
		 $('#selectMultiOrgForTestingSummary').hide();
	}
	else if ((apName != 'DLM') && (apName != 'PLTW')){
		$('#selectMultiOrgForTestingSummary').hide();
	}
	$('#multipleExtract').hide();
	
	$.ajax({
		url: 'getTestingSummary.htm',
		data: {
		},
		dataType: 'json',
		type: "GET"
		}).done(function(response) {
			initTestingSummaryTable(response.gridData);
			setOrgName(response.org);
    });	 
	var dialog = $('#testingContextSwitchPopup').dialog({
		resizable: false,
		width: 600,
		modal: true,
		autoOpen: false,
		title: 'Dashboard Organization Filter',
		create: function(event, ui){
			var widget = $(this).dialog("widget");
		},
		buttons: {
			
			"Ok" : {
		         text: "OK",
		         id: "okbtnid",
		         click: function(){
						var params = {
								districtId: $('#organizationDistrictId').val(),
								schoolId: $('#organizationSchoolId').val()
							};
							if (accessLevel=='20' &  params.districtId == ''){
								$('#testingSummaryOrgErrorMsg').html('Please select a district to view the testing summary data.');
							} else if (accessLevel=='50' & params.schoolId == ''){
								$('#testingSummaryOrgErrorMsg').html('Please select a school to view the testing summary data.');
							} else {
								loadTestingSummaryTableWithContext(stateId, params.districtId, params.schoolId);
								clearHtml = true;
								$('#selectedStateId').val(stateId);
								$('#selectedDistrictId').val(params.districtId);
								$('#selectedSchoolId').val(params.schoolId);
								resetSelects();
								$(this).dialog('close');
								$('#backToDefaultOrgForTestingSummary').show();
								$('#testingSummaryOrgErrorMsg').html('');
							}
						}   
		      } ,
		      "Cancel" : {
			         text: "Cancel",
			         id: "cancelbtnid",
			         click: function(){
			        	 $('#okbtnid').removeClass('ui-state-focus');
							resetSelects();
							$('#testingSummaryOrgErrorMsg').html('');
							$(this).dialog('close');		
						}   
			      }
		},
		open: function(){
			$('#okbtnid').removeClass('ui-state-focus');
			$('#organizationFilterDropdowns').show();
		},
		close: function(){
			$('#okbtnid').removeClass('ui-state-focus');
			$('#testingSummaryOrgErrorMsg').html('');
			$('#organizationFilterDropdowns').hide();
			
		}
	});
				
	$('#selectOrgForTestingSummary').on("click",function(){
		 $('#testingContextSwitchPopup').dialog("open");
		 if ( accessLevel =='20') {
		 	getDistrictsForOrganization(userOrg, $('#organizationDistrictId'));
		 	$('#organizationSchoolFilter').hide();
		 } else if (accessLevel=='50'){
			 $('#organizationDistrictFilter').hide();
			 getSchools(userOrg, $('#organizationSchoolId'));
		 }
	 });		
	
	//Added for Multiple States view button
	$('#selectMultiOrgForTestingSummary').on("click",function(){
		$('#backToDefaultOrgForTestingSummary').show();
		$('#selectMultiOrgForTestingSummary').hide();
		$('#selectOrgForTestingSummary').hide();
		$('#testingSummaryExtractButton').hide();
		$('#multipleExtract').show();
		if(clearHtml == true){
			document.getElementById('headers').innerHTML = '';			
		}
		loadTestingSummaryTableWithMultiContext(userStateIds);
	 });
	//End
	$('#backToDefaultOrgForTestingSummary').hide();
	$('#backToDefaultOrgForTestingSummary').on("click",function(){
		$('#selectOrgForTestingSummary').show();
		$('#testingSummaryExtractButton').show();
		$('#backToDefaultOrgForTestingSummary').hide();
		$('#multipleExtract').hide();
		if(accessLevel =='20'){
			$('#selectMultiOrgForTestingSummary').show();
		}
		loadTestingSummaryTableWithDefaultContext(clearHtml);
		clearHtml =  false;
		
	 });		
	$('#organizationDistrictId').on("change",function(){
		getSchools($(this).val(), $('#organizationSchoolId'));
	});	 
	$('#testingSummaryExtractButton').on("click",function(){
		var stateid = $('#selectedStateId').val();
		var districtid = $('#selectedDistrictId').val();
		var schoolid = $('#selectedSchoolId').val();
		if (stateid !== ''){
			stateid = encodeURIComponent(stateid);
		}
		if (districtid !== ''){
			districtid = encodeURIComponent(districtid);
		}
		if (schoolid !== ''){
			schoolid = encodeURIComponent(schoolid);
		}
		//Added an extra parameter getAllStateIndicator here
		getAllStateIndicator = false;
		window.open('getTestingSummaryCSV.htm?stateOrgId=' + stateid 
				+ '&districtOrgId=' + districtid 
				+ '&schoolOrgId=' + schoolid + '&getAllStates=' + getAllStateIndicator);
	});
	
	$('#multipleExtract').on("click",function(){
		//Added an extra parameter getAllStateIndicator here
		getAllStateIndicator = true;
		window.open('getTestingSummaryCSV.htm?getAllStates=' + getAllStateIndicator);
	});
});

function initTestingSummaryTable(response, id) {
	//Added for showing multiple Table structures
	if (id == null){
		id = 'testingSummaryTable';
	}
	var $testingGrid= $('#' + id);
	//End
	var grid_width = $('#testingSummaryTable').parent().width();
	if(grid_width == 100 || grid_width == 0 || grid_width == null) {
		grid_width = 1030;
	}
	var column_width = grid_width/10;
	var data = response;
	var gridParam;
	var csvName = 'TestingSummary';	
	
	//Added for PLTW requirement
	var classroomHidden = true;
	var subOrcourse = 'Subject';
		
		if (apName == 'PLTW' ) {
			subOrcourse = 'Course';
		if (accessLevel !='20')
			classroomHidden = false;
		}
	//End	
	var attrSetting = function (rowId, val, rawObject, cm) {
        var attr = rawObject.attr[cm.name], result;
        if (attr.rowspan) {
            result = ' rowspan=' + '"' + attr.rowspan + '"';
        } else if (attr.display) {
            result = ' style="display:' + attr.display + '"';
        }
        return result;
    };
    

    if(!classroomHidden){
   	 var attrSetting1 = function (rowId, val, rawObject, cm) {
   	        var courseAttr = rawObject.courseAttr[cm.name], result;
   	        if (courseAttr.rowspan) {
   	            result = ' rowspan=' + '"' + courseAttr.rowspan + '"';
   	        } else if (courseAttr.display) {
   	            result = ' style="display:' + courseAttr.display + '"';
   	        }
   	        return result;
   	    };
   }
    
	var currentSchoolYear = $('#currentSchoolYear').val();
	var schoolYearString = (currentSchoolYear - 1) + '-' + currentSchoolYear;
	var todayString = $('#today').val();
	var priorDayString = $('#priorDay').val();
	var cm=[
			{ name : 'rowId', index : 'rowId', label:'rowId', search : false, sortable: false, hidden: true,key:true },
	        
			{ name : 'assessmentProgram', index : 'assessmentProgram', label:'Assessment Program',	        		
	        		search : false, sortable: false, hidden: false, classes:'textalignleft', cellattr: attrSetting },

	        { name : 'subject', label: $('#currentAssessmentProgram').val()=="PLTW" ? 'Course' : 'Subject', index : 'subject', label:subOrcourse,
	        		search : false, sortable: false, hidden: false, classes:'textalignleft', cellattr: attrSetting1 },
	        		
	        { name : 'classroomId', index : 'classroomId', label:'Classroom ID',
				    search : false, sortable: false, hidden: classroomHidden },

	        
	        { name : 'todayCompleted', index : 'todayCompleted', label:'Today<br/>'+todayString,
		       		search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter },

		    { name : 'priorDayCompleted', index : 'priorDayCompleted', label:'Prior Day<br/>'+priorDayString,
			   		search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter  },
		       		
		    { name : 'yearCompleted', index : 'yearCompleted', label:'School Year<br/>' + schoolYearString,
			   		search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter  },
			   		
			{ name : 'yearStudentsAssigned', index : 'yearStudentsAssigned', label:'School Year<br/>' + schoolYearString,
					   	search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter  },			   		
		       	
			{ name : 'yearStudentsCompleted', index : 'yearStudentsCompleted', label:'School Year<br/>' + schoolYearString,
				   	search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter },
				   	
			{ name : 'studentsPercentCompleted', index : 'studentsPercentCompleted', label:'School Year<br/>' + schoolYearString,
				   	search : false, sortable: false, hidden: false },
			   		
	        { name : 'priorDayReactivations', index : 'priorDayReactivations', label:'Prior Day<br/>'+priorDayString, 
				   	search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter  },
		        	
		    { name : 'yearReactivations', index : 'yearReactivations', label:'School Year<br/>' + schoolYearString,
				   	search : false, sortable: false, hidden: false, formatter:naOrNumberFormatter  }
	];

	$testingGrid.jqGrid({
		mtype: "GET",	
		datatype : "local",
		data: data,
		width: grid_width,
   		colModel : cm,       	 
       	multiselect: false,
		loadonce : false,
		height:'auto',
        altRows : true,
        altclass: 'altrow',
        shrinktofit: true,
        pager:false,
        rowNum:1000,
	    beforeSelectRow: function(rowid, e) {
	    	return true;
	    },
	    loadComplete: function() {
            if ($testingGrid.getGridParam('records') === 0) {
                $('#testingSummaryTable tbody').html("<div style='width:300px;padding:10px;font-size:14px'>No records found</div>");
            }
         }
	});	
	$testingGrid.jqGrid('setGroupHeaders', {
		  useColSpanStyle: true, 
		  groupHeaders:
			  		[
		            	{startColumnName: 'todayCompleted', numberOfColumns: 3, titleText: '<div style="text-align: center;font-size: 16px; color:white">Test Sessions Completed</div>'},
		            	{startColumnName: 'yearStudentsAssigned', numberOfColumns: 1, titleText: '<div style="text-align: center;font-size: 16px;color:white">Students Assigned</div>'},
		            	{startColumnName: 'yearStudentsCompleted', numberOfColumns: 1, titleText: '<div style="text-align: center;font-size: 16px; color:white">Students All Sessions Complete</div>'},
		            	{startColumnName: 'studentsPercentCompleted', numberOfColumns: 1, titleText: '<div style="text-align: center;font-size: 16px; color:white">Students Percent Complete</div>'},
		            	{startColumnName: 'priorDayReactivations', numberOfColumns: 2, titleText: '<div style="text-align: center;font-size: 16px; color:white">Test Sessions Reactivated</div>'}
		            ]
		});
	
	//Added to populate the grid data of all states
	if(id != 'testingSummaryTable'){
		if ($testingGrid.getGridParam('records') === 0){
			$testingGrid.html("<div style='width:300px;padding:10px;font-size:14px;'>No records found</div>");
            
		}
		if (response.gridData != undefined && response.gridData.length > 0){
			$testingGrid.jqGrid('clearGridData').trigger('reloadGrid');
			$testingGrid.jqGrid('setGridParam', {data: response.gridData, datatype: 'local'});
			$testingGrid.trigger('reloadGrid');
		}
	}
}

function loadTestingSummaryTableWithDefaultContext(clearHtml){
	$.ajax({
			url: 'getTestingSummary.htm',
			data: {
			},
			dataType: 'json',
			type: "GET"
	}).done(function(response) {
				if(clearHtml == true){
					document.getElementById('headers').innerHTML = '';		
					document.getElementById('testingSummaryId').innerHTML = '';
					setHeaders();
					setTableData(response); 
				}
				if (response.gridData.length == 0 || response.gridData.length == undefined){
					$('#testingSummaryTable tbody').html("<div style='width:300px;padding:10px;font-size:14px'>No records found</div>");
				}
				setOrgName(response.org);
				$('#selectedStateId').val('');
				$('#selectedDistrictId').val('');
				$('#selectedSchoolId').val('');
	});	 
}

function loadTestingSummaryTableWithContext(stateid, districtid, schoolid){
	var orgData = {
			stateOrgId:stateid
	};
	if (districtid != null){
		if (districtid != ''){
			orgData.districtOrgId = districtid;
		}else{
			orgData.districtOrgId = userOrg;
		}
	}
	if (schoolid != null){
		orgData.schoolOrgId = schoolid;
	}
	$.ajax({
			url: 'getTestingSummary.htm',
			data: orgData,
			dataType: 'json',
			type: "GET"
	}).done(function(response) {
				if(clearHtml == true){
					document.getElementById('headers').innerHTML = '';
					document.getElementById('testingSummaryId').innerHTML = '';		
					setHeaders();
					setTableData(response);				    
				}
				if (response.gridData.length == 0 || response.gridData.length == undefined){
					$('#testingSummaryTable tbody').html("<div style='width:300px;padding:10px;font-size:14px'>No records found</div>");
				}
				setOrgName(response.org);
	  });
}

//New Function added to get all state details
function loadTestingSummaryTableWithMultiContext(userStateIds){
	document.getElementById('headers').innerHTML = '';
	document.getElementById('testingSummaryId').innerHTML = '';
	for(var i=0; i<userStateIds.length; i++){
		var orgData = {
				stateOrgId:userStateIds[i]
		};
		$.ajax({
				async: false,
				url: 'getTestingSummary.htm',
				data: orgData,
				dataType: 'json',
				type: "GET"
		 }).done(function(response) {
			var n = i.toString();
			var orgl = document.createElement('div');
			orgl.setAttribute('id', 'orgLabel' + n);
		    orgl.textContent = 'State: ' + response.org.stateName;
		    orgl.style.cssFloat = "left";

		    var asOff = document.createElement('div');
		    asOff.setAttribute('id', 'asOff' + n);
		    asOff.style.fontSize = "1rem";
		    asOff.style.cssFloat = 'right';
		    asOff.style.marginRight = "10px";
		    asOff.textContent = 'As of: ' + asOfval;

		    var brk = document.createElement('br');    
		    
		    var headContainer = document.createElement('div');
		    headContainer.style.paddingTop = "30px";
			headContainer.appendChild(orgl);
		    headContainer.appendChild(asOff);
		    headContainer.appendChild(brk);
		    headContainer.id = 'conta' + n;  
		    
		    if(i==0){
		    	document.getElementById('headers').appendChild(headContainer);
		    }
		    else{
		    	document.getElementById('contb' + (i-1).toString()).appendChild(headContainer);
		    }
		    
		    var stateTable = document.createElement('table');
		    stateTable.classList.add('kite-table', 'table_wrap');
		    stateTable.setAttribute('class', 'responsive');
		    stateTable.setAttribute('role', 'presentation');
		    var tableId =  'testingSummaryTable' + n;
		    stateTable.setAttribute('id', tableId);
		    
		    
		    var tableContainer = document.createElement('div');
		    tableContainer.style.paddingTop = "40px";
		    tableContainer.id = 'contb' + n;
		    tableContainer.appendChild(stateTable);				    
		    document.getElementById('conta' + n).appendChild(tableContainer);		
		    
		    initTestingSummaryTable(response.gridData, tableId);				    
		    tableContainer.appendChild(brk);
        });
	}
	clearHtml = true;
	getAllStateIndicator = true;
}

function setHeaders(){
	var orgl = document.createElement('div');
	orgl.setAttribute('id', 'orgLabel');
    orgl.style.cssFloat = "left";

    var asOff = document.createElement('div');
    asOff.setAttribute('id', 'asOff');
    asOff.style.fontSize = "1rem";
    asOff.style.cssFloat = 'right';
    asOff.style.marginRight = "10px";
    asOff.textContent = 'As of: ' + asOfval;
    
    var brk = document.createElement('br');	    
    
    var headContainer = document.createElement('div');
	headContainer.appendChild(orgl);
    headContainer.appendChild(asOff);
    headContainer.appendChild(brk);
    headContainer.id = 'conta';
    document.getElementById('headers').appendChild(headContainer);
}

function setTableData(response){
	var stateTable = document.createElement('table');
    stateTable.setAttribute('class', 'responsive');
    stateTable.setAttribute('role', 'presentation');
    var tableId =  'testingSummaryTable';
    stateTable.setAttribute('id', tableId);

    var brk = document.createElement('br');

    var tableContainer = document.createElement('div');
    tableContainer.style.paddingTop = "10px";
    tableContainer.appendChild(stateTable);	
    tableContainer.id = 'contb';
    			    
    document.getElementById('conta').appendChild(tableContainer);		
    tableContainer.classList.add('kite-table', 'table_wrap');
    initTestingSummaryTable(response.gridData, tableId);				    
    tableContainer.appendChild(brk);	
}

function setOrgName(orgs){
	if (orgs.schoolName != null){
		$('#orgLabel').html('School: ' + orgs.schoolName);
	} else if (orgs.districtName != null){
		$('#orgLabel').html('District: ' + orgs.districtName);
	} else {
		$('#orgLabel').html('State: ' + orgs.stateName);
	}
}

function resetSelects(){
	$('#organizationDistrictId').find('selected').remove();
	$('#organizationSchoolId').find('option:not(:first)').remove();
}

function switchButtonContext(){
	if (accessLevel=='20'){
		$('#selectOrgForTestingSummary').val('View a district');
		$('#backToDefaultOrgForTestingSummary').val('Back to state');
		$('#selectOrgForTestingSummary').show();
	}else if (accessLevel=='50'){
		$('#selectOrgForTestingSummary').val('View a school');
		$('#backToDefaultOrgForTestingSummary').val('Back to district');
		$('#selectOrgForTestingSummary').show();
	} else {
		$('#selectOrgForTestingSummary').hide();
	}
}