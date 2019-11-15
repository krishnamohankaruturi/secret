$(function(){
	
});

function prepareReportContent(data, paramsFromRequest){
	var $tabArea = $('.report-content', getContentElement());
	var ejsFile = '';
	var ejsParams = {};
	var callback = null;
	switch (reportsConfig.getReportType()){
		case 'kelpa_student_individual':
			can.view.render('js/views/new_reports/generalStudentReport.ejs',{
				data : data,
				reportType :reportsConfig.getReportType(),
				programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()}
			, function(fragment){
				$tabArea.html(fragment);
			});	
			break;
		case 'kelpa_student_all':
		 	can.view.render('js/views/new_reports/generalAllStudentReports.ejs',{
				data : data,
				reportType :reportsConfig.getReportType(),
				programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
				assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()}
			, function(fragment){
				$tabArea.html(fragment);
				loadBundledReportContent(data);
			});
			break;
		default: break;
	}
}

$('#reports-nav li.nav-item:first a').tab('show');	
	$("#englishLanguageLearnersAssessmentNav a").on('click', function() {
	   var clickedURL = $(this).attr("id"); 	
	setReportAccess(clickedURL); 
	});
		
var usersItemMenu = $('#reports-nav li.nav-item:first a');
if(usersItemMenu.length == 0){
	var clickedURL = $('.reports-nav a.nav-link').first().attr('id');
	setReportAccess(clickedURL);  	
}	