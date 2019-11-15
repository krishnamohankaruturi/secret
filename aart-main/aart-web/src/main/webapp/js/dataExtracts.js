var userAccessLevel = $('#dataExtractUserAccessLevelId').val();
var stateFilterRequired = userAccessLevel < 20;
var districtFilter = stateFilterRequired || userAccessLevel < 50;
var schoolFilter = districtFilter || userAccessLevel < 70;
var assessmentProgramFilterRequired = $('#userDefaultAssessmentProgram option').length > 1;
var extractFiltersAvailable = stateFilterRequired || districtFilter || schoolFilter; // only false if the user is restricted to one school
var isAllCompleted = true; // Determines whether there is any items In Progress items.

var extractRefreshInterval = null;
var extractTypeIdCheck = null;
var localextractTypeId;
$(function(){
	if (!stateFilterRequired){
		$('#stateExtractFilter').hide();
		$('#itiBluePrintStateExtractFilter').hide();
		$('#organizationStateExtractFilter').hide();
		$('#groupByCheckBoxForITIBP').hide();
	}
	if (!districtFilter){
		$('#districtExtractFilter').hide();
		$('#itiBluePrintDistrictExtractFilter').hide();
		$('#organizationDistrictExtractFilter').hide();
		$('#groupByCheckBoxForITIBP').hide();
	}
	if (!schoolFilter){
		$('#itiBluePrintSchoolExtractFilter').hide();
		$('#organizationSchoolExtractFilter').hide();
		$('#schoolExtractFilter').hide();
	}
	
	getExtractsTable();
	initializeFilterDropdowns();
	
	$('#extractAssessmentProgramId').on('change', function(){
		var assessmentProgramValue = $("#extractAssessmentProgramId option:selected" ).text();
		if($('#extractAssessmentProgramId').val()!='')
		{
			$("#extractSubFilter").removeClass("hidden");
		}
		else
		{
			$("#extractSubFilter").addClass("hidden");
		}
		checkAssessmentProgramPltw();		
		//$('#rosterExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
		$('#rosterExtractFilter').find('option:not(:first)').remove();
		$('#rosterExtractFilter').hide();
	});
	$(document).delegate('#extractStateId','change',function(){
		$('#extractSchoolId').find('option:not(:first)').remove();
		populateAssessmentProgramsForExtract();
		getDistricts($(this).val());
	});
	
	$(document).delegate('#extractStateIdForSecurityExtract','change',function(){
		$('#extractSchoolIdForSecurityExtract').find('option:not(:first)').remove();
		populateAssessmentProgramsForMultiSelect();
		getDistrictsForSecurityExtract($(this).val());
	});
		
	$(document).delegate('#organizationExtractStateId','change',function(){
		$('#organizationExtractSchoolId').find('option:not(:first)').remove();	
		getDistrictsForOrganization($(this).val());
	});
	
	$('#extractDistrictId').on('change', function(){
		getSchools($(this).val());
	});
	$('#extractDistrictIdForPDTraining').on('change', function(){
		getSchoolsForPDStatus($(this).val());
	});
	$('#extractDistrictIdForSecurityExtract').on('change', function(){
		getSchoolsForSecurityExtract($(this).val());
	});
	$('#extractSchoolId').on('change', function(){
		$('#extractGradeId').find('option:not(:first)').remove();
		getGrades($(this).val());
	});
	
	$('#itiBluePrintExtractDistrictId').on('change', function(){
		$('#itiGradeExtractFilter').find('option:not(:first)').remove();
		$('#itiSubjectExtractFilter').find('option:not(:first)').remove();
		getSubjectsForITIBpExtract();
		getSchoolsForItiBluePrint($(this).val());
	});
	
	$('#itiBluePrintExtractSchoolId').on('change', function(){
		$('#itiGradeExtractFilter').find('option:not(:first)').remove();
		$('#itiSubjectExtractFilter').find('option:not(:first)').remove();
		getSubjectsForITIBpExtract();		
	});
	
	// DLMGRF file district onChange
	$('#dlmGeneralReseachExtractDistrictId').on('change', function(){
		
		$('#dlmGeneralReseachSubjectExtractFilter').find('option:not(:first)').remove();
		$('#dlmGeneralReseachGradeExtractFilter').find('option:not(:first)').remove();
		getSchoolsForGRF($(this).val());	
	});
	//DLM GRF file for school change
	$('#dlmGeneralReseachExtractSchoolId').on('change', function(){
		$('#dlmGeneralReseachGradeExtractFilter').find('option:not(:first)').remove();
		getSubjectForGRF($(this).val());
	});
	//DLM GRF file for subject change
	$('#dlmGeneralReseachSubjectExtractFilter').on('change', function(){
		getGradeForGRF($(this).val());
	});
	
	$('#itiSubjectExtractFilter').on('change', function(){
		$('#itiGradeExtractFilter').find('option:not(:first)').remove();
		getGradesForITIBpExtract($(this).val());
	});
	
	$('#districtIdForStudentScore').on('change', function(){
		document.getElementById("schoolIdForStudentScore_kap_score").checked = true;
		getSchoolsForStudentScoreExtract($(this).val());		
	});
	

	$('#kelpa2districtIdForStudentScore').on('change', function(){
		$('#schoolIdForStudentScore_kelpa2_score').checked = true;
		getSchoolsForKelpaStudentScoreExtract($(this).val());
	});
	
	
	$('#schoolIdForStudentScore').on('change', function(){
		var schoolId=$('#schoolIdForStudentScore').val();
		var distictId=$('#districtIdForStudentScore').val();		
		if(schoolId != null && schoolId != ''){
			getSubjectsForStudentScoreExtract($(this).val(), 'SCH',localextractTypeId);	
			getGradesForStudentScoreExtract($(this).val(), 'SCH',localextractTypeId);
		}	else if((distictId != null && distictId !='')){			
			getSubjectsForStudentScoreExtract(distictId, 'DT',localextractTypeId);
			getGradesForStudentScoreExtract(distictId, 'DT',localextractTypeId);
		}	else if(schoolId==null && userAccessLevel ==50 ){
			getSubjectsForStudentScoreExtract($('#userNarrowestOrgId').val(), 'DT',localextractTypeId);
			getGradesForStudentScoreExtract($('#userNarrowestOrgId').val(), 'DT',localextractTypeId);
		}
	});
	
	$('#kelpa2schoolIdForStudentScore').on('change', function(){
		var schoolId=$('#kelpa2schoolIdForStudentScore').val();
		var distictId=$('#kelpa2districtIdForStudentScore').val();		
		if(schoolId != null && schoolId != ''){
			getGradesForKelpaStudentScoreExtract($(this).val(), 'SCH',localextractTypeId);
		}	else if((distictId != null && distictId !='')){			
			getGradesForKelpaStudentScoreExtract(distictId, 'DT',localextractTypeId);
		}	else if(schoolId==null && userAccessLevel ==50 ){
			getGradesForKelpaStudentScoreExtract($('#userNarrowestOrgId').val(), 'DT',localextractTypeId);
		}
		getSchoolYearsForStudentScoreExtractForKelpa(null);
	});
	
	$('#subjectIdForStudentScore').on('change', function(){
		var subjectIds = $('#subjectIdForStudentScore').select2("val");
		getSchoolYearsForStudentScoreExtract(subjectIds);
		
	});
	
	$('#extractGradeId').on('change', function(){
		//$('#extractSubjectId').find('option:not(:first)').remove();
		$('#extractSubjectId').find('option').filter(function(){return $(this).val() > 0}).remove().end();
	    if(extractTypeIdCheck == 25) {
			var assessmentProgramIds = $("#extractAssessmentProgramId").select2('val');
			getSubjectsByGradeAndAssessment($(this).val(), assessmentProgramIds);  	
	    } else {
			getSubjectsByGrade($(this).val());	    	
	    }
	});
	$('#extractSubjectId').on('change', function(){
		$('#extractRosterId').find('option:not(:first)').remove();
		var schoolId = $('#extractSchoolId').val();
	    if(extractTypeIdCheck != 25) {
		getRostersBySubject($(this).val(), schoolId);
	    }
	});
	$('input[name="extractSummaryLevel"]').on('click', function(){
		var $this = $(this);
		if (!$this.prop('checked')){
			showFilterDropdowns(false, false, false);
			return;
		}
		var val = $this.val();
		var showState = stateFilterRequired;
		var showDistrict = userAccessLevel < 50 && (val === '50' || val === '70');
		var showSchool = userAccessLevel < 70 && val === '70';
		$('#extractFilterDropdowns').show();
		showFilterDropdowns(showState, showDistrict, showSchool);
		loadInitialOrgs(10, showState, showDistrict, showSchool);
		$('#extractSummaryErrorMsg').empty();
		// if new(val) < old(val) then blank out the dropdown that corresponds to val
	});
	
	$('input[name="itiBluePrintextractSummaryLevel"]').on('click', function(){
		var $this = $(this);
		if (!$this.prop('checked')){
			showItiBluePrintFilterDropdowns(false, false, false);
			return;
		}
		var val = $this.val();
		var showState = stateFilterRequired;
		var showDistrict = userAccessLevel < 50 && (val === '50' || val === '70');
		var showSchool = userAccessLevel < 70 && val === '70';
		$('#itiSubjectExtractFilter').find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$('#itiGradeExtractFilter').find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$('#itiBluePrintExtractFilterDropdowns').show();
		$('#groupByTeacher').prop('checked', false);
		$('#itiBluePrintExtractDistrictId').find('option:not(:first)').remove();
		$('#itiBluePrintExtractSchoolId').find('option:not(:first)').remove();
		showItiBluePrintFilterDropdowns(showState, showDistrict, showSchool);		
		loadInitialOrgsForITIBluePrint(showState, showDistrict, showSchool);
		getSubjectsForITIBpExtract();		
		if( val == 'undefined' || val <= 20 || $('#groupCode').val() == 'TEA') {
			$('#groupByCheckBoxForITIBP').hide();
		} else {
			$('#groupByCheckBoxForITIBP').show();
		}
		$('#itiBluePrintSummaryErrorMsg').empty();		
	});
	
	$('input[name="organizationextractSummaryLevel"]').on('click', function(){
		$('#includeInactiveOrganizations').prop('checked',false);
		var $this = $(this);
		if (!$this.prop('checked')){
			showOrganizationFilterDropdowns(false, false, false);
			return;
		}
		var val = $this.val();
		var showState = stateFilterRequired;
		var showDistrict = userAccessLevel < 50 && (val === '50' || val === '70');
		var showSchool = userAccessLevel < 70 && val === '70';
		$('#organizationExtractFilterDropdowns').show();
		$('#organizationExtractDistrictId').find('option:not(:first)').remove();
		$('#organizationExtractSchoolId').find('option:not(:first)').remove();
		showOrganizationFilterDropdowns(showState, showDistrict, showSchool);
		loadInitialOrgsForOrganization(showState, showDistrict, showSchool);
		$('#organizationSummaryErrorMsg').empty();		
	});
	
	$('#organizationExtractDistrictId').on('change', function(){
		getSchoolsForOrganization($(this).val());
	});
	
	/*Initialize dates*/
	initializeDates();
	
});
$(document).on("click", ".selectAll_kap_extract", function(ev){	
	var checkboxId= this.id;  
	checkboxId = checkboxId.replace("_kap_score","");	
	if($(this).is(':checked')){
	   $("#"+checkboxId).find('option').prop("selected",true);
        $("#"+checkboxId).trigger('change');
	   }else{
	      $("#"+checkboxId).find('option').prop("selected",false);
        $("#"+checkboxId).trigger('change');

	   }
	   
});

$(document).on("click", ".selectAll_kelpa2_extract", function(ev){	
	var checkboxId= this.id;  
	checkboxId = checkboxId.replace("_kelpa2_score","");	
	if($(this).is(':checked')){
		$('#kelpa2' + checkboxId).find('option').prop("selected",true);
		$('#kelpa2' + checkboxId).trigger('change');
	}else{
		$('#kelpa2' + checkboxId).find('option').prop("selected",false);
		$('#kelpa2' + checkboxId).trigger('change');

	}
	   
});

function initializeFilterDropdowns(){
	$('.select-plugin').css({'width':'50%'}).select2();
}

function getExtractsTable(){
	isAllCompleted = true; // Assume all items are in COMPLETE state
	var grid = $('#extractsTable').jqGrid({
		url: 'getDataExtracts.htm',
		mtype: 'POST',
		datatype: 'json',
		shrinkToFit: true,
		autowidth: true,
		height: 415,
		postData: {
			filters: "",
		},
		colNames: [
			'id',
			'extractTypeId',
			'Extract',
			'Description',
			'Requested',
			'fileName',
			'File',
			'Action',
			'dataDetailFileLocation',
			'dataDetailFileName',
			'specialDataDetailFileLocation',
			'specialDataDetailFileName',
			'dataDetailActiveFlag',
			'specialDataDetailActiveFlag',
			'pdfFileName'
		],
		colModel: [
			{name: 'id', index: 'id', align: 'center', hidden: true},
			{name: 'extractTypeId', index: 'extractTypeId', align: 'center', hidden: true},
			{name: 'extractName', index: 'extractName', align: 'center', sortable: false, width: 30},
			{name: 'description', index: 'description', align: 'left', sortable: false, formatter: dataDetailLinkFormatter, width: 45,
				cellattr: function (cellValue,options,rowObject) { return ' title="'+rowObject[3]+'"'; }		
			},
			{name: 'requested', index: 'requested', align: 'center',sortable: false, width: 18,
				formatter: function(cellValue, options, rowObject){
					if(cellValue != 'Not Available' && cellValue != '') {
       					var date = new Date(parseFloat(cellValue));
       					var ampm = date.getHours() >= 12 ? 'PM' : 'AM';
       					var hours = date.getHours() % 12;
       					if (hours == 0){
       						hours = 12;
       					}
       					var timeStr = (hours > 9 ? hours : '0' + hours.toString()) + ':' +
       							(date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes().toString());
       					timeStr += ' ' + ampm;
       					return ($.datepicker.formatDate('mm/dd/yy', date)) + ' ' + timeStr;
       				} else { return cellValue; }
				}
			},
			{name: 'fileName', index: 'fileName', align: 'center', hidden: true},
			{name: 'status', index: 'status', align: 'center', sortable: false, formatter: extractLinkFormatter, width: 10},
			{name: 'actions', index: 'actions', align: 'center', sortable: false, formatter: extractActionFormatter, width: 10},
	       	{name:'dataDetailFileLocation', index:'dataDetailFileLocation', align:'center',hidden: true},
			{name:'dataDetailFileName',index:'dataDetailFileName',align:'center',hidden: true},
			{name:'specialDataDetailFileLocation', index:'specialDataDetailFileLocation', align:'center',hidden: true},
			{name:'specialDataDetailFileName',index:'specialDataDetailFileName',align:'center',hidden: true},
			{name:'dataDetailActiveFlag',index:'dataDetailActiveFlag',align:'center',hidden: true},
			{name:'specialDataDetailActiveFlag',index:'specialDataDetailActiveFlag',align:'center',hidden: true},
			{name: 'pdfFileName', index: 'pdfFileName', align: 'center', hidden: true}
			],
		rowNum: 100,
		pgbuttons : false,
		viewrecords : false,
		pgtext : "",
		pginput : false,
		//rowList: null,
		pager: '#extractsPager',
		viewrecords: true,
		sortorder: 'asc',
		sortname: 'extractTypeId',
		altclass: 'altrow',
		emptyrecords: fmtDataExtractMsg.CommonNoRecordsFound,
		altRows: true,
		hoverrows: true,
		multiselect: false,
		toppager: false,
		loadtext:'',
		loadui: 'disable',
		gridview: false,
		beforeSelectRow: function(){
			return false;
		},
		beforeRequest: function(){
			// turn off any subsequent refresh until we know we need it
			if (extractRefreshInterval != null){
				clearInterval(extractRefreshInterval);
				extractRefreshInterval = null;
			}
			// reset complete check
			isAllCompleted = true;
		},
		loadComplete: function(data){
			var ids = $(this).jqGrid('getDataIDs');         
			var tableid=$(this).attr('id');   			
			var objs= $('#gbox_'+tableid).find('tr');
            $.each(objs, function(index, value) { 
               $(value).attr('id',index);                          
           });
			
			if (isAllCompleted == false){
				if (extractRefreshInterval == null){
					// If all items are not done, recheck status after 15 seconds.
					extractRefreshInterval = setInterval(function(){
						$('#extractsTable').trigger('reloadGrid', [{current:true}]);
					}, 15000);
				}
			} else {
				if (extractRefreshInterval != null){
					clearInterval(extractRefreshInterval);
					extractRefreshInterval = null;
				}
			}
		},
		afterInsertRow: function(rowid, rowdata, rowelem){
			var status = rowelem[6].toUpperCase();
			// see if there are any extracts not done executing
			if($.inArray(status, ['', 'COMPLETED', 'FAILED']) == -1){
				isAllCompleted = false;
			}
		}
	});
	grid.jqGrid('navGrid', '#extractsPager', {search:false, edit:false, add:false, del: false, refreshstate: 'current'});
}

