 $(function(){	
	$('#reportsetupDiv li.nav-item:first a').tab('show');	
	 $('li.get-reportsetup').on('click',function(e){		 
	    	var clickedURL = $(this).find("a").attr('href');     
	    	reportSetUpInitNew(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });   	
	 var usersItemMenu = $('#reportsetupDiv li.nav-item:first a');
	  if(usersItemMenu.length>0){
	   var clickedURL = usersItemMenu.attr('href');     
	   reportSetUpInitNew(clickedURL.substring(1, clickedURL.length));
	  }
	  $('td[id^="view_"]').on("click",function(){
		  	$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
		});
}); 
 function reportSetUpInitNew(action){
				if(action == 'tabs_reportsetup_reportaccess') {
				$('#tabs_reportsetup_uploads').hide();
				$('#tabs_reportsetup_uploadResults').hide();
				$('#tabs_reportsetup_reportaccess').show();
				$('#tabs_reportsetup_manageGRF').hide();
				//summativeGridRefreshInterval = 'no_APs';
				clearInterval(summativeGridRefreshInterval);			
				clearReportPage();
			}else if(action == 'tabs_reportsetup_uploads'){
				$('#tabs_reportsetup_reportaccess').hide();
				$('#tabs_reportsetup_uploadResults').hide();
				$('#tabs_reportsetup_uploads').show();
				$('#tabs_reportsetup_manageGRF').hide();
				resetUploadReport();
				loadUploadGrid(false);
				if (summativeGridRefreshInterval !== 'no_APs'){
    				summativeGridRefreshInterval = setInterval(function(){
    					loadUploadGrid(false);
    				}, 10 * 1000);  
				 }
			} else if(action == 'tabs_reportsetup_uploadResults'){
				resetUploadResults();
				$('#tabs_reportsetup_reportaccess').hide();
				$('#tabs_reportsetup_uploadResults').show();
				$('#tabs_reportsetup_uploads').hide();
				$('#tabs_reportsetup_manageGRF').hide();
				loadUploadResultsAssessmentPrograms();
				loadUploadResultGrid(false);
				if (summativeGridRefreshInterval !== 'no_APs_result'){
    				summativeGridRefreshInterval = setInterval(function(){
    					loadUploadResultGrid(false);
    				}, 50 * 1000);  
				 }				
			}	 else if(action == 'tabs_reportsetup_manageGRF'){
				
				resetManageGrfUploads();
				$('#tabs_reportsetup_reportaccess').hide();
				$('#tabs_reportsetup_uploadResults').hide();
				$('#tabs_reportsetup_uploads').hide();
				$('#tabs_reportsetup_manageGRF').show();
				$('#editGRFStudentInformation').hide();
				loadRecentAction();
				loadGRFGridTable(false);
    				summativeGridRefreshInterval = setInterval(function(){
    					loadGRFGridTable(false);
    				}, 50 * 1000);  
			}		
		}
	function resetReportSetupGrids(){
		$('#uploadSummativeReportDataTableId').jqGrid('clearGridData');
		$('#uploadResultsReportDataTableId').jqGrid('clearGridData');
		$('#manageGRFReportDataTableId').jqGrid('clearGridData');		
	}