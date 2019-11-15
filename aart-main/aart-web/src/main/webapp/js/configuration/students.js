$(function(){
	$('#studentNav li.nav-item:first a').tab('show');
	 $('li.get-val').on('click',function(e){
	    	var clickedURL = $(this).find("a").attr('href');     
	    	studentsInitNew(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });  
		
		var studentsItemMenu = $('#studentNav li.nav-item:first a');
		if(studentsItemMenu.length>0){
			var clickedURL = studentsItemMenu.attr('href');     
			studentsInitNew(clickedURL.substring(1, clickedURL.length));
		}
	    
});
function resetStudents(){
	$("#studentActions").val('').trigger('change.select2');
	$("#studentActions").select2();
	var options = sortDropdownOptions($("#studentActions option"));
	options.appendTo("#studentActions");	    	
	$('#studentActions').val("");
	$('#viewStudents').hide();
	$('#uploadEnrollmentDiv').hide();
	$('#uploadTecDiv').hide();
	$('#addStudents').hide();
	$('#uploadFirstContactDiv').hide();
	$('#exitStudentsDiv').hide();
	$('#findStudentsDiv').hide();
	
	$('#transferStudentsDiv').hide();
	$("#transferStudentSelectViewGrid").hide();
	$("#selectDestinationDistrictViewDiv").hide();
	$("#removeNotTransferredStudentFromDestinationViewDiv").hide();
	$("#transferDestinationConfirmationDiv").hide();
}


	function studentsInitNew(tabId){	
				if(tabId == ""){
				clearStudentGrids();
				$('#viewStudents').hide();
				$('#exitStudentsDiv').hide();
				$('#uploadEnrollmentDiv').hide();
				$('#uploadTecDiv').hide();
				$('#addStudents').hide();
				$('#uploadFirstContactDiv').hide();
				$('#transferStudentsDiv').hide();
				$('#findStudentsDiv').hide();
				findStudentLoadOnce = false;
			} else if(tabId == "uploadEnrollmentst" || tabId == "uploadEnrollment.xml") {
				$('#enrollmentUploadFileIcon').attr('src', 'images/icon-csv.png');
				clearStudentGrids();
				var categoryCode= "ENRL_RECORD_TYPE";
				if(tabId == 'uploadEnrollment.xml')
				{
					categoryCode = "ENRL_XML_RECORD_TYPE";
					$('#enrollmentUploadFileIcon').attr('src', 'images/icon-xml.png');
				}
				if(!gUploadEnrollmentLoadOnce){
					uploadEnrollmentInit(categoryCode);
					//Added for DE10643
                    $('#uploadEnrollmentOrgFilter').orgFilter('reset');
				}else{
		   			$('#uploadEnrollmentOrgFilter').orgFilter('reset');
		   			if($("#enrollmentGrid")[0].grid && $("#enrollmentGrid")[0]['clearToolbar']){
						$("#enrollmentGrid")[0].clearToolbar();
					}
		   			$('#enrollmentGrid').jqGrid('clearGridData');
				}
				
				$('#uploadEnrollmentOrgFilterForm')[0].reset();
				$('#breadCrumMessage').text("Configuration: Students - Upload Enrollment");
				$('#uploadEnrollmentDiv').hide();
				$('#exitStudentsDiv').hide();
				$('#uploadTecDiv').hide();
				$('#viewStudents').hide();
				$('#addStudents').hide();
				$('#uploadFirstContactDiv').hide();
				$('#transferStudentsDiv').hide();
				$('#findStudentsDiv').hide();
				$('#enrollmentUploadReport').html('');
				$("label.error").html('');
		   		$('#enrollmentUploadReportDetails').html('');
				$('#uploadEnrollmentDiv').show();
				$('#uploadEnrollmentSelectorDiv').show();
		    	$(".studentARTSmessages").show();
				setTimeout("aart.clearMessages()", 5000);
				setTimeout(function(){ $(".studentARTSmessages").hide(); },5000);
				loadEnrollmentUploadData(categoryCode);
				$("#ENR_TemplatedownloadquickHelpPopup").hide();
				$(".QuickHelpHint").hide();
			}else if(tabId == "uploadTEC") {
				clearStudentGrids();				
				if(!gUploadExitsLoadOnce){
					uploadExitsInit();
				}else{
		   			$('#uploadExitsOrgFilter').orgFilter('reset');
		   			if($("#exitsGrid")[0].grid && $("#exitsGrid")[0]['clearToolbar']){
						$("#exitsGrid")[0].clearToolbar();
					}
		   			$('#exitsGrid').jqGrid('clearGridData');
				}
				$('#exitsGrid').jqGrid('clearGridData');
				$('#uploadExitsOrgFilter').orgFilter('reset');
		    	$("#TEC_TemplatedownloadquickHelpPopup").hide();
				$('#uploadExitsOrgFilterForm')[0].reset();
				$('#breadCrumMessage').text("Configuration: Students - Upload Test, Exit, and Clear");
				$('#uploadEnrollmentDiv').hide();
				$('#uploadTecDiv').hide();
				$('#exitStudentsDiv').hide();
				$('#viewStudents').hide();
				$('#addStudents').hide();
				$('#uploadFirstContactDiv').hide();
				$('#transferStudentsDiv').hide();
				$('#findStudentsDiv').hide();
				$('#exitsUploadReport').html('');
				$("label.error").html('');
		   		$('#exitsUploadReportDetails').html('');
				$('#uploadTecDiv').show();
				$('#uploadTecSelectorDiv').show();
		    	$(".studentARTSmessages").show();
				setTimeout("aart.clearMessages()", 5000);
				setTimeout(function(){ $(".studentARTSmessages").hide(); },5000);
				loadTECUploadData();
			}else if(tabId == "addstudents") {
				
				clearStudentGrids();
					if(!gAddStudentsLoadOnce){
						checkStateStudentIdentifierInit();
					}else{
						$('#addStudentsForm')[0].reset();
			   		   	$('#addStudentOrgFilter').orgFilter('reset');
					}
					
					$('#addStudentsForm')[0].reset();
		   			$('#breadCrumMessage').text("Configuration: Students - Add Student Manually");
		   			$('#viewStudents').hide();
		   			$('#uploadEnrollmentDiv').hide();
		   			$('#exitStudentsDiv').hide();
		   			$('#uploadTecDiv').hide();
		   			$('#uploadFirstContactDiv').hide();
		   			$('#transferStudentsDiv').hide();
		   			$('#findStudentsDiv').hide();
		   			$("label.error").html('');
			    	$('#addStudents').show();				   					
					$(".studentARTSmessages").hide();
					$('#successMessage').hide();
					/*setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#orgARTSmessages").hide(); },5000);*/
					resetAddStudentIdentifierSearch();					
		   		} else if((tabId == "viewStudents")) {
		   			clearStudentGrids();
		   			$('#viewStudentsOrgFilterForm')[0].reset();	
					if(!viewStudentLoadOnce){						
		   				viewStudentsInit();
					}else{
						$('#viewStudentsOrgFilter').orgFilter('reset');
			   			$("#viewStudentsByOrgTable").jqGrid('clearGridData');
			   			
			   			/*if($("#viewStudentsByOrgTable")[0].grid && $("#viewStudentsByOrgTable")[0]['clearToolbar']){
							$("#viewStudentsByOrgTable")[0].clearToolbar();
						}*/
			   			$grid = $("#viewStudentsByOrgTable");
						var myStopReload = function () {
				            $grid.off("jqGridToolbarBeforeClear", myStopReload);
				            return "stop"; // stop reload
				        };
				        $grid.on("jqGridToolbarBeforeClear", myStopReload);
				        if ($grid[0].ftoolbar) {
				        	$grid[0].clearToolbar();
						}
					}
		   			$('#breadCrumMessage').text("Configuration: Students - View Students");
		   			$('#addStudents').hide();
		   			$('#exitStudentsDiv').hide();
		   			$('#uploadEnrollmentDiv').hide();
					$('#uploadTecDiv').hide();
		   			$('#uploadFirstContactDiv').hide();
		   			$('#transferStudentsDiv').hide();
		   			$('#findStudentsDiv').hide();
					$("label.error").html('');

		   			$('#viewStudents').show();
		    		$(".studentARTSmessages").show();
					setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#orgARTSmessages").hide(); },5000);
		   		} else if(tabId == "uploadFirstContact") {
		   			clearStudentGrids();
		   			$('#breadCrumMessage').text("Configuration: Students - Upload FirstContact");
		   			$('#addStudents').hide();
		   			$('#uploadEnrollmentDiv').hide();
					$('#uploadTecDiv').hide();
		   			$('#viewStudents').hide();
		   			$('#findStudentsDiv').hide();
		   			$('#exitStudentsDiv').hide();
		   			$('#transferStudentsDiv').hide();
			   		$('#uploadFirstContactOrgFilterForm')[0].reset();
			   		$('#uploadFirstContactDiv').show();
			   		$(".studentARTSmessages").show();
					setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#orgARTSmessages").hide(); },5000);
		   		} else if(tabId == "exitStudents") {
		   			clearStudentGrids();
		   			if(!StudentExitLoadOnce){
			   		 	UnrollStudentsInit();
		   			}else{		   				
						$('#UnrollviewStudentsOrgFilter').orgFilter('reset');
			   			$("#UnrollviewStudentsByOrgTable").jqGrid('clearGridData');
			   			$grid = $("#UnrollviewStudentsByOrgTable");
			   			
						var myStopReload = function () {
				            $grid.off("jqGridToolbarBeforeClear", myStopReload);
				            return "stop"; // stop reload
				        };
				        $grid.on("jqGridToolbarBeforeClear", myStopReload);
				        if ($grid[0].ftoolbar) {
				        	$grid[0].clearToolbar();
				        }
					}
		   			$('#breadCrumMessage').text("Configuration: Students - Exit Student Manually");
		   			$('#viewStudents').hide();
		   			$('#uploadEnrollmentDiv').hide();
		   			$('#addStudents').hide();
		   			$('#uploadFirstContactDiv').hide();
		   			$('#uploadTecDiv').hide();
		   			$('#transferStudentsDiv').hide();
		   			$('#findStudentsDiv').hide();
		   			$('#exitStudentsDiv').show();
		    		$(".studentARTSmessages").show();
					setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#orgARTSmessages").hide(); },5000);
				} else if(tabId == "transferStudents") {
					clearStudentGrids();
					$('#viewStudentsOrgFilterForm')[0].reset();
					$('#transferStudentsGridNext').attr('disabled','disabled').addClass('ui-state-disabled');
					if(!transferStudentLoadOnce){						
						transferStudentInitmethod();
					}else{						
						$('#transferStudentsOrgFilter').orgFilter('reset');
			   			$("#transferStudentsByOrgTable").jqGrid('clearGridData');
			   			
			   			/*if($("#viewStudentsByOrgTable")[0].grid && $("#viewStudentsByOrgTable")[0]['clearToolbar']){
							$("#viewStudentsByOrgTable")[0].clearToolbar();
						}*/
			   			$grid = $("#transferStudentsByOrgTable");
						var myStopReload = function () {
				            $grid.off("jqGridToolbarBeforeClear", myStopReload);
				            return "stop"; // stop reload
				        };
				        $grid.on("jqGridToolbarBeforeClear", myStopReload);
				        if ($grid[0].ftoolbar) {
				        	$grid[0].clearToolbar();
						}
					}					
					$('#viewStudents').hide();
					$('#exitStudentsDiv').hide();
					$('#uploadEnrollmentDiv').hide();
					$('#uploadTecDiv').hide();
					$('#addStudents').hide();
					$('#uploadFirstContactDiv').hide();
					$('#findStudentsDiv').hide();
					$('#transferStudentsDiv').show();
					$("#transferStudentSelectViewGrid").show();
					$("#selectDestinationDistrictViewDiv").hide();
					$("#removeNotTransferredStudentFromDestinationViewDiv").hide();
					$("#transferDestinationConfirmationDiv").hide();
				} else if(tabId == "findStudents"){
					clearStudentGrids();
					$('#findStudentsFilterForm')[0].reset();
					$('.errorNoStudent').hide();
		   			$('#addStudents').hide();
		   			$('#exitStudentsDiv').hide();
		   			$('#uploadEnrollmentDiv').hide();
					$('#uploadTecDiv').hide();
		   			$('#uploadFirstContactDiv').hide();
		   			$('#transferStudentsDiv').hide();
		   			$("label.error").html('');
		   			$('#viewStudents').hide();
		   			$('#findStudentsDiv').show();
		    		//$(".studentARTSmessages").show();
					setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#orgARTSmessages").hide(); },5000);
				} else if (tabId == 'uploadPNP'){
					$('#viewStudents').hide();
					if (!gUploadPNPLoadOnce){
						uploadPNPInit();
					}
					$('#uploadPNPOrgFilter').orgFilter('reset');
					
					$('#findStudentsFilterForm')[0].reset();
					$('.errorNoStudent').hide();
		   			$('#addStudents').hide();
		   			$('#exitStudentsDiv').hide();
		   			$('#uploadEnrollmentDiv').hide();
					$('#uploadTecDiv').hide();
		   			$('#uploadFirstContactDiv').hide();
		   			$('#transferStudentsDiv').hide();
		   			$("label.error").html('');
		   			$('#findStudentsDiv').hide();
		    		//$(".studentARTSmessages").show();
					setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#orgARTSmessages").hide(); },5000);
				}

		
	}
	function clearStudentGrids(){
			$("#transferStudentsByOrgTable").jqGrid('clearGridData');
			$("#removeNotTransferredStudentFromDestinationTable").jqGrid('clearGridData');
			$("#viewConfirmationGridTable").jqGrid('clearGridData');
			$("#destinationDistrictByOrgTable").jqGrid('clearGridData');					
			$("#viewStudentsByOrgTable").jqGrid('clearGridData');		
			$("#UnrollviewStudentsByOrgTable").jqGrid('clearGridData');
	}