function extractLinkFormatter(cellValue, options, rowObject){
	var status = rowObject[6].toUpperCase();
	var extractTypeId = rowObject[1];
	var filePath = rowObject[5];
	if (status == 'COMPLETED' && (extractTypeId == 48 || extractTypeId == 41)){
		if (/\.csv$/i.test(filePath)){
			return '<a href="getDataExtractCsv.htm?extractName=' + filePath + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">CSV</a>';
		} else if (/\.xlsx$/i.test(filePath)){
			return '<a href="getDataExtractCsv.htm?extractName=' + filePath + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">XLSX</a>';
		}
	}
	if (status == "COMPLETED" && extractTypeId != 41 && extractTypeId != 42 && extractTypeId != 43 && extractTypeId != 47 && extractTypeId != 25) {
		return '<a href="getDataExtractCsv.htm?extractName=' + filePath + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">CSV</a>';
	} else if(status == "COMPLETED" && ( extractTypeId == 42 || extractTypeId == 43 || extractTypeId == 47 && extractTypeId != 25)) {
		return '<a href="getDataExtractCsv.htm?extractName=' + filePath + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">XLSX</a>';
	}else if(status == "COMPLETED" && (extractTypeId == 25 )) {
		var extPdf = '';
		var extCsv = '';
		if(rowObject[15] != 'Not Available') {
			extPdf = rowObject[15].split('.').pop();
		}
		if(filePath  != 'Not Available') {
			extCsv = filePath.split('.').pop();			
		}
		var downloadLink = 'Failed';
		if((extCsv == 'csv') && (extPdf == 'pdf')) {
			downloadLink = '<a href="getDataExtractCsv.htm?extractName=' + extCsv + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">CSV,</a>&nbsp;' +
			'<a href="getDataExtractCsv.htm?extractName=' + extPdf + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">PDF</a>'
			} else if(extPdf == 'pdf') {
			downloadLink = '<a href="getDataExtractCsv.htm?extractName=' + extPdf + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">PDF</a>';			
			} else if (extCsv == 'csv') {
				downloadLink = '<a href="getDataExtractCsv.htm?extractName=' + extCsv + '&extractType='+ extractTypeId +'&extractId='+rowObject[0] +'" title="Click to download '+rowObject[2]+' extract.">CSV</a>';			
			}
		return downloadLink;
	}
	return cellValue;
}


function dataDetailLinkFormatter(cellValue, options, rowObject){
	
	if((rowObject[12] =="false" || rowObject[12] == "null" ) &&  (rowObject[13]=="false"  || rowObject[13] == "null")){
			return rowObject[3];
	}else{ 
		if(rowObject[12]=="true" && rowObject[13]=="false" ){ 
			return rowObject[3]+'<a href="getDataDetail.htm?dataDetailFileName=' + rowObject[9] + '&dataDetailFileLocation='+ rowObject[8] +'" title="Click to download Data Detail."> <u>Data Detail</u></a>';
		}else if(rowObject[12]=="false" && rowObject[13]=="true" && rowObject[2]=="DLM General Research File (GRF)"){ 
			return rowObject[3]+'<a href="getSpecialDataDetail.htm?specialDataDetailFileName=' + rowObject[11] + '&specialDataDetailFileLocation='+ rowObject[10] +'" title="Click to download EE Crosswalk."> <u>EE Crosswalk</u></a>';
		}else if(rowObject[12]=="false" && rowObject[13]=="true" && rowObject[2] !="DLM General Research File (GRF)"){
			return rowObject[3]+'<a href="getSpecialDataDetail.htm?specialDataDetailFileName=' + rowObject[11] + '&specialDataDetailFileLocation='+ rowObject[10] +'" title="Click to download Data Detail 2."> <u>Data Detail 2</u></a>';
		}else if(rowObject[12]=="true" && rowObject[13]=="true" && rowObject[2] !="DLM General Research File (GRF)"){
			return rowObject[3]+'<a href="getDataDetail.htm?dataDetailFileName=' + rowObject[9] + '&dataDetailFileLocation='+ rowObject[8] +'" title="Click to download Data Detail."> <u>Data Detail</u></a>'
			+'<a href="getSpecialDataDetail.htm?specialDataDetailFileName=' + rowObject[11] + '&specialDataDetailFileLocation='+ rowObject[10] +'" title="Click to download Data Detail 2."> <u>Data Detail 2</u></a>';
		}else if(rowObject[12]=="true" && rowObject[13]=="true" && rowObject[2] =="DLM General Research File (GRF)"){
			return rowObject[3]+'<a href="getDataDetail.htm?dataDetailFileName=' + rowObject[9] + '&dataDetailFileLocation='+ rowObject[8] +'" title="Click to download Data Detail."> <u>Data Detail</u></a>'
			+'<a href="getSpecialDataDetail.htm?specialDataDetailFileName=' + rowObject[11] + '&specialDataDetailFileLocation='+ rowObject[10] +'" title="Click to download EE Crosswalk."> <u>EE Crosswalk</u></a>';
		}
	}
}
	
function extractActionFormatter(cellValue, options, rowObject){
	var buttonId = 'newFile' + rowObject[1];
	var currentAssessmentProgram = $('#userDefaultAssessmentProgram option:selected').text();
	if(currentAssessmentProgram == "DLM" && (buttonId == "newFile47" || buttonId == "newFile42")){
		return "";
	}else{
		var status = rowObject[6].toUpperCase();
		if (status == "IN PROGRESS") {
			return '<button id="'+buttonId+'" disabled="disabled" title="Extract generation is In-Progress">New File</button>';
		} else {
			return '<button id="'+buttonId+'" onclick="generateExtractFilterDialog('+ rowObject[0] +', '+ rowObject[1] + ',' + rowObject[14] +  ', this)" title="Click to generate '+rowObject[2]+' extract">New File</button>';						
		}
	}
}

