$(function(){
	reportAccessHasPerm();
});

$(document).on("change", "#altStudentDropDown", function(ev){
	$("#altReportDisplayDiv").empty();
	$("#altReportDisplayDiv").addClass("hidden");
	var studentVal= $('#altStudentDropDown').val();
	if(studentVal != null && studentVal.length > 0){
		$("#viewAltStudentReportDiv").removeClass("hidden");
	}	
	else
	{
		$("#viewAltStudentReportDiv").addClass("hidden");
	}
});

$(document).on("change", "#roster-report_rosterSelect", function(){
	 $("#select2-studentRoster-results").hide();
});

$(document).on("click", "#viewAltStudentReport", function(ev){	
	ev.preventDefault();
	var form = $('.report-content', getContentElement());
	values = can.deparam(form.serialize());
	values.districtName = $("#student-progress_districtSelect option:selected").text();
	values.districtId = $("#student-progress_districtSelect option:selected").val();
	values.schoolName = $("#student-progress_schoolSelect option:selected").text();
	values.schoolId = $("#student-progress_schoolSelect option:selected").val();
	values.subjectName = $("#student-progress_subjectSelect option:selected").text();
	values.subjectId = $("#student-progress_subjectSelect option:selected").val();
	values.studentName = $("#altStudentDropDown option:selected").text();
	values.studentId = $("#altStudentDropDown option:selected").val();
	values.gradeName =  $("#altStudentDropDown option:selected").data('grade-name');
	values.testCycleID =$('select[data-data-type="windowCycle"]', getFilterElement()).val();
	if(values.schoolId!=null && values.subjectId!=null && values.studentId != null && values.schoolId!='' && values.subjectId!='' && values.studentId!=''){
	$("#viewAltStudentReportDiv").addClass("hidden");
	DLMStudentReport.findOne(values, function(report) {
	form.trigger('selected', report );			
			if(reportsConfig.currentControlInstance) {
				reportsConfig.currentControlInstance.destroy();
			}
			reportsConfig.currentControlInstance = renderAltStudentReport(report);
		});
		}
	else {
		$("#viewAltStudentReportDiv #requiredmessage").show();
	}
});
$(document).on("change", "#studentRoster", function(ev){
	var studentVal= $('#studentRoster').val();
	if(studentVal != null && studentVal.length > 0){
		$("#viewReportDiv").removeClass("hidden");
	}	
	else
	{
		$("#viewReportDiv").addClass("hidden");
	}
});
$(document).on("click", "#viewReport", function(ev){
	ev.preventDefault();
	var form = $('.report-content', getContentElement());
	values = can.deparam(form.serialize());
	var checkTags = $("#studentRoster").val();
	var studentNames = [];	
	$('#studentRoster> option:selected').each(function() {
	    studentNames.push($(this).text());
	});
	values.studentNames = studentNames;
	values.schoolName= $('#roster-report_schoolSelect :selected').text();
	values.schoolId = $("#roster-report_schoolSelect option:selected").val();
	values.rosterName =$('#roster-report_rosterSelect :selected').text();
	values.rosterId = $('#roster-report_rosterSelect :selected').val();
	values.subjectName = $('#roster-report_subjectSelect :selected').text();
	values.subjectId = $("#roster-report_subjectSelect option:selected").val();
	values.students =$('#studentRoster').select2("val");
	if(values.students != null && values.students.length > 0){
		$("#viewReportDiv").addClass("hidden");
		DLMRosterReport.findOne(values, function(report) {
			form.trigger('selected', report );
			reportsConfig.report = report;
			if(reportsConfig.currentControlInstance) {
				reportsConfig.currentControlInstance.destroy();
			}
			reportsConfig.currentControlInstance = renderReport(); 
		});					
	}
	else {
		$("#studentDiv #requiredmessage").show();
	}		
}); 
function prepareReportContent(data, paramsFromRequest){
	var $tabArea = $('.report-content', getContentElement());
	var summaryLevel=$('select[data-data-type="summaryLevel"]', getFilterElement()).val();
	if (reportsConfig.getReportType() === 'alternate_student_individual' || reportsConfig.getReportType() === 'alternate_student_individual_teacher'){	 
	 	can.view.render('js/views/reports-ui/alternateExternalStudentReport.ejs',{
			data : data,
			reportType :reportsConfig.getReportType(),
			programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
			assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()}
		, function(fragment){
			$tabArea.html(fragment);
		});			
    }
	else if (reportsConfig.getReportType() === 'alternate_blueprint_coverage'){    	
		can.view.render('js/views/new_reports/blueprintCoverage.ejs',{
 			data : data,
			paramsFromRequest: paramsFromRequest}
		, function(fragment){
 			$tabArea.html(fragment); 			
 			var teacherSelect = $('#blueprintCoverageTeacherDropdown');
 			var selectWidth = 375;
 	     	if (data !== undefined && data !== null && data.length > 0) {
				var isTeacher = $('#isNewReportsTeacher').val() === 'true';
				if (isTeacher){
					$('#blueprintCoverageTeacherDropdownDiv, #viewBlueprintCoverageReportButtonsDiv').hide();
					var params = paramsFromRequest;
					params['edIds'] = [data[0].id];
					params['groupByTeacher'] = $('#blueprintCoverageGroupByTeacher').prop('checked');
					DLMBlueprintCoverageReport.findOne(params, function(report){
						reportsConfig.report = report;
						if(reportsConfig.currentControlInstance) {
							reportsConfig.currentControlInstance.destroy();
						}
						reportsConfig.currentControlInstance = renderBlueprintCoverageReport(params);
					});
				} else {
					$('#blueprintCoverageTeacherDropdownDiv, #viewBlueprintCoverageReportButtonsDiv').show(); 						
					$('#viewBlueprintCoverage').off('click').on("click",function(){
						var val = $('#blueprintCoverageTeacherDropdown').select2("val"); 							
						var params = paramsFromRequest;
						params['edIds'] = val;
						params['groupByTeacher'] = $('#blueprintCoverageGroupByTeacher').prop('checked');
						DLMBlueprintCoverageReport.findOne(params, function(report){
							reportsConfig.report = report;
							if(reportsConfig.currentControlInstance) {
								reportsConfig.currentControlInstance.destroy();
							}
							reportsConfig.currentControlInstance = renderBlueprintCoverageReport(params);
						});
					});
				}
				teacherSelect.select2({placeholder:'Select', multiple: true, width: selectWidth});
 	     	} else {
				teacherSelect.select2({placeholder:'No teachers available', width: selectWidth});
			}
 		});			
	}else if (reportsConfig.reportType === 'alternate_student'){
		//reportContent.html(can.view('js/views/new_reports/alternateStudentReport.ejs', {data: data}));
		
		can.view.render('js/views/new_reports/alternateStudentReport.ejs',{
			data : data
			}
		, function(fragment){
			$tabArea.html(fragment);			
			var altStudents = $('#altStudentDropDown');			
			altStudents.empty();			
			altStudents.select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 	});
			altStudents.val('').trigger('change.select2');			
			if (data !== undefined && data !== null && data.length > 0) {
				altStudents.append($('<option></option>').val('').html('Select student'));	
		    		for (var i = 0; i < data.length; i++){
		    			altStudents.append("<option value='" + data[i].student.id + "' data-grade-name='"+data[i].gradeCourse.name+"'>" + data[i].student.legalFirstName + " " +  data[i].student.legalLastName + " (ID:" + data[i].student.stateStudentIdentifier + ")  " + "</option>");
		 			}
		    		if (data.length === 1){
						altStudents.val(data[0].id).change();
					}
		    		altStudents.val('').trigger('change.select2');
		    	}
			else {
				altStudents.select2({placeholder:'No students available'});
			}
		});
	}else if (reportsConfig.getReportType() === 'alternate_monitoring_summary' ){
		var canParams = {
			data: data,
			summaryLevel: paramsFromRequest.summaryLevel
		};
		
		can.view.render('js/views/new_reports/alternateMonitoringSummary.ejs', canParams, function(fragment){
			$tabArea.html(fragment);
			 
			$('#saveTestAdminMonitoringSummaryPDF', getReportContentElement()).off('click').unbind('click').on("click",function(){
				$(this).attr('disabled', 'disabled').prop('disabled', true);
				var location = 'getAlternateMonitoringSummaryPDF.htm?summaryLevel=' + encodeURIComponent(paramsFromRequest.summaryLevel);
				
				var districtIds = paramsFromRequest.districtId;
				if (districtIds){
					if (!Array.isArray(districtIds)){
						districtIds = [districtIds];
					}
					for (var x = 0; x < districtIds.length; x++){
						location += '&districtId=' + encodeURIComponent(districtIds[x]);
					}
				}
				
				var schoolIds = paramsFromRequest.schoolId;
				if (schoolIds){
					if (!Array.isArray(schoolIds)){
						schoolIds = [schoolIds];
					}					
					for (var x = 0; x < schoolIds.length; x++){
						location += '&schoolId=' + encodeURIComponent(schoolIds[x]);
					}
				}
				
				window.location = location;
				$(this).removeAttr('disabled').prop('disabled', false);
			});
			
		});
	}else if(reportsConfig.getReportType() === 'alternate_roster'){
		can.view.render('js/views/new_reports/alternateClassRoster.ejs',{
			data : data
			}
		, function(fragment){
			$tabArea.html(fragment); 
		var student = $('#studentRoster');
		student.empty();
		if (data !== undefined && data !== null && data.length > 0) {
			for (var i = 0; i < data.length; i++){
				student.append("<option value='" + data[i].id + "'>" + data[i].legalFirstName + " " +  data[i].legalLastName + " (ID:" + data[i].stateStudentIdentifier + ")  " + "</option>");
			}
			if (data.length != 1) {
				student.prop('disabled', false);
			}
			student.val('').trigger('change.select2');
			student.select2({placeholder:'Select Multiple Students'});
    	} else {
			student.select2({placeholder:'No students to display'});
		}
    	$('#studentRoster').select2({placeholder:'Select', multiple: true});
		});
	} else if(reportsConfig.getReportType() === 'alternate_student_all'){			
	 	can.view.render('js/views/new_reports/generalAllStudentReports.ejs',{
			data : data,
			reportType :reportsConfig.getReportType(),
			programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
			assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()}
		, function(fragment){
			$tabArea.html(fragment);
			loadBundledReportContent(data);
		});
		
	}
			else if (reportsConfig.getReportType() === 'alternate_student_summary' || reportsConfig.getReportType() === 'alternate_student_summary_teacher' || reportsConfig.getReportType() === 'alternate_student_dcps'){
			can.view.render('js/views/new_reports/alternateExternalStudentSummaryReport.ejs',{
				data : data,
				reportType :reportsConfig.getReportType(),
				programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()}	 			
	 		, function(fragment){
	 			$tabArea.html(fragment); 
	 		});
		}	
