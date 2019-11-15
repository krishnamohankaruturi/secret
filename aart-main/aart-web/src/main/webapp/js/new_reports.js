var isDLM = false;
var isKAP = false;
var isAMP = false;
var isCPASS = false;
var isKELPA =false;
var generalExists = 0;
var cPassExists = 0;
var alternateExists = 0;
var kelpaExists = 0;

var isKSUser = false;
var isAKUser = false;
var isReportsBlockedAMP = false;


if($('#isNewReportsKSUser').val() == 'true'){
	isKSUser = true;
}

if($('#isNewReportsAKUser').val() == 'true'){
	isAKUser = true;
}

if($('#isNewReportsBlockedAMP').val()  == 'true'){
	isReportsBlockedAMP = true;
}

$("#dataExtracts").click(function() {
	$("#tabs_dataExtracts").removeClass("hidden");
	$("#tabs_default").addClass("hidden");
	$("#tabs_reportContent").addClass("hidden");
	$("#tabs_allReportsForStudent").addClass("hidden");	
});	

$("#dataExtractsTable").click(function() {
	if($("#tabs_default").hasClass( "hidden" )){
	 $("#tabs_reportContent").addClass("hidden");
	 $("#tabs_allReportsForStudent").addClass("hidden");	
	 $("#tabs_dataExtracts").removeClass("hidden");
	}			
});
$("#generalAssessmentsTable, #alternateAssessmentsTable, #cpassAssessmentsTable,#elpaAssesmentTable,#elpaAssessmentsTable").click(function() {
	
	$("#tabs_reportContent").removeClass("hidden");
	$("#tabs_default").addClass("hidden");
	$("#tabs_dataExtracts").addClass("hidden");	
	$("#tabs_allReportsForStudent").addClass("hidden");	
});


$(".allHeaders").click(function(event){
	 event.stopPropagation();
	 event.preventDefault();	
	//$("#filterLine").hide();
	//$("#textLine").hide();
	
});
$(".subtablereport").click(function(event){
	 event.stopPropagation();
	 event.preventDefault();
	//$("#filterLine").hide();
	//$("#textLine").hide();
});

$(".subtablereporttd").click(function(event){
	 event.stopPropagation();
	 event.preventDefault();	 
	//$("#filterLine").hide();
	//$("#textLine").hide();
});
$(function() {
	getAssessmentPrograms();
	$("#tabs_default").removeClass("hidden");
	$(".currentReport").removeClass("currentReport");
	$("#tabs_dataExtracts").addClass("hidden");
	$("#tabs_reportContent").addClass("hidden");
	$("#tabs_allReportsForStudent").addClass("hidden");	
	/*$("#generalAssessmentsTable").addClass("hidden");
	$("#alternateAssessmentsTable").addClass("hidden");
	$("#cpassAssessmentsTable").addClass("hidden");*/
});

var reportController = null;

$(".reportLinks").click(function(){
	$("#filterLine").show();
	$("#textLine").show();	
	var $this = $(this);	
	if ($this.hasClass('currentReport')) {
		return;
	}
	
	$(".currentReport").removeClass("currentReport");
	$this.closest("td").addClass("currentReport");
	
	var reportType = $this.data('report-type');
	var assessmentProgId = $this.attr('prog');
	var assessmentProgCode = $this.attr('progCode');
	var isInactiveOrgReq =$this.data('inactive-org-req');
	
	if (reportController != null){
		reportController.destroy();
	}
	
	reportController = new new_reportController($('#reportPane'), {
		reportType: reportType,
		assessmentProg : assessmentProgId,
		assessmentProgCode : assessmentProgCode,
		isInactiveOrgReq : isInactiveOrgReq
	});
});

function getAssessmentPrograms(){
	$.ajax({
		url: 'getAssessmentProgramsOfUser.htm',
		dataType: 'json',
		type: "POST",
		success: function(assessmentPrograms){
			if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
				$.each(assessmentPrograms, function(i, assessmentProgram) {
					if(assessmentProgram.abbreviatedname == 'KAP')
						isKAP = true;
					if(assessmentProgram.abbreviatedname == 'AMP')
						isAMP = true;
					if(assessmentProgram.abbreviatedname == 'DLM')
						isDLM = true;
					if(assessmentProgram.abbreviatedname == 'CPASS')
						isCPASS = true;
					if(assessmentProgram.abbreviatedname == 'KELPA2')						
						isKELPA = true;
				});
			}
			getReportNames();
			getReadyToViewFlags();
		}
	});
}

function getReportNames(){
	$.ajax({
		url: 'getReportNames.htm',
		dataType: 'json',
		type: "POST",
		success: function(reports){
			if (reports !== undefined && reports !== null && reports.length > 0) {
				$.each(reports, function(i, report) {	
					$("#"+report.categoryCode).append("<a>"+report.categoryName+"</a>");
				});	
			}
		}
	});
}