function generateExtractFilterDialog(extractId, extractTypeId, queued, button){
	extractTypeIdCheck = null;
	extractTypeIdCheck = extractTypeId;
	$('#downloadFileTypes').hide();
	localextractTypeId = extractTypeId;
	$('#pnpAbridgedFilters').hide();
	if(queued){
		$('#dateExtractQueueMessage').html(fmtDataExtractMsg.QueuedMessage).show();
	}else{
		$('#dateExtractQueueMessage').hide();
	}
	if(extractTypeId == 43 || extractTypeId == 42 || extractTypeId == 47 ){
		var params = {
				stateId: $('#userNarrowestOrgId').val()
		};		
		generateExtract(extractId, extractTypeId, params);
		return;	
	}
	if(extractTypeId == 8  && $('#dataExtractUserAccessLevelId').val() == 70){
		//Deb : F918 DLM Test Administration Report Date Change Removal Tech Specification
		// for building level user it won't ask for assessment program id. The extract will use the current selected assessment program id i.e. DLM
		var params = {
				assessmentProgramIds: $('#hiddenCurrentAssessmentProgramId').val()
		};		
		generateExtract(extractId, extractTypeId, params);
		return;	
	}
	
	$('#rosterExtractFilter').find('option:not(:first)').remove();
	$('#rosterExtractFilter').hide();
	//$('#rosterExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	//dlmGeneralReseach
	if(extractTypeId == 41){
		
		var params = {};
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: 505,			
			modal: true,
			autoOpen: true,
			title: 'Create Extract Filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					if(districtFilter){
						params = {
								stateId: $('#userNarrowestOrgId').val(),	
								districtId: $('#dlmGeneralReseachExtractDistrictId').val(),
								schoolId: $('#dlmGeneralReseachExtractSchoolId').val(),	
								subject: $('#dlmGeneralReseachSubjectExtractFilter').val(),
								grade: $('#dlmGeneralReseachGradeExtractFilter').val()
							};	
					} else if(schoolFilter) {
					params = {	
						stateId: '',	
						districtId: $('#userNarrowestOrgId').val(),
						schoolId: $('#dlmGeneralReseachExtractSchoolId').val(),	
						subject: $('#dlmGeneralReseachSubjectExtractFilter').val(),
						grade: $('#dlmGeneralReseachGradeExtractFilter').val()
					};
					}
					
					generateExtract(extractId, extractTypeId, params);
					$(this).dialog('close');	
					
				}},
				{
					text:"Cancel",
					title:'Cancel',
					click: function(){
					$(this).dialog('close');					
				}
			}],
			
			open: function(){			
				//loadCustomFiltersForItiBluePrintSummary();								
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();				
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#checkboxidforinternal').hide();
				$('#dlmGeneralReseach').show();		
				$('#dlmGeneralReseachExtractFilterDropdowns').show();
				
				
				$('#dlmGeneralReseachExtractSchoolId').find('option:not(:first)').remove();
				$('#dlmGeneralReseachSubjectExtractFilter').find('option:not(:first)').remove();
				$('#dlmGeneralReseachGradeExtractFilter').find('option:not(:first)').remove();
				
				if(districtFilter){
					var val = $(this).val();
					var showState = stateFilterRequired;
					var showDistrict = userAccessLevel < 50 && (val === '50' || val === '70');
					var showSchool = userAccessLevel < 70 && val === '70';					
					loadInitialOrgsForGRF(extractTypeId, showState, showDistrict, showSchool,$('#userNarrowestOrgId').val());					
				} else if(schoolFilter){
					$('#dlmGeneralReseachDistrictExtractFilter').hide();
					var val = $(this).val();
					var showState = stateFilterRequired;
					var showDistrict = userAccessLevel < 50 && (val === '50' || val === '70');
					var showSchool = userAccessLevel < 70 && val === '70';					
					//loadInitialOrgsForGRF(extractTypeId, showState, showDistrict, showSchool,$('#userNarrowestOrgId').val());
					getSchoolsForGRF($('#userNarrowestOrgId').val());
					
				}
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				
			}
		});
		
		return;	
	}
	
	
	if((extractTypeId == 34 && extractTypeId == 18) || extractTypeId == 38 || extractTypeId == 39 || extractTypeId == 51 || extractTypeId == 52  || extractTypeId == 56){
		  assessmentProgramFilterRequired = false;
	 }
	 
	if(isTeacher && extractTypeId!=8){
		  assessmentProgramFilterRequired = false;
	 }
	
	$(button).attr('disabled', 'disabled');	
	$('#extractSummaryErrorMsg').empty();
	
	var extractTypeIdsThatNeedFilterDialog = [8, 25, 38, 39, 48, 50, 51, 52, 56];
	if (!(extractFiltersAvailable || assessmentProgramFilterRequired) && $.inArray(extractTypeId, extractTypeIdsThatNeedFilterDialog) === -1){
		var params = {};
		if (extractTypeId == 10){ //PNP Summary
			params.extractSummaryLevel = $('input[name="extractSummaryLevel"][value="70"]').val();
			$('#extractSummaryErrorMsg').empty();
		}
		//DE13675 
		params.assessmentProgramIds = [$('#userDefaultAssessmentProgram').val()];
		generateExtractReplaceDialog(extractId, extractTypeId, params, button);
		return;
	}
	//DE13729
	var dialogWidth = 600;
	/*
     * Added during US16344 : for Extracting reports on Test Form assign to TestCollection for quality check
     */
	if(extractTypeId == 18||extractTypeId == 34|| extractTypeId == 5||extractTypeId == 35){		
		$('#checkboxidforinternal').show();
		$('#ksdeExtractFilter').hide();
		$('#includeInternuluserId').prop('checked',false);
	}else{
		$('#checkboxidforinternal').hide();
	}
	if(extractTypeId == 19 || extractTypeId == 20){
		if(extractTypeId == 20){
			$('#testFormWindow').show();
		} else if(extractTypeId == 19){
			$('#testFormWindow').hide();
		}
		
		$('#extractSummaryErrorMsg').empty();
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					    var params = {								
					    	assessmentProgramIds : $('#extractAssessmentProgram').select2("val"),
					    	qcCompleteStatus: $('#extractQCCompleteStatus').val(),
					    	fromDate: $('#fromDate').val(),
					    	media : $('#media').is(":checked"),
					    	toDate: $('#toDate').val()
						}; 		
					    
						if (params.assessmentProgramIds == null || params.assessmentProgramIds.length < 1 ){
							alert('Assessment Program is required.');
						}else if(params.qcCompleteStatus === ''){
							alert('QC Complete status is required.');
						}else if(params.fromDate === '' || params.toDate === ''){
							alert('Please provide date range');
						}else if(new Date(params.fromDate)>new Date(params.toDate) || new Date(params.toDate) > new Date()){
							alert('Invalid date');
						} else {
							$(this).dialog('close');
							$('#extractQCCompleteStatus').val("");
							generateExtractReplaceDialog(extractId, extractTypeId, params, button);								
						}
				}},
				{
					text:"Cancel",
					title:'Cancel',
					click: function(){
					$(this).dialog('close');
					initializeDates();
				}
			}],
			open: function(){
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#dlmGeneralReseach').hide();
				$('#testFormAssignmentLevelExtractFilter').show();
				getAssessmentProgram(extractTypeId);
				//loadKSDEFilters();
				
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#ksdeExtractFilter').hide();
				$('#summaryLevelExtractFilter').show();
				$('#extractFilterDropdowns').show();
				initializeDates();
			}
		});
		
	}else if(extractTypeId == 11 || extractTypeId == 12 || extractTypeId == 24){
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					var params = {
							studentStateId: $('#extractStudentStateId').val().trim()
						};
						
						if ((extractTypeId==11 || extractTypeId == 12) && params.studentStateId.trim() === ''){
							alert('Student State ID is required.');
						} else {
							$(this).dialog('close');
							$('#extractStudentStateId').val("");
							validateStudentID(extractId, extractTypeId, params);
						}
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#dlmGeneralReseach').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').show();
				//loadKSDEFilters();
				
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').show();
				$('#summaryLevelExtractFilter').show();
				$('#extractFilterDropdowns').show();
			}
		});
		
	}else if(extractTypeId == 38 || extractTypeId == 52){		
		document.getElementById("schoolIdForStudentScore_kap_score").checked = true;
		document.getElementById("gradeIdForStudentScore_kap_score").checked = true;
		document.getElementById("subjectIdForStudentScore_kap_score").checked = true;
		document.getElementById("schoolYearIdForStudentScore_kap_score").checked = true;
		$('#stateStudentIdForStudentScore').val("");
		$('#subjectIdForStudentScore').val("");
		
		if(extractTypeId == 38){
			$('#gradeIdForEnrolledStudentScore').text('Grade enrolled in '+ $('#kap_St_schoolYear').val())+":";
		}else{
			$('#gradeIdForEnrolledStudentScore').text('Grade tested in '+$('#kap_St_ReportYear').val()+":");
		}		
		var scoreDialogWidth = 600;
		if(districtFilter){
			scoreDialogWidth = 820;
		}
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: scoreDialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){					
					var params = {};					
					var districtId = null;
					var schoolIds = null;
					var gradeIds = null;
					var schoolYears = null;					
					if(districtFilter){
						districtId = $('#districtIdForStudentScore').val();
						if((districtId == null || districtId === '') && studentStateId.trim() === ''){
							alert('District selection is required.');
							return;
						}						
					}else if(!districtFilter && (districtId == null || districtId === '') && schoolFilter){
						districtId = $('#userNarrowestOrgId').val();
					}	
					
					params.districtId = districtId;
					$('#schoolIdForStudentScore').select2({multiple:true});
					schoolIds =$('#schoolIdForStudentScore').select2("val");
//					schoolId = $('#schoolIdForStudentScore').val();
					if(!districtFilter && !schoolFilter){						
						var orgIds=JSON.parse("[" + $('#userNarrowestOrgId').val() + "]");							
						schoolIds = orgIds;	
						if((schoolIds == null  ||  schoolIds.length < 1 )){
							alert('School selection is required.');
							return;
						}
						
					}
					params.schoolIds = schoolIds;
					$('#subjectIdForStudentScore').select2({multiple:true});
					var contentAreaIds = $('#subjectIdForStudentScore').select2("val");
					
					
					params.contentAreaIds = contentAreaIds;
					if((districtId != null && districtId != '') || (schoolId != null && schoolId != '')){
						if ($('#gradeIdForStudentScore option').length > 0) {
							gradeIds = $('#gradeIdForStudentScore').select2("val");
						}
					}
					params.gradeIds = gradeIds;
					
					if (contentAreaIds !== undefined && contentAreaIds !== null && contentAreaIds.length > 0 ){
						schoolYears = $('#schoolYearIdForStudentScore').select2("val");
					}					
					params.schoolYears = schoolYears;						
							
					$(this).dialog('close');
							
						generateExtractReplaceDialog(extractId, extractTypeId, params, button);
					
					
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#dlmTestAdministrationMessage').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#dlmGeneralReseach').hide();
				$('#kelpa2studentScoreExtractFilter').hide();
				$('#kapStudentScoreExtractFilter').show();
				$('#schoolYearIdForStudentScore').prop('disabled', true);
				$('#schoolIdForStudentScore').prop('disabled', true);
				$('#schoolFilterForStudentScore').hide();
				$('#schoolYearFilterForStudentScore').hide();
				$('#subjectIdForStudentScore').select2({multiple: true});
				if(districtFilter){
					$('#gradeFilterForStudentScore').hide();
				}
				populateFiltersForStudentScore();
				
				if(!districtFilter && !schoolFilter){
					getSubjectsForStudentScoreExtract($('#userNarrowestOrgId').val(), 'SCH',extractTypeId);
					getGradesForStudentScoreExtract($('#userNarrowestOrgId').val(), 'SCH',extractTypeId);
					
				}
				
			},
			close: function(){
				$('#subjectIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();
				$(button).removeAttr('disabled');
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#dlmTestAdministrationMessage').hide();
				$('#kelpa2studentScoreExtractFilter').hide();
				$('#kapStudentScoreExtractFilter').hide();
				$('#kapStudentSpecifiedScoreExtractFilter').hide();
			}
		});
		
	}else if(extractTypeId == 56){	
		document.getElementById("schoolIdForStudentScore_kelpa2_score").checked = true;
		document.getElementById("gradeIdForStudentScore_kelpa2_score").checked = true;
		document.getElementById("schoolYearIdForStudentScore_kelpa2_score").checked = true;
		$("#kelpa2StateStudentIdForStudentScore").val("");
		$('#kelpa2subjectIdForStudentScore').val("");
		
		if(extractTypeId == 56){
			$('#kelpa2gradeIdForEnrolledStudentScore').text('Grade enrolled in '+ $('#kelpa2_St_schoolYear').val())+":";
		}else{
			$('#kelpa2gradeIdForEnrolledStudentScore').text('Grade tested in '+$('#kelpa2_St_ReportYear').val()+":");
		}		
		var scoreDialogWidth = 600;
		if(districtFilter){
			scoreDialogWidth = 820;
		}
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: scoreDialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){					
					var params = {};					
					var districtId = null;
					var schoolIds = null;
					var gradeIds = null;
					var schoolYears = null;					
					if(districtFilter){
						districtId = $('#kelpa2districtIdForStudentScore').val();
						if((districtId == null || districtId === '') && studentStateId.trim() === ''){
							alert('District selection is required.');
							return;
						}						
					}else if(!districtFilter && (districtId == null || districtId === '') && schoolFilter){
						districtId = $('#userNarrowestOrgId').val();
					}	
					
					params.districtId = districtId;
					$('#kelpa2schoolIdForStudentScore').select2({multiple:true});
					schoolIds =$('#kelpa2schoolIdForStudentScore').select2("val");
					if(!districtFilter && !schoolFilter){						
						var orgIds=JSON.parse("[" + $('#userNarrowestOrgId').val() + "]");							
						schoolIds = orgIds;	
						if((schoolIds == null  ||  schoolIds.length < 1 )){
							alert('School selection is required.');
							return;
						}
						
					}
					params.schoolIds = schoolIds;
					$('#kelpa2subjectIdForStudentScore').select2({multiple:true});
					var contentAreaIds = $('#kelpa2subjectIdForStudentScore').select2("val");
					
					
					params.contentAreaIds = contentAreaIds;
					//DE19243 - grade K not displayed for KELPA score extract
					if ($('#kelpa2gradeIdForStudentScore option').length > 0) {
						gradeIds = $('#kelpa2gradeIdForStudentScore').select2("val");
					}
					params.kelpaGradeIds = gradeIds;
					
					schoolYears = $('#kelpa2schoolYearIdForStudentScore').select2("val");
									
					params.schoolYears = schoolYears;						
							
					$(this).dialog('close');
							
						generateExtractReplaceDialog(extractId, extractTypeId, params, button);
					
					
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#dlmTestAdministrationMessage').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#dlmGeneralReseach').hide();
				$('#kapStudentScoreExtractFilter').hide();
				$('#kelpa2studentScoreExtractFilter').show();
				$('#schoolYearIdForStudentScore').prop('disabled', true);
				$('#kelpa2schoolIdForStudentScore').prop('disabled', true);
				$('#kelpa2schoolFilterForStudentScore').hide();
				$('#kelpa2schoolYearFilterForStudentScore').hide();
				$('#kelpa2subjectIdForStudentScore').select2({multiple: true});
				if(districtFilter){
					$('#kelpa2gradeFilterForStudentScore').hide();
				}
				populateFiltersForKelpa2StudentScore();
				
				if(!districtFilter && !schoolFilter){
					getGradesForKelpaStudentScoreExtract($('#userNarrowestOrgId').val(), 'SCH',extractTypeId);
					getSchoolYearsForStudentScoreExtractForKelpa(null);
					
				}
				
			},
			close: function(){
				$('#kelpa2subjectIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();
				$(button).removeAttr('disabled');
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#dlmTestAdministrationMessage').hide();
				$('#kelpa2studentScoreExtractFilter').hide();
			}
		});
		
	}else if(extractTypeId == 51){
		$('#stateStudentIdForStudentScore').val("");
		var scoreDialogWidth = 600;		
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: scoreDialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){					
					var params = {};					
					var studentStateId = $('#stateStudentIdForStudentScore').val().trim();					
					params.studentStateId = studentStateId;				
					var subscores = $('#subScores').is(":checked");				
					$(this).dialog('close');
					
					if (studentStateId == null || studentStateId === '' ){						
							alert('Specific State Student Id is required.');
							return;
					}else {
						validateStudentIdForKapStudentScore(extractId, extractTypeId, params);
					}					
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#dlmTestAdministrationMessage').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#dlmGeneralReseach').hide();
				$('#kelpa2studentScoreExtractFilter').hide();
				$('#kapStudentScoreExtractFilter').hide();	
				$('#kapStudentSpecifiedScoreExtractFilter').show();				
				$('#schoolYearIdForStudentScore').prop('disabled', true);
				$('#schoolIdForStudentScore').prop('disabled', true);
				$('#schoolFilterForStudentScore').hide();
				$('#schoolYearFilterForStudentScore').hide();
				$('#subjectIdForStudentScore').select2({multiple: true});				
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#dlmTestAdministrationMessage').hide();
				$('#kelpa2studentScoreExtractFilter').hide();
				$('#kapStudentScoreExtractFilter').hide();
				$('#kapStudentSpecifiedScoreExtractFilter').hide();
			}
		});
		
	
	}
	else if(extractTypeId == 34 || extractTypeId == 18){
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract Filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					var orgainzationIds = [];
					if (userAccessLevel <= 20 && $('#groupCode').val() == 'PDAD'){
						orgainzationIds = $('#extractStateIdForPDTraining').val();
					}else if(userAccessLevel <= 20){
						orgainzationIds = $('#extractSchoolIdForPDTraining').val();						
						if(orgainzationIds  == null || orgainzationIds < 1){
							orgainzationIds = $('#extractDistrictIdForPDTraining').val();	
						}
					}else if(userAccessLevel <= 50){
						orgainzationIds = $('#extractSchoolIdForPDTraining').val();
					}			
					
					var params = { 	orgainzationIdsForMultiSelect: orgainzationIds};
					$(this).dialog('close');
					if(isQcState){
						params.includeInternalUsers=true;
					}else{
					params.includeInternalUsers=$('#includeInternuluserId').prop('checked');
					}
					generateExtractReplaceDialog(extractId, extractTypeId, params, button);		
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#dlmGeneralReseach').hide();				

				$('#extractSchoolIdForPDTraining').val('').select2().trigger('change.select2');
				$('#extractDistrictIdForPDTraining').val('').select2().trigger('change.select2');
				$('#extractStateIdForPDTraining').val('').select2().trigger('change.select2');
				
				$('#stateExtractFilterForPDTraining').show();
				$('#districtExtractFilterForPDTraining').show();
				$('#schoolExtractFilterForPDTraining').show();
				$('#extractSubFilterForPDTraining').show();
				$('#extractSchoolIdForSecurityExtract, #extractDistrictIdForSecurityExtract, #extractStateIdForSecurityExtract, #extractAssessmentProgramId').select2({
					placeholder : 'Select',
					multiple : true
				});
				loadDefaultFiltersForPDTrainingStatus();
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').show();
				$('#summaryLevelExtractFilter').show();
				$('#extractFilterDropdowns').show();
				$('#stateExtractFilterForPDTraining').hide();
				$('#districtExtractFilterForPDTraining').hide();
				$('#schoolExtractFilterForPDTraining').hide();
				$('#extractSubFilterForPDTraining').hide();
			}
		});
		
	}else if(extractTypeId == 35){
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract Filters',
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					var orgainzationIds = [];
					orgainzationIds = $('#extractSchoolIdForSecurityExtract').val();
					if(orgainzationIds == null || orgainzationIds.length == 0){
						orgainzationIds = $('#extractDistrictIdForSecurityExtract').val();
					}
					if(orgainzationIds == null || orgainzationIds.length == 0){
						orgainzationIds = $('#extractStateIdForSecurityExtract').val();
					}
					
					var params = { 	orgainzationIdsForMultiSelect: orgainzationIds};
					$(this).dialog('close');
					if(isQcState){
						params.includeInternalUsers=true;
					}else{
					params.includeInternalUsers=$('#includeInternuluserId').prop('checked');
					}
					params.assessmentProgramIds = $('#extractAssessmentProgramId').select2("val");
					if (params.assessmentProgramIds == null || params.assessmentProgramIds.length < 1 ){
						alert('Assessment Program is required.');
						return;
					}
					generateExtractReplaceDialog(extractId, extractTypeId, params, button);		
					
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#dlmGeneralReseach').hide();
				// These are requried to hide PD Training popup
				$('#stateExtractFilterForPDTraining').hide();
				$('#districtExtractFilterForPDTraining').hide();
				$('#schoolExtractFilterForPDTraining').hide();
				$('#extractSubFilterForPDTraining').hide();
				
				// For AP dropdonw
				$('#extractFilterDropdowns').show();
				$('#stateExtractFilter').hide();
				$('#assessmentProgramForStudentExtractFilter').show();
				$('#districtExtractFilter').hide();
				$('#schoolExtractFilter').hide();
				$('#gradeExtractFilter').hide();
				$('#subjectExtractFilter').hide();
				$('#rosterExtractFilter').hide();				
				
				$('#assessmentProgramForStudentExtractFilter').show().find('select').removeAttr('disabled');				
				$('#extractSchoolIdForSecurityExtract').val('').select2().trigger('change.select2');
				$('#extractDistrictIdForSecurityExtract').val('').select2().trigger('change.select2');
				$('#extractStateIdForSecurityExtract').val('').select2().trigger('change.select2');
				$('#extractAssessmentProgramId').val('').select2().trigger('change.select2');
				$('#stateExtractFilterForSecurityExtract').show();
				$('#districtExtractFilterForSecurityExtract').show();
				$('#schoolExtractFilterForSecurityExtract').show();
				$('#extractSubFilterForSecurityExtract').show();
				$('#extractSchoolIdForSecurityExtract, #extractDistrictIdForSecurityExtract, #extractStateIdForSecurityExtract, #extractAssessmentProgramId').select2({
					placeholder : 'Select',
					multiple : true
				});
				loadDefaultFiltersForSecurityExtract(extractTypeId);
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#ksdeExtractFilter').hide();
				$('#testFormAssignmentLevelExtractFilter').show();
				$('#summaryLevelExtractFilter').show();
				$('#extractFilterDropdowns').show();
				
				$('#stateExtractFilterForSecurityExtract').hide();
				$('#districtExtractFilterForSecurityExtract').hide();
				$('#schoolExtractFilterForSecurityExtract').hide();
				$('#extractSubFilterForSecurityExtract').hide();
			}
		});
		
	}else if(extractTypeId == 39){
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract Filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					var params = {
						stateId: $('#itiBluePrintExtractStateId').val(),
						districtId: $('#itiBluePrintExtractDistrictId').val(),
						schoolId: $('#itiBluePrintExtractSchoolId').val(),						
					};
					var extractSummaryLevel;
					if(extractFiltersAvailable){
						extractSummaryLevel = $('input[name="itiBluePrintextractSummaryLevel"]:checked').val();
					} else {
						extractSummaryLevel = $('input[name="itiBluePrintextractSummaryLevel"][value="70"]').val();
					}						
					if (typeof(extractSummaryLevel) != 'undefined'){
						$('#itiBluePrintSummaryErrorMsg').empty();
						params.extractSummaryLevel = extractSummaryLevel;							
						if(extractSummaryLevel == 20){
							if(params.districtId != '' || params.schoolId != ''  ){						
								params.districtId = '';
								params.schoolId = '';
							}
						}else if(extractSummaryLevel == 50){
							if(params.schoolId != ''  ){
								params.schoolId = '';
								}
							}							
						} else {
							alert('Please select a summary level.');
							return;
						}					
					params.subjectId = $('#itiSubjectExtractFilter').val(); 
					params.gradeId = $('#itiGradeExtractFilter').val();
					params.groupByTeacher = $('#groupByTeacher').is(":checked");
					if (stateFilterRequired && params.stateId === ''){
						alert('Please select a state.');
					} else {
						$(this).dialog('close');
						generateExtractReplaceDialog(extractId, extractTypeId, params, button);
					}
				}},
				{
				text:"Cancel",
				title:'Cancel',					
				click: function(){
					$(this).dialog('close');					
					initializeDates();
				}
			}],
			open: function(){
				$('#itiblueprintSummaryLevelFilter').hide().find('input:radio:checked').each(function(){
					$(this).prop('checked', false);
				});
				loadCustomFiltersForItiBluePrintSummary();				
				$('#groupByTeacher').prop('checked', false);
				$('#dataExtractFilterDialog').find('select').not('#extractQCCompleteStatus').each(function(){
					$(this).find('option:not(:first)').remove();
				});
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();				
				$('#ksdeExtractFilter').hide();
				$('#dlmGeneralReseach').hide();
				$('#testFormAssignmentLevelExtractFilter').hide();
				if(extractFiltersAvailable) {
					$('#itiblueprintSummaryLevelFilter').show();
				} else {
					$('#itiblueprintSummaryLevelFilter').hide();
					$('#itiBluePrintExtractFilterDropdowns').show();
					if($('#groupCode').val() == 'TEA') {
						$('#groupByCheckBoxForITIBP').hide();
					} else{
						$('#groupByCheckBoxForITIBP').show();
					}
					getSubjectsForITIBpExtract();					
				}
								
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#itiblueprintSummaryLevelFilter').hide();
				$('#itiBluePrintExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#summaryLevelExtractFilter').show();
				$('#extractFilterDropdowns').show();
				$('groupByTeacher').prop('checked', false);
				initializeDates();
			}
		});
		
	}else if(extractTypeId == 44){
		var dialog = $('#extractFiltersForOrganization').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract Filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					var params = {
						stateId: $('#organizationExtractStateId').val(),
						districtId: $('#organizationExtractDistrictId').val(),
						schoolId: $('#organizationExtractSchoolId').val(),
						includeInactiveOrganizations: $('#includeInactiveOrganizations').prop('checked')
						
					};
					var organizationextractSummaryLevel = $('input[name="organizationextractSummaryLevel"]:checked').val();
											
					if (typeof(organizationextractSummaryLevel) != 'undefined'){
						$('#organizationSummaryErrorMsg').empty();
						params.organizationextractSummaryLevel = organizationextractSummaryLevel;							
						if(organizationextractSummaryLevel == 20){
							if(params.districtId != '' || params.schoolId != ''  ){						
								params.districtId = '';
								params.schoolId = '';
							}
						}else if(organizationextractSummaryLevel == 50){
							if(params.schoolId != ''  ){
								params.schoolId = '';
								}
							}							
						} else {
							alert('Please select a summary level.');
							return;
						}					
				
					if (stateFilterRequired && params.stateId === ''){
						alert('Please select a state.');
					} else {
						$(this).dialog('close');
						generateExtractReplaceDialog(extractId, extractTypeId, params, button);
					}
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');			
					
				}
			}],
			open: function(){
				$('#organizationSummaryLevelFilter').hide().find('input:radio:checked').each(function(){
					$(this).prop('checked', false);
				});
				loadCustomFiltersForOrganizationSummary();				
				
				$('#summaryLevelExtractFilter').hide();
				$('#extractFilterDropdowns').hide();				
				$('#ksdeExtractFilter').hide();
				$('#dlmGeneralReseach').hide();
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#organizationSummaryLevelFilter').show();
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#organizationSummaryLevelFilter').hide();
				$('#organizationExtractFilterDropdowns').hide();
				$('#ksdeExtractFilter').hide();
				$('#summaryLevelExtractFilter').show();
				$('#extractFilterDropdowns').show();
			}
		});
		
	} else if (extractTypeId == 48){
		var dialog = $('#dataExtractFilterDialog').dialog({
			resizable: false,
			width: dialogWidth,
			modal: true,
			autoOpen: true,
			title: 'Create Extract Filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text: 'Ok',
				title:'Ok',
				click: function(){
					$(this).dialog('close');
					var params = {
						stateId: $('#extractStateId').val(),
						districtId: $('#extractDistrictId').val(),
						schoolId: $('#extractSchoolId').val(),
						assessmentProgramIds: $('#extractAssessmentProgramId').select2('val'),
						includeAllStudents: $('#pnpAbridgedFilters #includeAllStudents').prop('checked'),
						fileType: $('#pnpAbridgedFilters input[name="fileType"]:checked').val()
					};
					
					generateExtractReplaceDialog(extractId, extractTypeId, params, button);
				}
			},
			{
				text: 'Cancel',
				title:'Cancel',
				click: function(){
					$(this).dialog('close');			
				}
			}],
			open: function(){
				$('#pnpAbridgedFilters #includeAllStudents').prop('checked', false);
				$('#pnpAbridgedFilters').show();
				$('#dlmGeneralReseach').hide();
				$('#summaryLevelExtractFilter').hide().find('input:radio:checked').each(function(){
					$(this).prop('checked', false);
				});
				$('#dataExtractFilterDialog').find('select').not('#extractAssessmentProgramId, #extractQCCompleteStatus').each(function(){
					$(this).find('option:not(:first)').remove();
				});
				
				if(stateFilterRequired || districtFilter || schoolFilter){				
					$("#extractAssessmentProgramId").find('option:not(:first)').remove();
				}			
				$('#testFormAssignmentLevelExtractFilter').hide();
				loadStudentLoginPasswordFilters(false);
				$('#dlmTestAdministrationMessage').hide();
				$('#extractAssessmentProgramId').val('').select2().trigger('change.select2');
				loadInitialOrgs(extractTypeId, null, null, null);
				populateAssessmentProgramsForExtract(null, extractTypeId);
			},
			close: function(){
				$(button).removeAttr('disabled');
				$('#testFormAssignmentLevelExtractFilter').show();
				$('#dlmTestAdministrationMessage').hide();
				$('#ksdeExtractFilter').hide();
			}
		});
	} else {
		if(extractTypeId == 25){
			var schoolId = '';
			if(extractTypeId == 25){
				schoolId = $('#userNarrowestOrgId').val();
			} 
			populateAssessmentProgramsForExtract(null, extractTypeId);
			getGrades(schoolId);
		}else{
			populateAssessmentProgramsForExtract(null, extractTypeId);
		}
		$('#extractSummaryErrorMsg').empty();
		$('#dataExtractFilterDialog').dialog({
		resizable: false,
		width: dialogWidth,
		modal: true,
		autoOpen: true,
		title: 'Create Extract filters',
		create: function(event, ui){
			var widget = $(this).dialog("widget");
		},
		buttons: [{
			text:"Ok",
			title:'Ok',
			click: function(){
				var params = {
						stateId: $('#extractStateId').val(),
						districtId: $('#extractDistrictId').val(),
						schoolId: $('#extractSchoolId').val(),
						assessmentProgramIds : $('#extractAssessmentProgramId').select2("val")
					};
					if (extractTypeId == 10){ // PNP Summary
						var extractSummaryLevel = $('input[name="extractSummaryLevel"]:checked').val();
						if (typeof(extractSummaryLevel) != 'undefined'){
							$('#extractSummaryErrorMsg').empty();
							params.extractSummaryLevel = extractSummaryLevel;							
							if(extractSummaryLevel == 20){
								if(params.districtId != '' || params.schoolId != ''  ){
									params.districtId = '';
									params.schoolId = '';
								}
							}else if(extractSummaryLevel == 50){
								if(params.schoolId != ''  ){
									params.schoolId = '';
								}
							}
							
						} else {
							alert('Please select a summary level.');
							return;
						}
					}
					if(extractTypeId == 25) {
						var subjectList = [];
						var subjectIds=	$('select#extractSubjectId option:selected');
						$.each(subjectIds, function(i, item) {
							subjectList.push({"apId":$(item).attr('apId'), "apCode":$(item).attr('apCode'), "subjectId":$(item).val()});
						});
						params.gradeAbbreviatedName = $('#extractGradeId').val();
						params.assessmentSubjectIds = JSON.stringify(subjectList);
						params.assessmentProgramIds = $('#extractAssessmentProgramId').select2("val");
						params.csvDownload = $('#CSVdownload').prop('checked');
						params.pdfDownload = $('#PDFdownload').prop('checked');
						
						
						if($('#userDefaultGroup').val() < 70) {
							if ((params.districtId == null) || (params.districtId == "") )
							{
								alert('Please choose District');
								return;
							}
						}
						
						if (!params.csvDownload && !params.pdfDownload)
						{
							alert('Please choose any one Download format');
							return;
						}
					}
					if(extractTypeId == 53){						
						populateFiltersForStudentScore();
						if($("#extractDistrictId").val()=="" && districtFilter)
							{
							alert("Select District");
							return;
							}
					}
					if(extractTypeId == 54) {
						if(userAccessLevel < 50){
							//verify only if the user isn't at district level or school level
							if($('#extractDistrictId')==null || $('#extractDistrictId').val()===''){
								alert('Please select District.');
								return;
							}
						}
					}
					if (params.assessmentProgramIds == null || params.assessmentProgramIds.length < 1 ){
						alert('Assessment Program is required.');
						return;
					}	
					if (stateFilterRequired && params.stateId === ''){
						alert('Please select a state.');
					} else {
						$(this).dialog('close');
						if(isQcState){
							params.includeInternalUsers=true;
						}else{
						params.includeInternalUsers=$('#includeInternuluserId').prop('checked');
						}
						generateExtractReplaceDialog(extractId, extractTypeId, params, button);
					}
			}},
			{
				text:"Cancel",
				title:'Cancel',
				click: function(){
				$(this).dialog('close');
				/*Re-initialize dates*/
				initializeDates();
			}
		}],
		open: function(){
			$('#dlmGeneralReseach').hide();
			$('#summaryLevelExtractFilter').hide().find('input:radio:checked').each(function(){
				$(this).prop('checked', false);
			});
			$('#dataExtractFilterDialog').find('select').not('#extractAssessmentProgramId, #extractQCCompleteStatus').each(function(){
				$(this).find('option:not(:first)').remove();
			});
			
			if(stateFilterRequired || districtFilter || schoolFilter){				
				$("#extractAssessmentProgramId").find('option:not(:first)').remove();
			}			
			
			if (extractTypeId == 10 || extractTypeId == 45){ // PNP Summary
				loadCustomFiltersForPNPSummary();
			} else {
				loadInitialOrgs(extractTypeId, null, null, null);
			}
			$('#testFormAssignmentLevelExtractFilter').hide();
			if(extractTypeId == 25) {
				loadStudentLoginPasswordFilters(true);
			} else {
				loadStudentLoginPasswordFilters(false);
			}
			if(extractTypeId == 8)
			{
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#ksdeExtractFilter').hide();
				$('#extractFilterDropdowns').show();
				$('#dlmTestAdministrationMessage').show();
			} else {
				$('#dlmTestAdministrationMessage').hide();
			}
			if(extractTypeId == 46)
			{
				params.assessmentProgramIds = $('#extractAssessmentProgramId').select2("val");
			} else if(extractTypeId == 54){
				//PLTW testing Readiness
				if($('#extractAssessmentProgramId')!=null && $('#extractAssessmentProgramId').select2("val")!=null)
					$('#extractAssessmentProgramId').select2().trigger('change.select2');
			}else if(extractTypeId == 25){
				$('#extractAssessmentProgramId').select2().trigger('change.select2');
			}
			else {
				$('#extractAssessmentProgramId').val('').select2().trigger('change.select2');
			}

			//ISMART test admin extract
			if(extractTypeId == 50){
				$('#testFormAssignmentLevelExtractFilter').hide();
				$('#summaryLevelExtractFilter').hide();
				$('#ksdeExtractFilter').hide();
				$('#dateSelectionFilter').hide();
				$('#dlmTestAdministrationMessage').hide();
			}
			if(extractTypeId == 53 || extractTypeId == 54){
				$("#pltwFont").removeAttr('hidden');
			}
		},
		close: function(){
			$('#districtExtractFilter label span').remove();
			$( ".ui-dialog-buttonpane p" ).remove();
			$(button).removeAttr('disabled');
			$('#testFormAssignmentLevelExtractFilter').show();
			$('#dlmTestAdministrationMessage').hide();
			$('#ksdeExtractFilter').hide();
			if(extractTypeId == 8)
			{
				$('#dateSelectionFilter').hide();
			}
			if(extractTypeId == 53 || extractTypeId == 54)
			{
				$("#pltwFont").attr("hidden","true");
			}
		}
	});
	
	}
}

