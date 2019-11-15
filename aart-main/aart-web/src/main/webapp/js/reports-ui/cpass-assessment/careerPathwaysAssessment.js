$(function(){
	
});

function prepareReportContent(data, paramsFromRequest){
	var $tabArea = $('.report-content', getContentElement());
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
	 else if (reportsConfig.getReportType() === 'general_school_detail'){
	 	can.view.render('js/views/new_reports/generalSchoolDetails.ejs',{
			data : data,
			programCode: $('#userDefaultAssessmentProgram option[selected="selected"]').text().trim(),
			assessmentProgid: $('#userDefaultAssessmentProgram option[selected="selected"]').val()}
		, function(fragment){
			$tabArea.html(fragment);
		});
	 }
		else if (reportsConfig.getReportType() === 'alternate_student_all'){
		 
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
}

$('#reports-nav li.nav-item:first a').tab('show');		 
$("#careerPathwaysAssessmentNav a").on('click', function() { 
	   var clickedURL = $(this).attr("id");	 	  
	   setReportAccess(clickedURL); 
	}); 				
var usersItemMenu = $('#reports-nav li.nav-item:first a'); 		
if(usersItemMenu.length == 0){ 			
	 clickedURL = $('.reports-nav a.nav-link').first().attr('id');  			   
	 setReportAccess(clickedURL);  
}		
