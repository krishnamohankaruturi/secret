$(document).ready(function(){	
	$('#generalNav li.nav-item:first a').tab('show');
	  $('li.get-general').on('click',function(e){		  
	    	var clickedURL = $(this).find("a").attr('href');     
	    	generalSettingConfig(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });   
	  var generalsettingsItemMenu = $('#generalNav li.nav-item:first a');
	  if(generalsettingsItemMenu.length>0){
	   var clickedURL = generalsettingsItemMenu.attr('href');     
	   generalSettingConfig(clickedURL.substring(1, clickedURL.length));
	  }	
});

function generalSettingConfig(tabId){	
	if(tabId == "set-academic-year"){
		orgAcademicYearInit();
	}else if(tabId =="annual-reset"){
		initAnnualFCSReset();
	}
}