function showFilterDropdowns(state, district, school){
	if (state){
		$('#stateExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#stateExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
	
	if (district){
		$('#districtExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#districtExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
	
	if (school){
		$('#schoolExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#schoolExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
}

function showItiBluePrintFilterDropdowns(state, district, school){	
	if (state){
		$('#itiBluePrintStateExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#itiBluePrintStateExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
	
	if (district){
		$('#itiBluePrintDistrictExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#itiBluePrintDistrictExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
	
	if (school){
		$('#itiBluePrintSchoolExtractFilter').show().find('select').removeAttr('disabled');		
	} else {
		$('#itiBluePrintSchoolExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');		
	}
}


function showOrganizationFilterDropdowns(state, district, school){	
	if (state){
		$('#organizationStateExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#organizationStateExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
	
	if (district){
		$('#organizationDistrictExtractFilter').show().find('select').removeAttr('disabled');
	} else {
		$('#organizationDistrictExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
	
	if (school){
		$('#organizationSchoolExtractFilter').show().find('select').removeAttr('disabled');		
	} else {
		$('#organizationSchoolExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');		
	}
}

function loadCustomFiltersForPNPSummary(){
	var customFilter = $('#summaryLevelExtractFilter');
	customFilter.show();
	$('#extractFilterDropdowns').hide();
	if (userAccessLevel > 20){
		customFilter.find('input[value="20"]').attr('disabled', 'disabled');
		customFilter.find('#extractSummaryFilterStateSpan').hide();
	}
	if (userAccessLevel > 50){
		customFilter.find('input[value="50"]').attr('disabled', 'disabled');
		customFilter.find('#extractSummaryFilterDistrictSpan').hide();
	}
}

function loadCustomFiltersForItiBluePrintSummary(){
	var customFilter = $('#itiblueprintSummaryLevelFilter');
	customFilter.show();
	$('#itiBluePrintExtractFilterDropdowns').hide();
	if (userAccessLevel > 20){
		customFilter.find('input[value="20"]').attr('disabled', 'disabled');
		customFilter.find('#itiBluePrintSummaryFilterStateSpan').hide();
	}
	if (userAccessLevel > 50){
		customFilter.find('input[value="50"]').attr('disabled', 'disabled');
		customFilter.find('#itiBluePrintSummaryFilterDistrictSpan').hide();
	}
}


function loadCustomFiltersForOrganizationSummary(){
	var customFilter = $('#organizationSummaryLevelFilter');
	customFilter.show();
	$('#organizationExtractFilterDropdowns').hide();
	if (userAccessLevel > 20){
		customFilter.find('input[value="20"]').attr('disabled', 'disabled');
		customFilter.find('#organizationSummaryFilterStateSpan').hide();
	}
	if (userAccessLevel > 50){
		customFilter.find('input[value="50"]').attr('disabled', 'disabled');
		customFilter.find('#organizationSummaryFilterDistrictSpan').hide();
	}
}

function getAssessmentProgram(extractTypeId,params){
	var count=0;
	var apSelect;
	var apSelect = $('#extractAssessmentProgram');
	var optionText='';
	
	$('.messages').html('').hide();
	apSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	$.ajax({
		url: 'getCurrentUserAssessmentPrograms.htm',
		dataType: 'json',
		type: "POST",
		success: function(assessmentPrograms) {				
			if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
				$.each(assessmentPrograms, function(i, assessmentProgram) {
					if(assessmentPrograms[i].abbreviatedname == 'KAP' || assessmentPrograms[i].abbreviatedname == 'DLM' 
						|| assessmentPrograms[i].abbreviatedname == 'CPASS' || assessmentPrograms[i].abbreviatedname == 'AMP'
							|| assessmentPrograms[i].abbreviatedname == 'KELPA2'){
					optionText = assessmentPrograms[i].abbreviatedname;
					if(assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
						apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(assessmentProgram.id).html(optionText));
						if(params){
							if(!params.assessmentProgramIds){
								params.assessmentProgramIds=[];
								params.assessmentProgramIds[0]=data[i].id;
							}
						}
					} else {
					    apSelect.append($('<option></option>').val(assessmentProgram.id).html(optionText));
					}
					count++;
					}
				});	
				apSelect.trigger('change');
				apSelect.select2();
                
                if (assessmentPrograms.length != 1) {
                    apSelect.prop('disabled', false);
                }
                apSelect.trigger('change.select2');
			} 
		}
	});
}

function loadDefaultFiltersForPDTrainingStatus(){
	var select;	
	if (userAccessLevel <= 20 && $('#groupCode').val() == 'PDAD'){
		select = $('#extractStateIdForPDTraining');
		$('#districtExtractFilterForPDTraining').hide();
		$('#schoolExtractFilterForPDTraining').hide();
	} else if (userAccessLevel <= 20){
		$('#stateExtractFilterForPDTraining').hide();
		select = $('#extractDistrictIdForPDTraining');
	}else if (userAccessLevel <= 50){
		$('#stateExtractFilterForPDTraining').hide();
		$('#districtExtractFilterForPDTraining').hide();
		select = $('#extractSchoolIdForPDTraining');
	} else {
		return;
	}
	var role = $('#groupCode').val();
	if(role == null || role == '' || role == 'undefined'){
		return;
	}
	//select.find('option').remove();
	$('#extractStateIdForPDTraining').find('option').remove();
	$('#extractDistrictIdForPDTraining').find('option').remove();
	$('#extractSchoolIdForPDTraining').find('option').remove();

	$.ajax({
		url: 'getOrgsForExtractFiltersPDStatus.htm',
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){				
				for (var i = 0; i < data.length; i++){
					select.append($('<option>', {value: data[i].id, text: data[i].organizationName}));
				}
				select.select2();
				if (data.length == 1){
					select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					if (userAccessLevel <= 20 && $('#groupCode').val() == 'PDAD'){
					  select.trigger('change');
					}
				}
				select.trigger('change.select2');   
				$('#districtExtractFilterForPDTraining>span').css("width", "62%");
				$('#schoolExtractFilterForPDTraining>span').css("width", "62%");

				//showFilterDropdownsForPDStatus($('#groupCode').val() == 'PDAD', $('#groupCode').val() == 'SAAD', $('#groupCode').val() == 'SAAD');
        	} else {
        		// error
        	}
		}
	});
}

function loadDefaultFiltersForSecurityExtract(extractTypeId){
	var select;
	if (stateFilterRequired){
		select = $('#extractStateIdForSecurityExtract');
		$('#districtExtractFilterForSecurityExtract').show();
		$('#schoolExtractFilterForSecurityExtract').show();
		// Just refresh dependents		
		$('#extractDistrictIdForSecurityExtract').val('').select2().trigger('change.select2');
		$('#extractSchoolIdForSecurityExtract').val('').select2().trigger('change.select2');
		
	} else if (districtFilter){
		select = $('#extractDistrictIdForSecurityExtract');
		$('#stateExtractFilterForSecurityExtract').hide();
		// Just refresh dependents		
		$('#extractStateIdForSecurityExtract').val('').select2().trigger('change.select2');
	} else if (schoolFilter){
		select = $('#extractSchoolIdForSecurityExtract');
		$('#stateExtractFilterForSecurityExtract').hide();
		$('#districtExtractFilterForSecurityExtract').hide();
	}
	
	var role = $('#groupCode').val();
	if(role == null || role == '' || role == 'undefined'){
		return;
	}
	$('#extractStateIdForSecurityExtract').find('option').remove();
	$('#extractDistrictIdForSecurityExtract').find('option').remove();
	$('#extractSchoolIdForSecurityExtract').find('option').remove();

	$.ajax({
		url: 'getOrgsForExtractFiltersMultiSelect.htm',
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
				populateAssessmentProgramsForExtract(null, extractTypeId);
				for (var i = 0; i < data.length; i++){
					select.append($('<option>', {value: data[i].id, text: data[i].organizationName}));
				}
				select.select2();
				if (data.length == 1){
					select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');					
					select.trigger('change.select2');
				}
				select.val('').select2().trigger('change.select2');
					
				$('#assessmentProgramForStudentExtractFilter>span').css("width", "60%");
				$('#schoolExtractFilterForSecurityExtract>span').css("width", "75%");
				$('#districtExtractFilterForSecurityExtract>span').css("width", "75%");
        	} else {
        		// Do nothing.
        	}
		}
	});
}
function getSchoolsForGRF(districtId){
	var stateId = $('#userNarrowestOrgId').val();
	var select = $('#dlmGeneralReseachExtractSchoolId');
	select.val('').find('option:not(:first)').remove();
	//var url = 'getSchoolsForUser.htm';
	var data = {};
	if (typeof(districtId) != 'undefined'){
		if (districtId == ''){
			return;
		}
		url = 'getOrgsForGRFFilters.htm';		
	
		data.districtId = districtId;
		data.stateId = stateId;	
		data.districtId = districtId;			
		
		data.orgType = "SCH";
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'POST',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
        		//populateOrgSelectForGRFSchools(select, data);
				populateOrgSelectForGRF(select, data);
        	} else {
        		// error
        	}
		}
	});
	
}

function populateOrgSelectForGRFSchools($select, orgs){
	for (var i = 0; i < orgs.length; i++){
		$select.append($('<option>', {value: orgs[i].schoolId, text: orgs[i].schoolName}));
	}
	if (orgs.length == 1){
		$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
		$select.trigger('change');
	} else {
		$select.prop('disabled', false);
	}
}

function getSubjectForGRF(schoolId){
	var data = {};	
	var stateId = $('#userNarrowestOrgId').val();
	var districtId = $('#dlmGeneralReseachExtractDistrictId').val();
	
	if(districtFilter){
		data.districtId = districtId;
		data.stateId = stateId;
		data.schoolId = schoolId;		
	} else if(schoolFilter){
		data.districtId = stateId;
		data.schoolId = schoolId;	
	}
	

	
	
	select = $('#dlmGeneralReseachSubjectExtractFilter');
	select.val('').find('option:not(:first)').remove();	
	
	if(schoolId != ''){
	$.ajax({
		url: 'getContentAreasforGRF.htm',
		dataType: 'json',
		data: data,		
		type: 'GET',
		success: function(data) {				
			for (var i = 0; i < data.length; i++){	
				select.append($('<option>', {value: data[i].id, text: data[i].abbreviatedName}));
			}
			if (data.length == 1){
				select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				select.trigger('change');
			} else {
				select.prop('disabled', false);
			}
		}
	});
	}
}

function getGradeForGRF(contentAreaId){	
	
	var param = {};
	var stateId = $('#userNarrowestOrgId').val();
	var districtId = $('#dlmGeneralReseachExtractDistrictId').val();
	var schoolId = $('#dlmGeneralReseachExtractSchoolId').val();
	
	if(districtFilter){
		param.contentAreaId = contentAreaId;
		param.stateId =stateId;
		param.districtId = districtId;
		param.schoolId = schoolId;		
	} else if(schoolFilter){
		param.districtId = stateId;
		param.schoolId = schoolId;		
		param.contentAreaId = contentAreaId;
	}
	
	
	
	select = $('#dlmGeneralReseachGradeExtractFilter');
	select.val('').find('option:not(:first)').remove();
	if(contentAreaId != ''){		
	$.ajax({
		url: 'getGradeCourseByGRF.htm',
		data: param, 
		dataType: 'json',		
		type: 'GET',
		success: function(data) {
			
			for (var i = 0; i < data.length; i++){
				select.append($('<option>', {value: data[i].id, text: data[i].abbreviatedName}));
			}
			if (data.length == 1){
				select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				select.trigger('change');
			} else {
				select.prop('disabled', false);
			}			
		}
	});	
	}
	
}



function loadInitialOrgsForGRF(extractTypeId, showState, showDistrict, showSchool,stateId){
	
	var select;
	if (stateFilterRequired){
		select = $('#dlmGeneralReseachExtractDistrictId');
	} else if (districtFilter){
		select = $('#dlmGeneralReseachExtractDistrictId');
	} else if (schoolFilter){
		select = $('#dlmGeneralReseachExtractSchoolId');
		
	} else {
		return;
	}
	select.find('option:not(:first)').remove();
	$.ajax({
		//url: 'getOrgsForExtractFilters.htm',
		url: 'getOrgsForGRFFilters.htm',
		dataType: 'json',
		data: {
				stateId:stateId,
				orgType:"DT",		
		     },
		type: 'POST',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelectForGRF(select, data);        		
					 $('#dlmGRFErrorMsg').hide();			    
				 
        	} else {
        		 $('#dlmGRFErrorMsg').show();
        		// error
        	}
		}
	});
}


function populateOrgSelectForGRF($select, orgs){
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


function loadInitialOrgs(extractTypeId, showState, showDistrict, showSchool){
	if(extractTypeId == 10 || extractTypeId == 45 )
    {
		// PNP summary custom filter handling
		showFilterDropdowns(showState, showDistrict, showSchool);
    }
	var select;
	if (stateFilterRequired){
		select = $('#extractStateId');
	} else if (districtFilter){
		select = $('#extractDistrictId');
	} else if (schoolFilter){
		select = $('#extractSchoolId');
	} else {
		return;
	}
	select.find('option:not(:first)').remove();
	$.ajax({
		url: 'getOrgsForExtractFilters.htm',
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        		populateAssessmentProgramsForExtract(null, extractTypeId);
        		if(extractTypeId != 10) {
    				showFilterDropdowns(stateFilterRequired, districtFilter, schoolFilter);
    			}
        		$('#extractFilterDropdowns').show();        		
        	} else {
        		// error
        	}
		}
	});
}
function loadInitialOrgsForITIBluePrint(showState, showDistrict, showSchool){	
	var select;
	if (stateFilterRequired){
		select = $('#itiBluePrintExtractStateId');		
	} else if (districtFilter){
		select = $('#itiBluePrintExtractDistrictId');
	} else if (schoolFilter){
		select = $('#itiBluePrintExtractSchoolId');
	} else {
		return;
	}
	select.find('option:not(:first)').remove();
	$.ajax({
		url: 'getOrgsForExtractFilters.htm',
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        		$('#itiBluePrintExtractFilterDropdowns').show();     
        		showItiBluePrintFilterDropdowns(showState, showDistrict, showSchool);        		   	
        	} else {
        		// error
        	}
		}
	});	
}
function loadInitialOrgsForOrganization(showState, showDistrict, showSchool){	
	var select;
	if (stateFilterRequired){
		select = $('#organizationExtractStateId');		
	} else if (districtFilter){
		select = $('#organizationExtractDistrictId');
	} else if (schoolFilter){
		select = $('#organizationExtractSchoolId');
	} else {
		return;
	}
	select.find('option:not(:first)').remove();
	$.ajax({
		url: 'getOrgsForExtractFilters.htm',
		dataType: 'json',
		data:{isInactiveOrgReq: true},
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        		$('#organizationExtractFilterDropdowns').show();     
        		showOrganizationFilterDropdowns(showState, showDistrict, showSchool);        		   	
        	} else {
        		// error
        	}
		}
	});	
}
function getDistricts(stateId){
	var select = $('#extractDistrictId');
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
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateOrgSelect(select, data);
			} else {
				// error
			}
		}
	});
}