else if (reportsConfig.getReportType() === 'alternate_yearend_state_summary'){			
			can.view.render('js/views/new_reports/alternateStateSummary.ejs',{
				data : data
			}
	 		, function(fragment){
	 			$tabArea.html(fragment); 
	 		});			
	}
		else if (reportsConfig.getReportType() === 'alternate_yearend_district_summary'){			
			can.view.render('js/views/new_reports/alternateDistrictSummary.ejs',{
				data : data
			}
	 		, function(fragment){
	 			$tabArea.html(fragment); 
	 		});			
	}
		else if (reportsConfig.getReportType() === 'alternate_school_summary'){			
			can.view.render('js/views/new_reports/alternateSchoolSummaryReports.ejs',{
				data : data
			}
	 		, function(fragment){
	 			$tabArea.html(fragment); 
	 		});			
	}
		else if (reportsConfig.getReportType() === 'alternate_classroom'){			
			can.view.render('js/views/new_reports/alternateClassroomReports.ejs',{
				data : data
			}
	 		, function(fragment){
	 			$tabArea.html(fragment); 
	 		});			
	}
	
	else if (reportsConfig.getReportType() === 'alternate_student_summary_all'){		
			can.view.render('js/views/new_reports/alternateStudentSummaryBundledReports.ejs', {data: data}, function(fragment){
				$tabArea.html(fragment);
				loadStudentSummaryBundledReportContent(data);
			});
	}
	
	else if (reportsConfig.getReportType() === 'alternate_school_summary_all'){	
		can.view.render('js/views/new_reports/alternateSchoolSummaryBundledReports.ejs', {data: data}, function(fragment){
			$tabArea.html(fragment);
			if(data!=null && data!=undefined && data.length > 0){
				var date = new Date(data[0].createdDate);
				var opts = $.extend({}, $.jgrid.formatter.date, opts);
				$('#submittedDate').empty().append($.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts));
			}
		});
	}
	

}
function customFilterLoad(){
	
	var customFilters = $(null).add();
	var filters = reportsConfig.getFiltersAvailable();
	if (reportsConfig.getReportType() == 'alternate_monitoring_summary'){		
		if (filters.district){
			customFilters = customFilters.add(getFilterWithDataType('district'));
		}
		if (filters.school){
			customFilters = customFilters.add(getFilterWithDataType('school'));
		}
	} else if (reportsConfig.getReportType() == 'alternate_blueprint_coverage'){
		if (filters.subject){
			customFilters = customFilters.add(getFilterWithDataType('subject'));
		}
		if (filters.grade){
			customFilters = customFilters.add(getFilterWithDataType('grade'));
		}
	}
	
	if (customFilters.length > 0){
		customFilters.each(function(){
			var $this = $(this);
			// if the filter is disabled, wait until it becomes enabled to set the multiple option
			if (!$this.data('select2').options.options.disabled){
				$this.find('option[value="0"]').remove();
				$this.select2({
					multiple: true,
					closeOnSelect: true
				});
				$this.trigger('select2.change');
			}
		});
	}
}

