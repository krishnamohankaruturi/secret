$(function(){
	reportAccessHasPerm();
});

var WritingReport = can.Model.extend({
	findOne: function(params) {
		return $.ajax({
			url: 'getWritingResponsesForStudents.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

function customFilterValidation(){
	if (reportsConfig.getReportType() === 'general_student_bundled'){
		var districtIds = getFilterWithDataType('district').val();
		var schoolIds = getFilterWithDataType('school').val();
		return (districtIds != null && districtIds.length > 0) || (schoolIds != null && schoolIds.length > 0);
	}
}

function prepareReportContent(data, paramsFromRequest){
	var $tabArea = $('.report-content', getContentElement());
	var ejsFile = '';
	var ejsParams = {};
	var callback = null;
	switch (reportsConfig.getReportType()){
		case 'general_student':
		case 'kelpa_student_individual':
			ejsFile = 'js/views/new_reports/generalStudentReport.ejs';
			ejsParams = {data: data};
			callback = studentIndividualReportCallback;
			break;
		case 'general_student_writing':
			ejsFile = 'js/views/new_reports/writingReport.ejs';
			ejsParams = {data: data};
			callback = studentWritingReportCallback;
			break;
		case 'general_student_all':
			ejsFile = 'js/views/new_reports/generalAllStudentReports.ejs';
			ejsParams = {
				data: data,
				programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()
			};
			callback = allStudentReportCallback;
			break;
		case 'general_school_summary':
			ejsFile = 'js/views/new_reports/generalSchoolSummary.ejs';
			ejsParams = {data: data};
			callback = schoolSummaryReportCallback;
			break;
		case 'general_district_summary':
			ejsFile = 'js/views/new_reports/generalDistrictSummary.ejs';
			ejsParams = {data: data};
			callback = districtSummaryReportCallback;
			break;
		default: break;
	}
	
	if (ejsFile !== ''){
		can.view.render(ejsFile, ejsParams, function(fragment){
			$tabArea.html(fragment);
			if (typeof (callback) === 'function'){
				callback(data, paramsFromRequest);
			}
		});
	}
}

function studentIndividualReportCallback(data, paramsFromRequest){
	
}

function studentWritingReportCallback(data, paramsFromRequest){
	var writingStudents = $('#writingStudentDropDown', getReportContentElement());
	var customOptions = {
		multiple: true,
		closeOnSelect: false,
		width: 500,
		placeholder: writingStudents.find('option').length > 0 ? 'Select Students' : 'No students available'
	};
	writingStudents.on('change', function(){
		var val = $(this).val();
		if (val != null && val.length > 0){
			if (val.length > 0){
				$('#viewWritingResponses', getReportContentElement()).show();
			} else {
				$('#viewWritingResponses', getReportContentElement()).hide();
			}
		}
	});
	var opts = $.extend(getSelect2DefaultOptionsCopy(), customOptions);
	writingStudents.val('').trigger('change').select2(opts);
	
	$('#saveWritingResponsePDF', getReportContentElement()).hide();
	$('#viewWritingResponses', getReportContentElement()).hide().off('click').on('click', function(){
		$('#saveWritingResponsePDF', getReportContentElement()).hide().off();
		
		var params = {
			districtId: getFilterWithDataType('district').val(),
			schoolId: getFilterWithDataType('school').val(),
			gradeId: getFilterWithDataType('grade').val(),
			assessmentProgramId: $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
			ids: $('#writingStudentDropDown', getReportContentElement()).val(),
			includeScoredResponses: $('#writingIncludeScored', getReportContentElement()).prop('checked')
		};
		
		if(params.assessmentProgramId != null && params.assessmentProgramId != '' &&
				params.districtId != null && params.districtId != '' &&
				params.schoolId != null && params.schoolId != ''&&
				params.gradeId != null && params.gradeId != '' &&
				params.ids.length > 0){
			$('#writingReportDisplayDiv', getReportContentElement()).empty();
			WritingReport.findOne(params, function(report) {
				var canSavePDF = false;
				var ids = [];
				
				for (var x in report){
					if (!isNaN(x)){
						var found = false;
						for (var i = 0; i < ids.length; i++){
							if (ids[i] === report[x].studentId){
								found = true;
							}
						}
						if (!found){
							ids.push(report[x].studentId);
							canSavePDF = true;
						}
					}
				}
				
				var reportIdsParam = '';
				for (var x = 0; x < ids.length; x++){
					reportIdsParam += '&ids%5B%5D=' + encodeURIComponent(ids[x]);
				}
				
				new writingReport('#writingReportDisplayDiv', {report: report});
				$('#writingReportDisplayDiv').show();
				
				if (canSavePDF){
					var districtId = paramsFromRequest.districtId;
					var schoolId = paramsFromRequest.schoolId;
					var gradeId = paramsFromRequest.gradeId;
					$('#saveWritingResponsePDF').on('click', function(){
						var pdfUrlParams = '&districtId=' + encodeURIComponent(districtId) +
						'&schoolId=' + encodeURIComponent(schoolId) +
						'&gradeId=' + encodeURIComponent(gradeId) +
						'&includeScoredResponses=' + encodeURIComponent($('#writingIncludeScored', getReportContentElement()).prop('checked')) + reportIdsParam;
						
						var url = 'getWritingResponsesPDF.htm?' + pdfUrlParams;
						window.location = url;
					});
					$('#saveWritingResponsePDF').show();
				} else {
					$('#saveWritingResponsePDF').hide();
				}
			});
		}
	});
}

function allStudentReportCallback(data, paramsFromRequest){
	loadBundledReportContent(data);
}

function schoolSummaryReportCallback(data, paramsFromRequest){
	
}

function districtSummaryReportCallback(data, paramsFromRequest){
	
}

$('#reports-nav li.nav-item:first a').tab('show');	
	$("#generalAssessmentNav a").on('click', function() {
	   var clickedURL = $(this).attr("id"); 	
	   setReportAccess(clickedURL);  
	});
		
var usersItemMenu = $('#reports-nav li.nav-item:first a');
if(usersItemMenu.length == 0){
	var clickedURL = $('.reports-nav a.nav-link').first().attr('id');
	setReportAccess(clickedURL);    	
}	



function reportAccessHasPerm(){
	var organizationId = $('#userDefaultOrganization').val();
	var groupId = $('#userDefaultGroup').val();
	var userAssessmentProgramId = $('#userDefaultAssessmentProgram').val();
	var data = {};
	data.currentOrganizationId = organizationId;
	data.currentGroupId = groupId;	
	data.currentAssessmentProgramId = userAssessmentProgramId;	
	
	$.ajax({
		url: 'getReportAccessPermission.htm',
		dataType: 'json',
		data: data,
		type: "POST"
	}).done(function(result) {			
			if(result) {
				$('#GEN_ST_WRITING').show();
			} else {
				$('#GEN_ST_WRITING').hide();				
			}
	});
}
$(function(){
	reportAccessHasPerm();
});

var WritingReport = can.Model.extend({
	findOne: function(params) {
		return $.ajax({
			url: 'getWritingResponsesForStudents.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

function customFilterValidation(){
	if (reportsConfig.getReportType() === 'general_student_bundled'){
		var districtIds = getFilterWithDataType('district').val();
		var schoolIds = getFilterWithDataType('school').val();
		return (districtIds != null && districtIds.length > 0) || (schoolIds != null && schoolIds.length > 0);
	}
}

function prepareReportContent(data, paramsFromRequest){
	var $tabArea = $('.report-content', getContentElement());
	var ejsFile = '';
	var ejsParams = {};
	var callback = null;
	switch (reportsConfig.getReportType()){
		case 'general_student':
		case 'kelpa_student_individual':
			ejsFile = 'js/views/new_reports/generalStudentReport.ejs';
			ejsParams = {data: data};
			callback = studentIndividualReportCallback;
			break;
		case 'general_student_writing':
			ejsFile = 'js/views/new_reports/writingReport.ejs';
			ejsParams = {data: data};
			callback = studentWritingReportCallback;
			break;
		case 'general_student_all':
			ejsFile = 'js/views/new_reports/generalAllStudentReports.ejs';
			ejsParams = {
				data: data,
				programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()
			};
			callback = allStudentReportCallback;
			break;
		case 'general_school_summary':
			ejsFile = 'js/views/new_reports/generalSchoolSummary.ejs';
			ejsParams = {data: data};
			callback = schoolSummaryReportCallback;
			break;
		case 'general_district_summary':
			ejsFile = 'js/views/new_reports/generalDistrictSummary.ejs';
			ejsParams = {data: data};
			callback = districtSummaryReportCallback;
			break;
		default: break;
	}
	
	if (ejsFile !== ''){
		can.view.render(ejsFile, ejsParams, function(fragment){
			$tabArea.html(fragment);
			if (typeof (callback) === 'function'){
				callback(data, paramsFromRequest);
			}
		});
	}
}

function studentIndividualReportCallback(data, paramsFromRequest){
	
}

function studentWritingReportCallback(data, paramsFromRequest){
	var writingStudents = $('#writingStudentDropDown', getReportContentElement());
	var customOptions = {
		multiple: true,
		closeOnSelect: false,
		width: 500,
		placeholder: writingStudents.find('option').length > 0 ? 'Select Students' : 'No students available'
	};
	writingStudents.on('change', function(){
		var val = $(this).val();
		if (val != null && val.length > 0){
			if (val.length > 0){
				$('#viewWritingResponses', getReportContentElement()).show();
			} else {
				$('#viewWritingResponses', getReportContentElement()).hide();
			}
		}
	});
	var opts = $.extend(getSelect2DefaultOptionsCopy(), customOptions);
	writingStudents.val('').trigger('change').select2(opts);
	
	$('#saveWritingResponsePDF', getReportContentElement()).hide();
	$('#viewWritingResponses', getReportContentElement()).hide().off('click').on('click', function(){
		$('#saveWritingResponsePDF', getReportContentElement()).hide().off();
		
		var params = {
			districtId: getFilterWithDataType('district').val(),
			schoolId: getFilterWithDataType('school').val(),
			gradeId: getFilterWithDataType('grade').val(),
			assessmentProgramId: $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
			ids: $('#writingStudentDropDown', getReportContentElement()).val(),
			includeScoredResponses: $('#writingIncludeScored', getReportContentElement()).prop('checked')
		};
		
		if(params.assessmentProgramId != null && params.assessmentProgramId != '' &&
				params.districtId != null && params.districtId != '' &&
				params.schoolId != null && params.schoolId != ''&&
				params.gradeId != null && params.gradeId != '' &&
				params.ids.length > 0){
			$('#writingReportDisplayDiv', getReportContentElement()).empty();
			WritingReport.findOne(params, function(report) {
				var canSavePDF = false;
				var ids = [];
				
				for (var x in report){
					if (!isNaN(x)){
						var found = false;
						for (var i = 0; i < ids.length; i++){
							if (ids[i] === report[x].studentId){
								found = true;
							}
						}
						if (!found){
							ids.push(report[x].studentId);
							canSavePDF = true;
						}
					}
				}
				
				var reportIdsParam = '';
				for (var x = 0; x < ids.length; x++){
					reportIdsParam += '&ids%5B%5D=' + encodeURIComponent(ids[x]);
				}
				
				new writingReport('#writingReportDisplayDiv', {report: report});
				$('#writingReportDisplayDiv').show();
				
				if (canSavePDF){
					var districtId = paramsFromRequest.districtId;
					var schoolId = paramsFromRequest.schoolId;
					var gradeId = paramsFromRequest.gradeId;
					$('#saveWritingResponsePDF').on('click', function(){
						var pdfUrlParams = '&districtId=' + encodeURIComponent(districtId) +
						'&schoolId=' + encodeURIComponent(schoolId) +
						'&gradeId=' + encodeURIComponent(gradeId) +
						'&includeScoredResponses=' + encodeURIComponent($('#writingIncludeScored', getReportContentElement()).prop('checked')) + reportIdsParam;
						
						var url = 'getWritingResponsesPDF.htm?' + pdfUrlParams;
						window.location = url;
					});
					$('#saveWritingResponsePDF').show();
				} else {
					$('#saveWritingResponsePDF').hide();
				}
			});
		}
	});
}

function allStudentReportCallback(data, paramsFromRequest){
	loadBundledReportContent(data);
}

function schoolSummaryReportCallback(data, paramsFromRequest){
	
}

function districtSummaryReportCallback(data, paramsFromRequest){
	
}

$('#reports-nav li.nav-item:first a').tab('show');	
	$("#generalAssessmentNav a").on('click', function() {
	   var clickedURL = $(this).attr("id"); 	
	   setReportAccess(clickedURL);  
	});
		
var usersItemMenu = $('#reports-nav li.nav-item:first a');
if(usersItemMenu.length == 0){
	var clickedURL = $('.reports-nav a.nav-link').first().attr('id');
	setReportAccess(clickedURL);    	
}	



function reportAccessHasPerm(){
	var organizationId = $('#userDefaultOrganization').val();
	var groupId = $('#userDefaultGroup').val();
	var userAssessmentProgramId = $('#userDefaultAssessmentProgram').val();
	var data = {};
	data.currentOrganizationId = organizationId;
	data.currentGroupId = groupId;	
	data.currentAssessmentProgramId = userAssessmentProgramId;	
	
	$.ajax({
		url: 'getReportAccessPermission.htm',
		dataType: 'json',
		data: data,
		type: "POST"
	}).done(function(result) {			
			if(result) {
				$('#GEN_ST_WRITING').show();
			} else {
				$('#GEN_ST_WRITING').hide();				
			}
	});
}