function getDistrictsForOrganization(stateId){
	var select = $('#organizationExtractDistrictId');
	select.val('').find('option:not(:first)').remove();
	var url = 'getDistrictsForUser.htm';
	var data = {};
	if (typeof(stateId) != 'undefined'){
		if (stateId == ''){
			return;
		}
		url = 'getDistrictsInState.htm';
		data.stateId = stateId;
		data.isInactiveOrgReq = true;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateOrgSelect(select, data);
			} else {
				// error
			}
		}
	});
}

function getSchools(districtId){
	var select = $('#extractSchoolId');
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
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        	} else {
        		// error
        	}
		}
	});
}

function getSchoolsForItiBluePrint(districtId){
	var select = $('#itiBluePrintExtractSchoolId');
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
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        	} else {
        		// error
        	}
		}
	});
}

function getSchoolsForOrganization(districtId){
	var select = $('#organizationExtractSchoolId');
	select.val('').find('option:not(:first)').remove();
	var url = 'getSchoolsForUser.htm';
	var data = {};
	if (typeof(districtId) != 'undefined'){
		if (districtId == ''){
			return;
		}
		url = 'getSchoolsInDistrict.htm';
		data.districtId = districtId;
		data.isInactiveOrgReq = true;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        	} else {
        		// error
        	}
		}
	});
}