function customFilterValidation(){
	var summaryLevel = getFilterWithDataType('summaryLevel').val();
	if (summaryLevel === 'state'){
		return true;
	} else if (summaryLevel === 'district'){
		var districtIds = getFilterWithDataType('district').val();
		return districtIds != null && districtIds.length > 0;
	} else if (summaryLevel === 'school'){
		var districtIds = getFilterWithDataType('district').val();
		var schoolIds = getFilterWithDataType('school').val();
		return districtIds != null && districtIds.length > 0 && schoolIds != null && schoolIds.length > 0;
	}
	
	return false;
}
function renderBlueprintCoverageReport(params){
	$('#blueprintCoverageReportDisplayDiv').show();
	return new blueprintCoverageReport('#blueprintCoverageReportDisplayDiv', {report: reportsConfig.report, params: params});
}
function renderAltStudentReport(report) {
	 $("#altReportDisplayDiv").removeClass("hidden");
	 return new studentReport('#altReportDisplayDiv',{report: report});
}
function renderReport() {
	 $("#reportDisplayDiv").removeClass("hidden");
	 return new rosterReportOnline('#reportDisplayDiv',{report: reportsConfig.report});
}

$('#reports-nav li.nav-item:first a').tab('show');	
$("#alternateAssessmentNav a").on('click', function() {
	var clickedURL = $(this).attr("id"); 	
	var clickedText = $('#'+clickedURL).text();
   	setReportAccess(clickedURL); 
});

$("#generalReportDropDown a").on('click', function() {
	displaySelectedText($(this).attr("id"));
});

$("#instructionallyEmbeddedReportDropDown a").on('click', function() {
	displaySelectedText($(this).attr("id"));
});

$("#endToYearReportDropdown a").on('click', function() {
	displaySelectedText($(this).attr("id"));
});

function displaySelectedText(clickedURL){
	var clickedText = $('#'+clickedURL).text();
	$('#displaySelectedMenu').html(clickedText);
}
		
var usersItemMenu = $('#reports-nav li.nav-item:first a');
if(usersItemMenu.length == 0){
	var clickedURL =$('.reports-nav a.dropdown-item').first().attr('id');
	displaySelectedText(clickedURL);
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
				$('#ALT_MONITORING_SUMMARY').show();
				$('#monitoring-summary').show()
			} else {
				$('#ALT_MONITORING_SUMMARY').hide();
				$('#monitoring-summary').hide()
			}
	});
}