function getReadyToViewFlags(){

	$.ajax({	
		url: 'getReadyToViewFlagsForReports.htm',
		dataType: 'json',
		type: "POST",		
		success: function(reportDetails){			   
			   if (reportDetails !== undefined && reportDetails !== null && reportDetails.length > 0) {
			    $.each(reportDetails, function(i, reportDetail) {
			    	//$("#"+reportDetail.reportCode).append("<a>"+reportDetail.categoryName+"</a>");			    	
			     if((((reportDetail.abbreviatedName == 'KAP') && isKAP) && isKSUser) ||
			    		 (reportDetail.abbreviatedName == 'KELPA2' && isKELPA) ||
			    		 (((reportDetail.abbreviatedName == 'AMP') && (isAMP) ) && isAKUser )) { 			    	 
				      generalExists++;	
				  
				      $("#"+reportDetail.reportCode+"_tr").removeClass('hidden');
				      $("#"+reportDetail.reportCode).attr( 'prog',reportDetail.assessmentProgramId );
				      $("#"+reportDetail.reportCode).attr( 'progCode',reportDetail.abbreviatedName );
				      $("#"+reportDetail.reportCode).attr( 'readyToView',reportDetail.readyToView);
				      $("#"+reportDetail.reportCode).attr( 'access',reportDetail.access);
			     }
			   
			     if(((reportDetail.abbreviatedName == 'DLM') && (isDLM) ))    {
			    	
			      alternateExists++;
			      $("#"+reportDetail.reportCode+"_tr").removeClass('hidden');
			      $("#"+reportDetail.reportCode).attr( 'prog',reportDetail.assessmentProgramId );
			      $("#"+reportDetail.reportCode).attr( 'progCode',reportDetail.abbreviatedName );
			      $("#"+reportDetail.reportCode).attr( 'readyToView',reportDetail.readyToView);
			      $("#"+reportDetail.reportCode).attr( 'access',reportDetail.access);
			      if((reportDetail.reportCode == 'ALT_ST') || (reportDetail.reportCode == 'ALT_CR')){
			    	  $("#ALT_ST_CR_tr").removeClass('hidden');  
			      }
			      if((reportDetail.reportCode == 'ALT_ST_IND') || (reportDetail.reportCode == 'ALT_ST_ALL') || 
			    		  (reportDetail.reportCode == 'ALT_DS') || (reportDetail.reportCode == 'ALT_SS') || (reportDetail.reportCode == 'ALT_ST_SUM_ALL') || (reportDetail.reportCode == 'ALT_SCH_SUM_ALL') 
			    		  || (reportDetail.reportCode == 'ALT_STD_SUMMARY') || (reportDetail.reportCode == 'ALT_SCHOOL') || (reportDetail.reportCode == 'ALT_CLASS_ROOM')){
			    	  $("#ALT_IND_ALL_tr").removeClass('hidden');
			      }
			      if(reportDetail.reportCode === 'ALT_MONITORING_SUMMARY'){
			    	  $('#ALT_GENERAL_REPORTS_tr').removeClass('hidden');
			      }
			      if(reportDetail.reportCode == 'ALT_ST_SUM_ALL'){
			    	  $("#ALT_ST_SUM_ALL_tr").removeClass('hidden');  
			      }
			      if(reportDetail.reportCode == 'ALT_SCH_SUM_ALL'){
			    	  $("#ALT_SCH_SUM_ALL_tr").removeClass('hidden');  
			      }
			      if(reportDetail.reportCode == 'ALT_STD_SUMMARY'){
			    	  $("#ALT_STD_SUMMARY_tr").removeClass('hidden');  
			      }
			      if(reportDetail.reportCode == 'ALT_SCHOOL'){
			    	  $("#ALT_SCHOOL_tr").removeClass('hidden');  
			      }
			      if(reportDetail.reportCode == 'ALT_CLASS_ROOM'){
			    	  $("#ALT_CLASS_ROOM_tr").removeClass('hidden');  
			      }
			      if(reportDetail.reportCode == 'View_DLM_Students_DCPS'){
			    	  $("#ALT_ST_DCPS_tr").removeClass('hidden');  
			      }
			     }
			     if(((reportDetail.abbreviatedName == 'CPASS') && (isCPASS) ))
			     {     
			      cPassExists++;
			      $("#"+reportDetail.reportCode+"_tr").removeClass('hidden');
			      $("#"+reportDetail.reportCode).attr( 'prog',reportDetail.assessmentProgramId );
			      $("#"+reportDetail.reportCode).attr( 'progCode',reportDetail.abbreviatedName );
			      $("#"+reportDetail.reportCode).attr( 'readyToView',reportDetail.readyToView );
			      $("#"+reportDetail.reportCode).attr( 'access',reportDetail.access);
			     }
			     
			     if(((reportDetail.abbreviatedName == 'KELPA2') && (isKELPA) ))
			     {     
			    	 kelpaExists++;
			      $("#"+reportDetail.reportCode+"_tr").removeClass('hidden');
			      $("#"+reportDetail.reportCode).attr( 'prog',reportDetail.assessmentProgramId );
			      $("#"+reportDetail.reportCode).attr( 'progCode',reportDetail.abbreviatedName );
			      $("#"+reportDetail.reportCode).attr( 'readyToView',reportDetail.readyToView );
			      $("#"+reportDetail.reportCode).attr( 'access',reportDetail.access);
			     }			   			     
			    });
			   }
			   if((isKAP || isAMP || isKELPA) && generalExists > 0 ){	
				 
				   $('#generalAssessmentsTable').removeClass('hidden');
			   } else {
				   $('#generalAssessmentsTable').remove();
			   }
			   if(isDLM  && alternateExists > 0){
			     $('#alternateAssessmentsTable').removeClass('hidden');
			   }else {
			    $('#alternateAssessmentsTable').remove();
			   }
			   
			   if(isCPASS && cPassExists > 0){  
				   
			    $('#cpassAssessmentsTable').removeClass('hidden');			  
			   } else {
			    $('#cpassAssessmentsTable').remove();			   
			   }
			   
			   if(isKELPA && kelpaExists > 0){
			   $('#elpaAssessmentsTable').removeClass('hidden');
			    $('#elpaAssesmentTable').removeClass('hidden');
			    
			   } else {
			   $('#elpaAssessmentsTable').remove();
			    $('#elpaAssesmentTable').remove();
			    
			   }
			
			
			
		
		}
	});

	
	
	
	
}