function getSchoolsForPDStatus(districtId){
	var select = $('#extractSchoolIdForPDTraining');
	select.val('').find('option').remove();
	var data = {};
		if (districtId == null || districtId == '' || districtId.length == 0){
			$('#extractSchoolIdForPDTraining').val('').select2().trigger('change.select2');
			
			return;
		}
		url = 'getSchoolsInDistrictForPDStatus.htm';
		data.districtId = districtId;
	
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'POST',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				for (var i = 0; i < data.length; i++){
					select.append($('<option>', {value: data[i].id, text: data[i].organizationName}));
				}
				select.select2();
				if (data.length == 1){
					$('#extractSchoolIdForPDTraining option').prop('selected', true);
				}
				select.trigger('change.select2');  
        	} else {
        		// error
        	}
		}
	});
}

function populateOrgSelect($select, orgs){
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

function populateMultiOrgSelect($select, orgs){
	$select.find('option').remove();
	for (var i = 0; i < orgs.length; i++){
		$select.append($('<option>', {value: orgs[i].id, text: orgs[i].organizationName}));
	}
	if (orgs.length == 1){
		$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
		$select.trigger('change');
	} else {
		$select.prop('disabled', false);
	}
	$select.trigger('change.select2');  
}

function validateStudentID(extractId, extractTypeId, params){
	params.extractId = extractId;
	params.extractTypeId = extractTypeId;
	
	var button = $('#newFile' + extractTypeId).attr('disabled', 'disabled');
	$.ajax({
		type: 'POST',
		url: 'validateExtract.htm',
		dataType: 'json',
		data: params,
		success: function(data){
			if( data == -1) 
			{
				validateStudentIDtReplaceDialog(extractId, extractTypeId, params, button);
			}
			else if(data == 0)
			{
				generateExtractReplaceDialog(extractId, extractTypeId, params, button);	
			}
		},
		complete: function(){
			$(button).removeAttr('disabled');
		}
	});
}

function validateStudentIDtReplaceDialog(extractId, extractTypeId, params, button){
		var dialog = $('#dataExtractNewFileDialog').dialog({
			resizable: false,
			height: 200,
			width: 500,
			modal: true,
			autoOpen: true,
			title: 'Validate extract filters',
			create: function(event, ui){
				var widget = $(this).dialog("widget");
			},
			buttons: [{
				text:"Ok",
				title:'Ok',
				click: function(){
					$(this).dialog('close');
					generateExtractFilterDialog(extractId, extractTypeId, button);
					return;
				}},
				{
				text:"Cancel",
				title:'Cancel',
				click: function(){
					$(this).dialog('close');
				}
			}],
			open: function(){
				$(button).attr('disabled', 'disabled');
			},
			close: function(){
				$(button).removeAttr('disabled');
			}
		});
		
		
		if(extractTypeId == 38 || extractTypeId == 51 || extractTypeId == 56){
			dialog.html("Student information not available.");
		}else if(extractTypeId == 52 || extractTypeId == 11){
			dialog.html("State Student ID does not exist.");
		}
		
		//State Student ID does not exist for the current school year. Please enter a valid State Student ID.
		dialog.dialog("open");
	
}

function generateExtractReplaceDialog(extractId, extractTypeId, params, button){
	extractTypeIdCheck = extractTypeId;
	if (extractId > 0){
		if(extractTypeId == 19 || extractTypeId == 20){
			getAssessmentProgram(extractTypeId,params);
		}else if(extractTypeId != 34 && extractTypeId != 18 && extractTypeId != 38 && extractTypeId != 39 && extractTypeId != 51 && extractTypeId != 52 && extractTypeId != 56){
			 populateAssessmentProgramsForExtract(params, extractTypeId);
		}
		var dialog = $('#dataExtractNewFileDialog').dialog({
			resizable: false,
			height: 150,
			width: 500,
			modal: true,
			autoOpen: false,
			height: 'auto',
			title: 'Create Extract Confirmation',	
			buttons: {
				Yes: function(){
					$(this).dialog('close');
					generateExtract(extractId, extractTypeId, params);
				},
				No: function(){
					if(extractTypeId==8 || extractTypeId==19 || extractTypeId==20)
					{
						/*Re-initialize dates*/
						initializeDates();
					}
					$(this).dialog('close');
				}
			},
			open: function(){
				$(button).attr('disabled', 'disabled');
			},
			close: function(){
				$(button).removeAttr('disabled');
				if(extractTypeId==8 || extractTypeId==19 || extractTypeId==20)
				{
					/*Re-initialize dates*/
					initializeDates();
				}
			}
		});
		
		dialog.html("The existing report request and file will be deleted. Do you want to proceed?");
		dialog.dialog("open");
	} else {
		if(extractTypeId != 34 && extractTypeId != 18 && extractTypeId != 38 && extractTypeId != 39 && extractTypeId != 51 && extractTypeId != 52  && extractTypeId != 56)
		{
		   populateAssessmentProgramsForExtract(params, extractTypeId);
		}
		generateExtract(extractId, extractTypeId, params);
	}
}

function initializeDates()
{
	$("#itiFromDate").datepicker({
	    dateFormat: 'mm/dd/yy'
	}).datepicker("setDate", new Date(instructionalStartDate));

	$("#itiToDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker("setDate", new Date(instructionalEndDate));
	
	$("#eoyFromDate").datepicker({
	    dateFormat: 'mm/dd/yy'
	}).datepicker("setDate", new Date(eoyStartDate));
	
	$("#eoyToDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker("setDate", new Date(eoyEndDate));
	
	$("#toDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker("setDate", new Date());
	
	$("#fromDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker("setDate", new Date());	
}

function generateExtract(extractId, extractTypeId, params){
	params.extractId = extractId;
	params.extractTypeId = extractTypeId;
	var button = $('#newFile' + extractTypeId).attr('disabled', 'disabled');
	$.ajax({
		type: 'POST',
		url: 'generateExtract.htm',
		dataType: 'json',
		data: params,
		success: function(data){
			if (data == -1) return;
			var newExtractId = data;
			$('#extractsTable').trigger('reloadGrid');
		}
	});
}

function populateDependentList($select, responseData, displayProperty){
	if (responseData !== undefined && responseData !== null && responseData.length > 0){
		for (var i = 0; i < responseData.length; i++){
			if(localextractTypeId ==25){
				$select.append($('<option>', {value: responseData[i].abbreviatedName, text: responseData[i][displayProperty]}));
			}else{
			$select.append($('<option>', {value: responseData[i].id, text: responseData[i][displayProperty]}));
			}
			}
		if (responseData.length == 1){
			$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
			$select.trigger('change');
		} else {
			$select.prop('disabled', false);
		}
	} else {
		// nothing returned, no action required
	}
}

function getGrades(schoolId){
	var data = {};
	data.schoolId = schoolId;
	$.ajax({
		url: 'selectOrgGradeCourses.htm',
		dataType: 'json',
		data: data,
		type: 'POST',
		success: function(data) {
			$select = $('#gradeExtractFilter').find('select');
			populateDependentList($select, data, 'name');
		}
	});
}
function getSubjectsByGrade(gradeId){
	var getSubjectURL = null;
	var data = {};
	getSubjectURL = 'getSubjectsByGrade.htm';	
	data.gradeId = gradeId;

	$.ajax({
		url: getSubjectURL,
		dataType: 'json',
		data: data,
		type: 'POST',
		success: function(data) {
			$select = $('#subjectExtractFilter').find('select');
			populateDependentList($select, data, 'name');
		}
	});
}

function getSubjectsByGradeAndAssessment(gradeId, assessmentProgramIds){
	var getSubjectURL = null;
	var data = {};
	getSubjectURL = 'getSubjectsByGradeAndAssessment.htm';
	data.assessmentProgramIds = assessmentProgramIds;
	data.gradeId = gradeId;

	$.ajax({
		url: getSubjectURL,
		dataType: 'json',
		data: data,
		type: 'POST',
		success: function(data) {

			$('#extractSubjectId').find('option').filter(function(){return $(this).val() > 0}).remove().end();		
			
			var subjectSelect = $('#extractSubjectId'), optionText = '',organizationId = '';

            if (data !== undefined && data !== null && data.length > 0) {
                for (var i = 0, length = data.length; i < length; i++) {
                    optionText = data[i].name;                    
                    subjectSelect.append($('<option></option>').val(data[i].id).attr({
                    	apCode:data[i].assessmentProgramCode, 
                    	apId:data[i].assessmentProgramId
                    }).html(optionText));					
                }
                subjectSelect.trigger('change.select2');                
                if (data.length != 1) {
                	subjectSelect.prop('disabled', false);
                }                
            } 
			
		}
	});
}

function getRostersBySubject(subjectId, schoolId) {
	var data = {};
	data.subjectId = subjectId;
	if(!extractFiltersAvailable){
		schoolId = $('#userNarrowestOrgId').val();
	}
	data.schoolId = schoolId;
	$.ajax({
		url: 'getRostersBySubject.htm',
		dataType: 'json',
		data: data,
		type: 'POST',
		success: function(data) {
			$('#rosterExtractFilter').show();
			$select = $('#rosterExtractFilter').find('select');
			populateDependentList($select, data, 'courseSectionName');
		}
	});
}

function loadStudentLoginPasswordFilters(display){
	if (display){
		$("#extractSubjectId").prepend("<option value='' selected='selected'>Select</option>");
		$('#CSVdownload').prop('checked', false);
		$('#PDFdownload').prop('checked', false);
		$('#downloadFileTypes').show();
		$('#extractSchoolId').find('option:not(:first)').remove();
		$('#extractGradeId').find('option:not(:first)').remove();
		$('#extractSubjectId').find('option:not(:first)').remove();
		$('#extractSubjectId').select2({multiple: true});
		$("#extractSubjectId option[value='']").remove();
		//$('#extractRosterId').find('option:not(:first)').remove();
		$('#ksdeExtractFilter').hide();
		$('#assessmentProgramForStudentExtractFilter').show().find('select').removeAttr('disabled');
		//$("#extractSubFilter").addClass("hidden");
		$('#gradeExtractFilter').show().find('select').removeAttr('disabled');
		$('#subjectExtractFilter').show().find('select').removeAttr('disabled');
		$('#rosterExtractFilter').addClass("hidden");  	
		$('#districtExtractFilter label').append('<span class="displayRedColor">*</span>');				
		$(".ui-dialog-buttonpane").append('<p><span class="displayRedColor">*</span> = Required</p>');	

	} else {
		$("#extractSubFilter").removeClass("hidden");
		$('#gradeExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
		$('#subjectExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
		//$('#rosterExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
		//$('#assessmentProgramForStudentExtractFilter').hide().find('select').val('').attr('disabled', 'disabled');
	}
}
function populateAssessmentProgramsForExtract(params, extractTypeId){
	
	var apSelect = $('#extractAssessmentProgramId'), optionText = '',organizationId = '';
    apSelect.find('option').filter(function() {
        return $(this).val() > 0
    }).remove().end();
    var currentOrgType=$('#userCurrentOrganizationType').val();
	var orgAssessmentProgramURL='';
	if(currentOrgType==='CONS'){
		organizationId = $('#extractStateId').val();
		orgAssessmentProgramURL='getAssessmentProgramIdByOrganizationOnly.htm';
	}else{
		organizationId = $('#userDefaultOrganization').val();
		orgAssessmentProgramURL='getExtractReportAssessmentPrograms.htm';
	}
	if(organizationId != ''){
		$.ajax({
		url: orgAssessmentProgramURL,
		data: {
			organizationId: organizationId,
			extractTypeId: extractTypeId
		},
		dataType: 'json',
		type: "POST",
		async: false,
        success: function(data) {
            if (data !== undefined && data !== null && data.length > 0) {
                for (var i = 0, length = data.length; i < length; i++) {
                    optionText = data[i].abbreviatedname;                 
                    if(extractTypeId == 53){
                    	if(optionText == 'PLTW'){
                    		apSelect.append($('<option></option>').val(data[i].id).html(optionText).attr('selected','selected'));
                    		apSelect.prop("disabled", true);
                    	}
                    }                    
                    else if(data[i].id == $('#hiddenCurrentAssessmentProgramId').val()){
                    	apSelect.append($('<option></option>').val(data[i].id).html(optionText).attr({'code':data[i].abbreviatedname, 'selected':'selected'}));
						//apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(data[i].id).html(optionText));
						if(params){
							if(!params.assessmentProgramIds){
								params.assessmentProgramIds=[];
								params.assessmentProgramIds[0]=data[i].id;
							}
						}
					}else{
						if(extractTypeIdCheck != 25) {
							apSelect.append($('<option></option>').val(data[i].id).attr('code',data[i].abbreviatedname).html(optionText));
						}
                    
					}
                }
                apSelect.trigger('change.select2');
                          
                if (data.length != 1 && extractTypeId != 53) {
                    apSelect.prop('disabled', false);
                }
                if(extractTypeId==54){
                	//PLTW testing Readiness
                	apSelect.attr('disabled', 'disabled');
                }
            } 
 checkAssessmentProgramPltw();
        }
    });
	}
}

function populateAssessmentProgramsForMultiSelect(params){
	var apSelect = $('#extractAssessmentProgramId'), optionText = '',organizationIds = [];
    apSelect.find('option').filter(function() {
        return $(this).val() > 0
    }).remove().end();
    var currentOrgType=$('#userCurrentOrganizationType').val();
	var orgAssessmentProgramURL='';
	if(currentOrgType==='CONS'){
		organizationIds = $('#extractStateIdForSecurityExtract').val();
		orgAssessmentProgramURL='getAssessmentProgramIdByOrganizations.htm';
	}else{
		organizationIds = $('#userDefaultOrganization').val();
		orgAssessmentProgramURL='getCurrentUserAndOrgAssessmentPrograms.htm';
	}
	if(organizationIds != ''){
		$.ajax({
		url: orgAssessmentProgramURL,
		data: {
			organizationIds: organizationIds
		},
		dataType: 'json',
		type: "POST",
		async: false,
        success: function(data) {
            if (data !== undefined && data !== null && data.length > 0) {
                for (var i = 0, length = data.length; i < length; i++) {
                    optionText = data[i].abbreviatedname;
                    if(data[i].id == $('#hiddenCurrentAssessmentProgramId').val()){
						apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(data[i].id).html(optionText));
						if(params){
							if(!params.assessmentProgramIds){
								params.assessmentProgramIds=[];
								params.assessmentProgramIds[0]=data[i].id;
							}
						}
					}else{
		                    apSelect.append($('<option></option>').val(data[i].id).html(optionText));
					}
                }
                apSelect.select2();
                
                if (data.length != 1) {
                    apSelect.prop('disabled', false);
                }
                apSelect.trigger('change.select2');
            } 
        }
    });
	}
}

function getDistrictsForGRF(stateIds){
	var select = $('#dlmGeneralReseachExtractDistrictId');
	select.val('').find('option:not(:first)').remove();
	var data = {};
	if (typeof(stateIds) != 'undefined'){
		if (stateIds == ''|| stateIds.length === 0 ){
			return;
		}
		url = 'getDistrictsForStates.htm';
		data.stateIds = stateIds;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateMultiOrgSelect(select, data);
			} else {
				// error
			}
		}
	});
}


function getDistrictsForSecurityExtract(stateIds){
	var select = $('#extractDistrictIdForSecurityExtract');
	select.val('').find('option:not(:first)').remove();
	var data = {};
	if (typeof(stateIds) != 'undefined'){
		if (stateIds == ''|| stateIds.length === 0 ){
			return;
		}
		url = 'getDistrictsForStates.htm';
		data.stateIds = stateIds;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateMultiOrgSelect(select, data);
			} else {
				// error
			}
		}
	});
}

function getSchoolsForSecurityExtract(districtIds){
	var select = $('#extractSchoolIdForSecurityExtract');
	select.val('').find('option:not(:first)').remove();
	var data = {};
	if (typeof(districtIds) != 'undefined' ){
		if (districtIds == '' || districtIds.length === 0){
			return;
		}
		url = 'getSchoolsForDistricts.htm';
		data.districtIds = districtIds;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateMultiOrgSelect(select, data);
        	} else {
        		// error
        	}
		}
	});
}

	
	
	

function populateFiltersForStudentScore(extractTypeId){
	
	if (!districtFilter){
		$('#districtFilterForStudentScore').hide();
	}
	
	if (!schoolFilter){
		$('#schoolFilterForStudentScore').hide();
	}
	var select;
	if (districtFilter){
		$('#districtIdForStudentScore').find('option:not(:first)').remove();		
	} else if (schoolFilter){
		$('#schoolIdForStudentScore').find('option:not(:first)').remove();		
	} else {
		return;
	}
	
	$.ajax({
		url: 'getOrgsForExtractFilters.htm',
		dataType: 'json',
		data:{isInactiveOrgReq: true},
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
				if (districtFilter){					
					for (var i = 0; i < data.length; i++){
						if(data[i].activeFlag == false){
							$('#districtIdForStudentScore').append($('<option class="displayRedColor"></option>').val(data[i].id).html(data[i].organizationName));
						}else{
							$('#districtIdForStudentScore').append($('<option>', {value: data[i].id, text: data[i].organizationName}));
						}
					}
					
					$('.districtIdForStudentScore').trigger('change.select2');
					if (data.length == 1){
						$('#districtIdForStudentScore').find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						$('#districtIdForStudentScore').trigger('change');
					} else {
						$('#districtIdForStudentScore').prop('disabled', false);
					}
				}else if (schoolFilter){
					$('#schoolFilterForStudentScore').show();
					$('#schoolIdForStudentScore').prop('disabled', false);
					for (var i = 0; i < data.length; i++){
						$('#schoolIdForStudentScore').append($('<option>', {value: data[i].id, text: data[i].organizationName}));
					}
					
					$('#schoolIdForStudentScore').trigger('change.select2');
					if (data.length == 1){
						$('#schoolIdForStudentScore').find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						$('#schoolIdForStudentScore').trigger('change');
					} else {
						$('#schoolIdForStudentScore').prop('disabled', false);
					}
				
						 $("#schoolIdForStudentScore").find('option').prop("selected",true);
			               $("#schoolIdForStudentScore").trigger('change');
					
					  
				}else {
					return;
				}
        		         		
        	} else {
        		if (districtFilter){
        			$('#schoolFilterForStudentScore').hide();
        		}
        		$('#gradeFilterForStudentScore').hide();
        	}
			 $('#gradeFilterForStudentScore').find('option').prop("selected",true);
	         $('#gradeFilterForStudentScore').trigger('change');
		}
	});
	
	
}


function populateFiltersForKelpa2StudentScore(extractTypeId){
	
	if (!districtFilter){
		$('#kelpa2districtFilterForStudentScore').hide();
	}
	
	if (!schoolFilter){
		$('#kelpa2schoolFilterForStudentScore').hide();
	}
	var select;
	if (districtFilter){
		$('#kelpa2districtIdForStudentScore').find('option:not(:first)').remove();		
	} else if (schoolFilter){
		$('#kelpa2schoolIdForStudentScore').find('option:not(:first)').remove();		
	} else {
		return;
	}
	
	$.ajax({
		url: 'getOrgsForExtractFilters.htm',
		dataType: 'json',
		data:{isInactiveOrgReq: true},
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){
				if (districtFilter){					
					for (var i = 0; i < data.length; i++){
						if(data[i].activeFlag == false){
							$('#kelpa2districtIdForStudentScore').append($('<option class="displayRedColor"></option>').val(data[i].id).html(data[i].organizationName));
						}else{
							$('#kelpa2districtIdForStudentScore').append($('<option>', {value: data[i].id, text: data[i].organizationName}));
						}
					}
					
					$('.districtIdForStudentScore').trigger('change.select2');
					if (data.length == 1){
						$('#kelpa2districtIdForStudentScore').find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						$('#kelpa2districtIdForStudentScore').trigger('change');
					} else {
						$('#kelpa2districtIdForStudentScore').prop('disabled', false);
					}
				}else if (schoolFilter){
					$('#kelpa2schoolFilterForStudentScore').show();
					$('#kelpa2schoolIdForStudentScore').prop('disabled', false);
					for (var i = 0; i < data.length; i++){
						$('#kelpa2schoolIdForStudentScore').append($('<option>', {value: data[i].id, text: data[i].organizationName}));
					}
					
					$('#kelpa2schoolIdForStudentScore').trigger('change.select2');
					if (data.length == 1){
						$('#kelpa2schoolIdForStudentScore').find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						$('#kelpa2schoolIdForStudentScore').trigger('change');
					} else {
						$('#kelpa2schoolIdForStudentScore').prop('disabled', false);
					}
				
						 $('#kelpa2schoolIdForStudentScore').find('option').prop("selected",true);
			               $('#kelpa2schoolIdForStudentScore').trigger('change');
					
					  
				}else {
					return;
				}
        		         		
        	} else {
        		if (districtFilter){
        			$('#kelpa2schoolFilterForStudentScore').hide();
        		}
        		$('#kelpa2gradeFilterForStudentScore').hide();
        	}
			 $('#kelpa2gradeFilterForStudentScore').find('option').prop("selected",true);
	         $('#kelpa2gradeFilterForStudentScore').trigger('change');
		}
	});
	
}

function getSchoolsForStudentScoreExtract(districtId){
	
	$('#schoolFilterForStudentScore').show();
	$('#schoolIdForStudentScore').prop('disabled', false);	
	$('#schoolIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();;
	var url = 'getSchoolsForUser.htm';
	var data = {};
	if (typeof(districtId) != 'undefined'){
		if (districtId == ''){
			return;
		}	
		url = 'getSchoolsInDistrictforKapScore.htm';
		data.districtId = districtId;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].organizationName;
					if(data[i].activeFlag == false){
						$('#schoolIdForStudentScore').append($('<option class="displayRedColor"></option>').val(data[i].id).html(optionText));
					}else{
						$('#schoolIdForStudentScore').append($('<option></option>').val(data[i].id).html(optionText));
					}
				}					
				$('#schoolIdForStudentScore').select2({multiple: true});		
				
				if (data.length == 1){
					$('#schoolIdForStudentScore').find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$('#schoolIdForStudentScore').trigger('change');
				} else {
					$('#schoolIdForStudentScore').prop('disabled', false);
				}
				if(document.getElementById("schoolIdForStudentScore_kap_score").checked == true){
				 $('#schoolIdForStudentScore').find('option').prop("selected",true);
		         $('#schoolIdForStudentScore').trigger('change');
				}
        	} else {        		
        		$('#schoolFilterForStudentScore').hide();
        	}
			
		}
	});
}

function getSchoolsForKelpaStudentScoreExtract(districtId){
	
	$('#kelpa2schoolFilterForStudentScore').show();
	$('#kelpa2schoolIdForStudentScore').prop('disabled', false);	
	$('#kelpa2schoolIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();;
	var url = 'getSchoolsForUser.htm';
	var data = {};
	if (typeof(districtId) != 'undefined'){
		if (districtId == ''){
			return;
		}	
		url = 'getSchoolsInDistrictforKapScore.htm';
		data.districtId = districtId;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0){				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].organizationName;
					if(data[i].activeFlag == false){
						$('#kelpa2schoolIdForStudentScore').append($('<option class="displayRedColor"></option>').val(data[i].id).html(optionText));
					}else{
						$('#kelpa2schoolIdForStudentScore').append($('<option></option>').val(data[i].id).html(optionText));
					}
				}					
				$('#kelpa2schoolIdForStudentScore').select2({multiple: true});		
				
				if (data.length == 1){
					$('#kelpa2schoolIdForStudentScore').find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$('#kelpa2schoolIdForStudentScore').trigger('change');
				} else {
					$('#kelpa2schoolIdForStudentScore').prop('disabled', false);
				}
				if(document.getElementById("schoolIdForStudentScore_kelpa2_score").checked == true){
				 $('#kelpa2schoolIdForStudentScore').find('option').prop("selected",true);
		         $('#kelpa2schoolIdForStudentScore').trigger('change');
				}
        	} else {        		
        		$('#kelpa2schoolFilterForStudentScore').hide();
        	}
			
		}
	});
}

function getSubjectsForStudentScoreExtract(orgId, orgType,extractTypeId){	
		orgIds=JSON.parse("[" + orgId + "]");		
	var params = {};
	$('#subjectIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();	
	params.orgId = orgIds;
	params.orgType = orgType;
	params.extractTypeId = extractTypeId;	
	$.ajax({
		url: 'getSubjectsForStudentScoreExtract.htm',
		data: params,
		dataType: 'json',
		type: 'POST',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$('#subjectIdForStudentScore').html('');
				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].name;
					$('#subjectIdForStudentScore').append($('<option></option>').val(data[i].id).html(optionText));					
				}					
				$('#subjectIdForStudentScore').select2({multiple: true});				
				if (data.length != 1) {
					$('#subjectIdForStudentScore').prop('disabled', false);
				}
			}	if(document.getElementById("subjectIdForStudentScore_kap_score").checked == true){	
			$("#subjectIdForStudentScore").find('option').prop("selected",true);
	         $("#subjectIdForStudentScore").trigger('change');
		}
		}
	});
}

function getSchoolYearsForStudentScoreExtractForKelpa(subjectIds){
	var params = {};
	$('#kelpa2schoolYearFilterForStudentScore').show();
	$('#kelpa2schoolYearIdForStudentScore').prop('disabled', false);
	$('#kelpa2schoolYearIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();	
	var reportYear=$('#kelpa2_St_ReportYear').val();
	params.contentAreaId = subjectIds;
	
	$.ajax({
		url: 'getSchYrBySubjectForStudentScoreExtract.htm',
		data: params,
		dataType: 'json',
		type: 'POST',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$('#kelpa2schoolYearIdForStudentScore').html('');				
				for (var i = 0, length = data.length; i < length; i++) {
					if(reportYear != data[i]){				
						optionText = data[i];
						$('#kelpa2schoolYearIdForStudentScore').append($('<option></option>').val(data[i]).html(optionText));	
					}										
				}					
				$('#kelpa2schoolYearIdForStudentScore').select2({multiple: true});
				if (data.length != 1) {
					$('#kelpa2schoolYearIdForStudentScore').prop('disabled', false);
				}
			}else{
				$('#kelpa2schoolYearFilterForStudentScore').hide();
			}
			if(document.getElementById("schoolYearIdForStudentScore_kelpa2_score").checked == true){
			$("#kelpa2schoolYearIdForStudentScore").find('option').prop("selected",true);
	         $("#kelpa2schoolYearIdForStudentScore").trigger('change');
			}
		}
	});
}

function getSchoolYearsForStudentScoreExtract(subjectIds){
	var params = {};
	$('#schoolYearFilterForStudentScore').show();
	$('#schoolYearIdForStudentScore').prop('disabled', false);
	$('#schoolYearIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();	
	var reportYear=$('#kap_St_ReportYear').val();
	params.contentAreaId = subjectIds;	
	if(subjectIds === null || subjectIds == ''){
		$('#schoolYearFilterForStudentScore').hide();
	}
	$.ajax({
		url: 'getSchYrBySubjectForStudentScoreExtract.htm',
		data: params,
		dataType: 'json',
		type: 'POST',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$('#schoolYearIdForStudentScore').html('');				
				for (var i = 0, length = data.length; i < length; i++) {
					if(reportYear != data[i]){				
						optionText = data[i];
						$('#schoolYearIdForStudentScore').append($('<option></option>').val(data[i]).html(optionText));	
					}										
				}					
				$('#schoolYearIdForStudentScore').select2({multiple: true});
				if (data.length != 1) {
					$('#schoolYearIdForStudentScore').prop('disabled', false);
				}
			}else{
				$('#schoolYearFilterForStudentScore').hide();
			}
			if(document.getElementById("schoolYearIdForStudentScore_kap_score").checked == true){
			$("#schoolYearIdForStudentScore").find('option').prop("selected",true);
	         $("#schoolYearIdForStudentScore").trigger('change');
			}
		}
	});
}

function getGradesForStudentScoreExtract(orgId, orgType,extractTypeId){	
	var params = {};
	$('#gradeFilterForStudentScore').show();
	$('#gradeIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();	
	orgIds=JSON.parse("[" + orgId + "]");	
	params.orgId = orgIds;
	params.orgType = orgType;
	params.extractTypeId = extractTypeId;
	if((orgIds === null || orgIds == '')&& orgType =="DT" ){
		$('#gradeFilterForStudentScore').show();
	}

	$.ajax({
		url: 'getGradesForStudentScoreExtract.htm',
		data: params,
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$('#gradeIdForStudentScore').html('');
				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].name;
					$('#gradeIdForStudentScore').append($('<option></option>').val(parseInt(data[i].abbreviatedName)).html(optionText));					
				}					
				$('#gradeIdForStudentScore').select2({multiple: true});
				if (data.length != 1) {
					$('#gradeIdForStudentScore').prop('disabled', false);
				}
			}else{			
				$('#gradeFilterForStudentScore').hide();
			}
			if(document.getElementById("gradeIdForStudentScore_kap_score").checked ==true){
				$("#gradeIdForStudentScore").find('option').prop("selected",true);
		         $("#gradeIdForStudentScore").trigger('change');
			}
			
		}
	});
}

function getGradesForKelpaStudentScoreExtract(orgId, orgType,extractTypeId){	
	var params = {};
	$('#kelpa2gradeFilterForStudentScore').show();
	$('#kelpa2gradeIdForStudentScore').find('option').filter(function(){return $(this).val() > 0}).remove().end();	
	orgIds=JSON.parse("[" + orgId + "]");	
	params.orgId = orgIds;
	params.orgType = orgType;
	params.extractTypeId = extractTypeId;
	if((orgIds === null || orgIds == '')&& orgType =="DT" ){
		$('#kelpa2gradeFilterForStudentScore').show();
	}

	$.ajax({
		url: 'getGradesForStudentScoreExtract.htm',
		data: params,
		dataType: 'json',
		type: 'GET',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$('#kelpa2gradeIdForStudentScore').html('');
				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].name;
					$('#kelpa2gradeIdForStudentScore').append($('<option></option>').val(data[i].abbreviatedName).html(optionText));	
				}					
				$('#kelpa2gradeIdForStudentScore').select2({multiple: true});
				if (data.length != 1) {
					$('#kelpa2gradeIdForStudentScore').prop('disabled', false);
				}
			}else{			
				$('#kelpa2gradeFilterForStudentScore').hide();
			}
			if(document.getElementById("gradeIdForStudentScore_kelpa2_score").checked ==true){
				$("#kelpa2gradeIdForStudentScore").find('option').prop("selected",true);
		         $("#kelpa2gradeIdForStudentScore").trigger('change');
			}
			
		}
	});
}

function validateStudentIdForKapStudentScore(extractId, extractTypeId, params){	
	params.extractId = extractId;
	params.extractTypeId = extractTypeId;
	
	var button = $('#newFile' + extractTypeId).attr('disabled', 'disabled');
	$.ajax({
		type: 'POST',
		url: 'validateStudentIdForKapScoreDE.htm',
		dataType: 'json',
		data: params,
		success: function(data){
			if( data == -1) 
			{
				validateStudentIDtReplaceDialog(extractId, extractTypeId, params, button);
			}
			else if(data == 0)
			{
				generateExtractReplaceDialog(extractId, extractTypeId, params, button);	
			}
		},
		complete: function(){
			$(button).removeAttr('disabled');
		}
	});
}

function getSubjectsForITIBpExtract(){	
	$.ajax({
		url: 'getSubjectsForITIBPSummaryExtract.htm',
		dataType: 'json',		
		type: 'POST',
		success: function(data) {
			$select = $('#subjectExtractForITIBP').find('select');
			populateDependentList($select, data, 'name');
		}
	});
}

function getGradesForITIBpExtract(contentAreaId){	
	var param = {};
	param.contentAreaId = contentAreaId;
	if($('#itiBluePrintExtractSchoolId').val() != undefined && $('#itiBluePrintExtractSchoolId').val() != '') {
		param.narrowOrgId = $('#itiBluePrintExtractSchoolId').val();
	} else if($('#itiBluePrintExtractDistrictId').val() != undefined && $('#itiBluePrintExtractDistrictId').val() != '') {
		param.narrowOrgId = $('#itiBluePrintExtractDistrictId').val();
	} else if($('#itiBluePrintExtractStateId').val() != undefined && $('#itiBluePrintExtractDistrictId').val() != '') {
		param.narrowOrgId = $('#itiBluePrintExtractStateId').val();
	} else {
		param.narrowOrgId = $('#userNarrowestOrgId').val();
	}	
	$.ajax({
		url: 'getGradesForITIBPSummaryExtract.htm',
		data: param,
		dataType: 'json',		
		type: 'POST',
		success: function(data) {
			$select = $('#gradeExtractForITIBP').find('select');
			populateDependentList($select, data, 'name');
		}
	});
}

function stateSpecificFilesPopUp(){
	$('#stateSpecificFilesDialog').dialog(
	{
		autoOpen : false,
		modal : true,
		width : 1000,
		height : 600,
		title : "Custom Files",
		position : {
	        'my': 'center',
	        'at': 'center'
	    },
		create : function(event, ui) {
			var widget = $(this).dialog("widget");
		},
		open : function(event, ui) {
			getLoggedInUserApandStateDetails();
			stateSpecificFilesJQGrid();
			 $(".ui-dialog #postFileButton").on('click', function () {
				 $("#uploadPostFileDialog").dialog("open");
		     });
		},
		close : function(event, ui) {
			
		}
	
	}).dialog('open');
	
}


function stateSpecificFilesJQGrid(){
	var $grid= $('#stateSpecificFilesTableId');
	var grid_width =900;
	//var cell_width = (grid_width)/4;
	var cm;
	var columnNames =["Description","File Name","Date Added","Actions"];
	if(hasEditCustomFilePermission){
		cm=[ 
			{name:'fileDescription', index: 'fileDescription', sorttype : 'text', search : true, hidden: false,width: '225'},
			{name:'fileName', index: 'fileName', sorttype : 'text',formatter: customFileLinkFormatter, search : true, hidden: false,width: '225'},		
			{name:'createdDate', sorttype : 'date', search : false, hidden: false,width: '225',
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}
			,{ name: 'actions', sortable: false, hidden : false, hidedlg: true, search: false, width: '225',
			formatter: function(cellValue, options, rowObject){
				return '<span onclick="removeStateSpecificFile('+rowObject.id+')" class="ui-icon ui-icon-trash" title="Remove file"></span>';
			},unformat: function(cellValue, options, rowObject){
				return;
			}
	      	}
			];	
	}else{
		//cell_width = (grid_width)/3;
		columnNames =["Description","File Name","Date Added"];
		cm=[ 
			{name:'fileDescription', index: 'fileDescription', sorttype : 'text', search : true, hidden: false,width: '300'},
			{name:'fileName', index: 'fileName', sorttype : 'text',formatter: customFileLinkFormatter, search : true, hidden: false,width: '300'},		
			{name:'createdDate', sorttype : 'date', search : false, hidden: false,width:'300',
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}
			];	
	}
	
	$grid.scb({
		mtype: "POST",
		datatype : "local",
		//width: grid_width,
		colNames: columnNames,
	  	colModel :cm,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#pstateSpecificFilesTableId',
		sortname: 'fileDescription',
	    sortorder: 'asc',
		loadonce: false,
		viewable: false,
	    beforeRequest: function() {
	    		//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	        	$(this).setGridParam({postData: {page : lastPage}});
	        }
	        $("#stateSpecificFilesTableId").jqGrid('clearGridData');
	    },jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
		    	root: function(obj) { 
		    		return obj.stateSpecificFilesData;
		    	} 
	    },  
	    loadComplete: function() {
	    		this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
        },beforeSelectRow: function(rowid, e) {
            return false;
        }
	});
	
	$grid[0].clearToolbar();
	$grid.jqGrid('setGridParam',{
		datatype:"json", 
		url : 'getStateSpecificFileData.htm?q=1', 
		sortname: 'fileDescription',
	    sortorder: 'asc',
		search: false, 
	}).trigger("reloadGrid").trigger('resize');	

	$grid.jqGrid('navGrid', '#pstateSpecificFilesTableId', {edit: false, add: false, del: false});
}

function customFileLinkFormatter(cellValue, options, rowObject){
		
	return '<a href="downloadStateSpecificFile.htm?stateSpecificFileId=' + rowObject.id  +'" title="Click to download Custom File.">'+rowObject.fileName+'</a>';
}


$('#uploadPostFileDialog').dialog({
		width : 400, 
		height : 250,
		autoOpen: false,
		modal:true,
		position:{
        'my': 'center',
        'at': 'center'
    },
    open : function(event, ui) {
    	$('#postFileData').val('');
    	$('#descriptionName').val('');
    	$('#uploadPostFileDialog').parent().find('.ui-dialog-titlebar-close').hide();
	},
    buttons: {
		Cancel: function(){
			$(this).dialog('close');	
		},
		Submit: function(){
			uploadCustomFiles($(this));
		}
	}
});


function getLoggedInUserApandStateDetails(){
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST",
		success: function(assessmentPrograms) {
			if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
				$.each(assessmentPrograms, function(i, assessmentProgram) {
					if(assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
						$('#currentAssessmentProgram').html(' '+assessmentPrograms[i].programName);
					}
				});
			}
		}
	});
	
	$.ajax({
		url : 'getStatesOrgsForUser.htm',
		data : {},
		dataType : 'json',
		type : "GET",
		success : function(states) {
			if (states !== undefined && states !== null && states.length > 0) {
				$.each(states, function(i, stateOrg) {
					$('#currentOrganization').html(' '+states[i].organizationName);
				});
			}
		}
	});
}

function removeStateSpecificFile(stateSpecificFileId){
	$('#removeStateSpecificFileDialog').dialog({
		autoOpen : false,
		modal : true,
		width : 400,
		height : 150,
	    open : function(event, ui) {
	    	$('#removeStateSpecificFileDialog').parent().find('.ui-dialog-titlebar-close').hide();
		},
		buttons : {
			"Confirm" : function(event, ui) {
				if(stateSpecificFileId!=null && stateSpecificFileId!=''){
					$.ajax({
						url: 'removeStateSpecificFile.htm',
						data: {stateSpecificFileId:stateSpecificFileId},
						dataType: 'json',
						type: 'POST',
						success: function(data){
							if (data.successMessage) {
								$('#removeCustomFileSucess').text(data.successMessage);
								$('#removeCustomFileSucess').show();
								setTimeout("$('#removeCustomFileSucess').hide()", 5000);
							}else {
								$('#removeCustomFileError').text(data.error);
								$('#removeCustomFileError').show();
								setTimeout("$('#removeCustomFileError').hide()", 5000);
							}
							$('#stateSpecificFilesTableId').trigger('reloadGrid');
						}	 
					});
				}
				$(this).dialog("close");				
			},
			"Cancel" : function(ev, ui) {
				$(this).dialog("close");
			}
		},
		close : function(ev, ui) {
			$(this).dialog('close');
		}
	}).dialog('open');
	
}

function uploadCustomFiles(dialog, maxFileSize){
	var fd = new FormData();
	var filedata = $('#postFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	var descriptionName=$('#descriptionName').val();
	if(filelist.length === 0 || descriptionName === null || descriptionName===""){
		if(filelist.length === 0){
			$("#uploadCustomNoFileError").text("File name must be entered.");
			$("#uploadCustomNoFileError").show();
			setTimeout("$('#uploadCustomNoFileError').hide()", 5000);
		}
		if(descriptionName===""){
			$("#uploadCustomFileDescriptionError").text("File description must be entered.");
			$("#uploadCustomFileDescriptionError").show();
			setTimeout("$('#uploadCustomFileDescriptionError').hide()", 5000);
		}
		return false;
	} else {
		var maxFileSize = $('#stateSpecificFileMaxSize').val();
		if(file.size > maxFileSize*1024*1024){
			$("#uploadCustomNoFileError").text("File size must be less than 20MB");
			$("#uploadCustomNoFileError").show();
			setTimeout("$('#uploadCustomNoFileError').hide()", 5000);
		} else {
			var assessmentProgramId=$('#hiddenCurrentAssessmentProgramId').val();
			fd.append('assessmentProgramId',assessmentProgramId);
			var stateId=$('#hiddenCurrentOrganizationId').val();
			fd.append('stateId',stateId);
			fd.append('uploadCustomFile',file);
			fd.append('descriptionName',descriptionName);
			
			$.ajax({
				url: 'uploadCustomFilesData.htm',
				data: fd,
				dataType: 'json',
				processData: false,
				contentType: false,
				cache: false,
				type: 'POST',
				success: function(data){
					if (data.success) {
						$('#uploadCustomFileSucess').text(data.success);
						$('#uploadCustomFileSucess').show();
						setTimeout("$('#uploadCustomFileSucess').hide()", 5000);
					}else {
						$('#uploadCustomFileError').text(data.error);
						$('#uploadCustomFileError').show();
						setTimeout("$('uploadCustomFileError').hide()", 5000);
					}
					$('#stateSpecificFilesTableId').trigger('reloadGrid');
				}
			});
			dialog.dialog('close');
		}
	}
}

function checkAssessmentProgramPltw() {
	var selectedAP = null;	
	var assessmentProgramId = null;
	var assessmentProgramIds = $("#extractAssessmentProgramId").select2('val');
	$("#extractAssessmentProgramId option:selected").each(function () {
		   var $this = $(this);
		   if ($this.length) {
			   selectedAP = $this.text();
		    if(selectedAP == 'PLTW'){
		    	assessmentProgramId = $this.val(); 
		    	return false;
		    }
		   }
		});
	
	if(selectedAP == 'PLTW'){
		$("#extractSubjectId option").remove();	
	    $("#subjectExtractFilter label").text("Course:");
	    if(extractTypeIdCheck == 25) {
			$("#gradeExtractFilter").hide();    	
	    }
	    
	} else if(selectedAP != 'PLTW'){
		$("#extractSubjectId option").remove();	
		$("#subjectExtractFilter label").text("Subject:");
	    if(extractTypeIdCheck == 25) {
			$("#gradeExtractFilter").show();
	    }
	}
    getSubjectsByGradeAndAssessment(null, assessmentProgramIds);
}