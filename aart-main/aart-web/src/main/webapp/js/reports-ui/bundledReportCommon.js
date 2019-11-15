
$(document).ready(function(){
	
	var totalSchoolLength = 0; 
	var totalGradesLength = 0; 
	var totalSubjectLength = 0;
	
	$(document).on('click', '#newBundledReport', function(element, event){
				
				clearTimeout(ref);
				ref = 'started';
				checkRef = this;
				// New Bundled Report Click		
				$('.allStudentsReportsFile').hide();
				$('.studentBundledReportStatus').show();
				$('#schoolSeparateSelect').prop('disabled', true);
			    $('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
			    $('#schoolSeparateSelect').css({"cursor":"text"});
			    
				$('#districtSeparateSelect').prop('disabled', true); 
				$('#districtSeparateSelectDiv').addClass('separateSelectDiv');
			    $('#districtSeparateSelect').css({"cursor":"text"});
                $('#bundledContentAreaLastName').hide();
                $('#bundledGradeLastName').hide();
			    var studentBundledReportSchool = $('#studentBundledReportSchool');
				
					var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
					
					// student bundled school event
					var form = $("#report-filter-form"),
					values = can.deparam(form.serialize());
					districtOrgId  = $('select[data-data-type="district"]', getFilterElement()).val();
					
					//getOrgsBasedOnUserContext.htm
					
				if(schoolLevel == 0 || schoolLevel == null){			
					$.ajax({
					url: 'getBundledReportOrg.htm',
					data: {
					districtId : districtOrgId,					
					assessmentProgId : $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
					assessmentProgCode : reportsConfig.getAssessmentProgramCode(),
					reportType :  reportsConfig.getReportType(),
					reportCode : reportsConfig.getReportCode()
					},
					dataType: 'json',
					type: "GET"
				}).done(function(schoolOrgs) {
						
						totalSchoolLength =schoolOrgs.length ;
						
								
					$.each(schoolOrgs, function(i, schoolOrg) {
						if(totalSchoolLength != 0){	
							studentBundledReportSchool.append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
						}else{
							studentBundledReportSchool.append($('<option selected="select"></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
						}
					});
					
					studentBundledReportSchool.val('').trigger('change.select2');
					if(totalSchoolLength != 1){
						studentBundledReportSchool.val('').trigger('change.select2');
						studentBundledReportSchool.select2({placeholder:'Select'}); 	
					}else {
						loadStudentBundledReportContentAreas();
					}
					
				});
					
					$('#studentBundledDistrictBottom').show();
					$('#studentBundledContentAreaDiv').addClass('studentBundledContentAreaDivSchool');
				
					loadPdfInformation($('#userDefaultAssessmentProgram option[selected="selected"]').val(),districtOrgId);
				} else {
					
					$('#studentBundledContentAreaDiv').addClass('studentBundledContentAreaDivSchoolTop');
						$('#studentBundledReportSchoolDiv').hide();
						$('#studentBundledDistrictBottom').hide();				
						$('#studentBundledSchoolBottom').show();
						loadStudentBundledReportContentAreas();		
						loadPdfInformation($('#userDefaultAssessmentProgram option[selected="selected"]').val(),schoolLevel);
				}			
				studentBundledReportSchool.val('').trigger('change.select2');
				
				
				//grades
				var studentBundledReportGrades = $('#studentBundledReportGrades');
				studentBundledReportGrades.select2({placeholder:'Select'});
				studentBundledReportGrades.val('').trigger('change.select2');
				
				// content area
				var studentBundledReportContentAreas = $('#studentBundledReportContentAreas');
				studentBundledReportContentAreas.select2({placeholder:'Select'});
				studentBundledReportContentAreas.val('').trigger('change.select2');
		
	});
	
	
	$(document).on('click', '#newStudentSummaryBundledReport', function(element, event){

		clearTimeout(ref);
		ref = 'started';
		checkRef = this;
		// New Bundled Report Click		
		$('.allStudentsReportsFile').hide();
		$('.studentSummaryBundledReportStatus').show();
		$('#schoolSummarySeparateSelect').prop('disabled', true);
	    $('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
	    $('#schoolSummarySeparateSelect').css({"cursor":"text"});
	    
		$('#districtSummarySeparateSelect').prop('disabled', true); 
		$('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSummarySeparateSelect').css({"cursor":"text"});
	    
	    var assessmentProgId = $('#userDefaultAssessmentProgram option[selected="selected"]').val();
	    
	    var studentBundledReportSchool = $('#studentSummaryBundledReportSchool');
		
			var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
			
			// student bundled school event
			var form = $("#report-filter-form"),
			values = can.deparam(form.serialize());
			districtOrgId  = $('select[data-data-type="district"]', getFilterElement()).val();
			
			//getOrgsBasedOnUserContext.htm
			
		if(schoolLevel == 0 || schoolLevel == null){			
			$.ajax({
			url: 'getBundledReportOrg.htm',
			data: {
			districtId : districtOrgId,
			assessmentProgId : assessmentProgId,
			assessmentProgCode : reportsConfig.getAssessmentProgramCode(),
			reportType : reportsConfig.getReportType(),
			reportCode : reportsConfig.getReportCode()
			},
			dataType: 'json',
			type: "GET"
		}).done(function(schoolOrgs) {
				
				totalSchoolLength =schoolOrgs.length ;
				
						
			$.each(schoolOrgs, function(i, schoolOrg) {
				if(totalSchoolLength != 0){	
					studentBundledReportSchool.append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				}else{
					studentBundledReportSchool.append($('<option selected="select"></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				}
			});
			
			studentBundledReportSchool.val('').trigger('change.select2');
			if(totalSchoolLength != 1){
				studentBundledReportSchool.val('').trigger('change.select2');
				studentBundledReportSchool.select2({placeholder:'Select'}); 	
			}else {
				studentSummaryBundledReportGrade();
			}
			
		});
			
			$('#studentSummaryBundledDistrictBottom').show();
			
			loadSummaryPdfInformation(assessmentProgId, districtOrgId);
		} else {
			
				$('#studentSummaryBundledReportSchoolDiv').hide();
				$('#studentSummaryBundledDistrictBottom').hide();				
				$('#studentSummaryBundledSchoolBottom').show();
				studentSummaryBundledReportGrade();	
				loadSummaryPdfInformation(assessmentProgId, schoolLevel);
		}			
		studentBundledReportSchool.val('').trigger('change.select2');
		
		
		//grades
		var studentBundledReportGrades = $('#studentSummaryBundledReportGrades');
		studentBundledReportGrades.select2({placeholder:'Select'});
		studentBundledReportGrades.val('').trigger('change.select2');		
		
	});

	$(document).on('click', '#studentBundledPrev', function(element, event){	
		clearTimeout(ref);
		ref = 'refreshed';
		$('.allStudentsReportsFile').show();
		$('.studentBundledReportStatus').hide();
		// refresh select option
		$('#studentBundledReportSchool').val('').trigger('change.select2');
		$('#studentBundledReportSchool').val('').trigger('change.select2');
		
		$('#studentBundledReportContentAreas').val('').trigger('change.select2');
		$('#studentBundledReportContentAreas').val('').trigger('change.select2');
		
		$('#studentBundledReportGrades').val('').trigger('change.select2');
		$('#studentBundledReportGrades').val('').trigger('change.select2');
		
		// refresh sort
		$('#sortFirstBy').val('').trigger('change.select2');
		$('#sortSecondBy').val('').trigger('change.select2');
		$('#sortLastBy').val('').trigger('change.select2');
		 
		// refresh checkbox
		$('#districtBundledSelect').attr('checked', false); 
		$('#districtSeparateSelect').attr('checked', false); 
		$('#schoolBundledSelect').attr('checked', false); 
		$('#schoolSeparateSelect').attr('checked', false); 
		$('#separateBundledReportSelect').attr('checked', false);
		
		if($('select[data-data-type="school"]', getFilterElement()).val() == 0 || $('select[data-data-type="school"]', getFilterElement()).val() == null){
		  $('select[data-data-type="district"]', getFilterElement()).trigger("change");
		}else{
		  $('select[data-data-type="school"]', getFilterElement()).trigger("change");	
		}
		
	});
	
	$(document).on('change', '#studentBundledReportContentAreas', function(element, event){	
		$('#studentBundledReportGrades').empty();
		$('#studentBundledReportGrades').val('').trigger('change.select2');
		studentBundledReportGrade();		
	});	
	
	
	$(document).on('click', '#submitBundledReport', function(element, event){
		
		var selectSchooltext=[];
		var selectSchoolNames=[];
		var schoolId = $('select[data-data-type="school"] option:selected', getFilterElement()).val();
		
		$("#studentBundledReportSchool option:selected").each(function(){
       	 var option = $(this);
       	 selectSchooltext.push(option.val());
         selectSchoolNames.push(option.text());
        });
            
      	if(selectSchoolNames != '' && totalSchoolLength == selectSchooltext.length)
      		{
      			selectSchoolNames = [];
      			selectSchoolNames.push("All");
      		}
      	
      	var selectSubjecttext=[];
        var selectSubjectNames=[];
        $("#studentBundledReportContentAreas option:selected").each(function(){
        	 var option = $(this);
        	 selectSubjecttext.push(option.val());
        	 selectSubjectNames.push(option.text());
        });
     
        if(totalSubjectLength == selectSubjecttext.length){
        	selectSubjectNames=[];
        	selectSubjectNames.push("All");
      	}
        
        var selectGradetext=[];
        var selectGradeNames=[];     
        $("#studentBundledReportGrades option:selected").each(function(){
       	 var option = $(this);
       	 selectGradetext.push(option.val());
       	 selectGradeNames.push(option.text());
        });
        
      if(totalGradesLength == selectGradetext.length){
    	  selectGradeNames=[];
    	  selectGradeNames.push("All");
      }
      
    if((schoolId == '0' || schoolId == null)  && selectSchooltext == '')
 	   {
 	   $('#emptyStudentbundlereportform').html("<span>Select School</span>");
			setTimeout(function(){ $("#emptyStudentbundlereportform").html("&nbsp;"); },4000);
			return;
 	   }
    if(selectSubjecttext =='')
 	   {
 	   $('#emptyStudentBundledReportContentAreas').html("<span>Select Subject</span>");
 	   setTimeout(function(){ $("#emptyStudentBundledReportContentAreas").html("&nbsp;"); },4000);
 	   return;
 	   }
    if(selectGradetext =='')
 	   {
    	
 	   $('#emptyStudentBundledReportGrades').html("<span>Select Grade</span>");
 	   setTimeout(function(){ $("#emptyStudentBundledReportGrades").html("&nbsp;"); },4000);
 	   return;
 	   }
        
    //sort option
    	var selectFirstSort= $('#sortFirstBy option:selected').val(); 
    	var selectSecondSort= $('#sortSecondBy option:selected').val();
    	var selectLastSort= $('#sortLastBy option:selected').val();
    
    	if(selectFirstSort == undefined) selectFirstSort =  0;
    	if(selectSecondSort == undefined) selectSecondSort =  0;
    	if(selectLastSort == undefined) selectLastSort = 0;
  
    	if(selectFirstSort == "0")
    		{
			   $('#emptySortFirstBy').html("<span>Select First Sort</span>");
			   setTimeout(function(){ $("#emptySortFirstBy").html("&nbsp;"); },4000);
			   return;
    		}
    	if(selectSecondSort =="0" && selectFirstSort!="legallastname")
    		{
			  $('#emptySortSecondBy').html("<span>Select Second Sort</span>");
			   setTimeout(function(){ $("#emptySortSecondBy").html("&nbsp;"); },4000);
			   return;
    		}
    	if(selectLastSort =="0" && (selectSecondSort!="legallastname" && selectFirstSort!="legallastname"))
    		{
			  $('#emptySortLastBy').html("<span>Select Last Sort</span>");
			  setTimeout(function(){ $("#emptySortLastBy").html("&nbsp;"); },4000);
			  return;
			 }  
        
       
   //district combobox option select
    	var  districtBundledSelect =  $('#districtBundledSelect').prop('checked');
    	var districtSeparateSelect = $('#districtSeparateSelect').prop('checked');
    	var schoolBundledSelect = $('#schoolBundledSelect').prop('checked');
    	var schoolSeparateSelect = $('#schoolSeparateSelect').prop('checked');
    	var separateBundledReportSelect = $('#separateBundledReportSelect').prop('checked');
    	
    	var organizationId  = '';
    	 
    	var districtId = $('select[data-data-type="district"] option:selected', getFilterElement()).val();
    	
    	var assessmentProgId = $('#userDefaultAssessmentProgram option[selected="selected"]').val();
    	 
    	if(schoolId == 0 || schoolId == null){
    		 organizationId = districtId;
    	} else {
    		 organizationId = schoolId;
    		 
    	}   
    	
    	if((schoolId == 0 || schoolId == null) && !districtBundledSelect && !schoolBundledSelect){
    		
    		 $('#studentBundledSelectError').html("<span>Select options for District/School bundled.</span>");
			  setTimeout(function(){ $("#studentBundledSelectError").html("&nbsp;"); },4000);
			  return;
    	 } 	
    	 var separateFile = false;
    	 if(districtSeparateSelect || separateBundledReportSelect || schoolSeparateSelect){
    		 separateFile = true;
    	 }
    	 if(schoolBundledSelect && selectSchooltext.length > 1){
    		 separateFile = true; 
    	 }
    	 if(schoolBundledSelect && districtBundledSelect){
    		 separateFile = true;
    	 }
    	 $('#submitBundledReport').prop('disabled', true);
    	//showing popup        
        	$.ajax({
        		url: 'getEstimatedFileSizeForSelectedFilters.htm',
        		dataType: 'json',
        		data: {
        			organizationId : organizationId,
        			assessmentProgramId : assessmentProgId,
        			schoolIds : selectSchooltext,
        			subjectIds : selectSubjecttext,			
        			gradeIds : selectGradetext,
        			separateFile : separateFile,
        			reportCode : reportsConfig.getReportCode()					
        			},
        			type: 'GET'
        		}).done(function(data){
        				data.totalSize = data.totalSize/1024;//Size will be converted from kb to mb
        				if(data.totalSize > 100){             
        		             $('#bundledReportSize').empty().append('<p>Note - this file will be very large. A rough estimate is '+ Math.round(parseFloat(data.totalSize)) +' MB</p><p>');
        		            }else{
        		             $('#bundledReportSize').empty();
        		            }
        		            if(data.inProgress){
        		             $('#bundledReportStatus').empty().append('<p>Already one more request is in progress for same organization.</p>');
        		            }else{
        		             $('#bundledReportStatus').empty();
        		            }
        		            
        				var byOrganization = false;
        				if((schoolId != 0 && schoolId!=null) || districtBundledSelect){
        					byOrganization = true;
        				}
        				
        				var separateFile = false;
        				if(districtSeparateSelect || separateBundledReportSelect){
        					separateFile = true;
        				}
        				
        				if(byOrganization && schoolBundledSelect){
        					$('#popupText').empty().append('district and school(s)');
        				}else if(schoolId != 0 && schoolId!=null){
        					$('#popupText').empty().append('school');
        				}else if(schoolId == 0 || schoolId == null){
        					$('#popupText').empty().append('district');
        				}        				
        				
        				$('#submitBundledReport').prop('disabled', false);
        				// popup         				
        				var dialog = $('#submitBundledReportDialog').dialog({
        					resizable: false,
        					width: 585,
        					modal: true,
        					autoOpen: true,
        					dialogClass: '_bcg',
        					title: 'Create new bundled report(s)',
        					create: function(event, ui){
        						$('#submitBundledReport').prop('disabled', false);
        						var widget = $(this).dialog("widget");
        						$(".ui-dialog-titlebar-close span", widget).removeClass('ui-button-icon-primary').removeClass('ui-button-text');
        					},
        				buttons: {
        					
        					'Cancel': {            
        				            'click': function() {
        				            	$('#submitBundledReport').prop('disabled', false);
                						$(this).dialog('close');
        				            },
        				            'text' : 'Cancel',         
        				            'class': 'btn_blue'
        				     },
        				        
        				     'Continue': {            
     				            'click': function() {
            						$.ajax({
            			        		url: 'requestForDynamicBundle.htm',
            			        		dataType: 'json',
            			        		data: {
            			        			organizationId : organizationId,
            			        			assessmentProgramId : assessmentProgId,
            			        			schoolIds : selectSchooltext,
            			        			subjectIds : selectSubjecttext,			
            			        			gradeIds : selectGradetext,
            			        			schoolNames : selectSchoolNames,
            			        			subjectNames : selectSubjectNames,
            			        			gradeNames : selectGradeNames,
            			        			sort1 : selectFirstSort,
            			        			sort2 : selectSecondSort,
            			        			sort3 : selectLastSort,
            			        			byOrganization : byOrganization,
            			        			separateFile : separateFile,
            			        			bySchool : schoolBundledSelect,
            			        			separateFileForSchool : schoolSeparateSelect,
            			        			reportCode : reportsConfig.getReportCode()
            			        			},
            			        			type: 'POST'
            			        			}).done(function(data){
            			        				$('#submitBundledReport').prop('disabled', false);
            			        				clearTimeout(ref);	
            			        				loadPdfInformation(assessmentProgId, organizationId);
            			        				// scrollTop       			        					        				
            			        				$(".allStudents").animate({ scrollTop: 0}, "fast");
            			        				
            			        		});
            						
            						$('#bundledContentAreaLastName').hide();
            						$('#bundledGradeLastName').hide();
            						
            						// close 
            					$(this).dialog('close');
            					// refresh select option
    	        					$('#studentBundledReportSchool').val('').trigger('change.select2');
    	        					$('#studentBundledReportContentAreas').val('').trigger('change.select2');
    	        					$('#studentBundledReportGrades').val('').trigger('change.select2');
    	        					
    	        					if(totalSchoolLength !=1){
    	        			             $('#studentBundledReportSchool').val('').trigger('change.select2');
    	        			          } 
    	        			        if(totalSubjectLength !=1){
    	        			             $('#studentBundledReportContentAreas').val('').trigger('change.select2');
    	        			          } 
    	        			        if(totalGradesLength !=1 )
    	        			          {
    	        			             $('#studentBundledReportGrades').val('').trigger('change.select2');
    	        			          }
    	        					
    	        					
    	        					// refresh sort
    	        					$('#sortFirstBy').val('').trigger('change.select2');
    	        					$('#sortFirstBy').prop('disabled', false);
    	        					$('#sortSecondBy').val('').trigger('change.select2');
    	        					$('#sortSecondBy').prop('disabled', false);
    	        					$('#sortLastBy').val('').trigger('change.select2');
    	        					$('#sortLastBy').prop('disabled', false);
    	        					 
    	        					// refresh checkbox
    	        					$('#districtBundledSelect').attr('checked', false); 
    	        					$('#districtSeparateSelect').attr('checked', false); 
    	        					$('#schoolBundledSelect').attr('checked', false); 
    	        					$('#schoolSeparateSelect').attr('checked', false); 
    	        					$('#separateBundledReportSelect').attr('checked', false);
    	        					$('#submitBundledReport').prop('disabled', false);
            					
     				            },
     				            'text' : 'Continue',         
     				            'class': 'btn_blue'
        				     }
        				},
        				});
        			}).fail(function(){});
        
	});
	
	/*  sort option  */	
	$(document).on('change', '#sortFirstBy', function(element, event){	
        
		$('#sortSecondBy').val('').trigger('change.select2');
		$('#sortSecondBy').prop('disabled', false);
		$('#sortLastBy').val('').trigger('change.select2');
		$('#sortLastBy').prop('disabled', false);
		$('#bundledGradeLastName').hide();
		 // refresh checkbox
			$('#districtBundledSelect').attr('checked', false); 
			$('#districtSeparateSelect').attr('checked', false); 
			$('#schoolBundledSelect').attr('checked', false); 
			$('#schoolSeparateSelect').attr('checked', false); 
			$('#separateBundledReportSelect').attr('checked', false);
			$('#schoolSeparateSelect').prop('disabled', true);
			$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
			 $('#schoolSeparateSelect').css({"cursor":"text"});
		    
			$('#districtSeparateSelect').prop('disabled', true); 
			$('#districtSeparateSelectDiv').addClass('separateSelectDiv');
			$('#districtSeparateSelect').css({"cursor":"text"});
			
        var selvalue1= $('#sortFirstBy option:selected').val();	      
        var selvalue2= $('#sortSecondBy option:selected').val();
        var selvalue3= $('#sortLastBy option:selected').val();
        var schoolLevel = $('select[data-data-type="school"] option:selected', getFilterElement()).val();	    
        
       	if(selvalue1 == undefined) selvalue1 = 0;
    	if(selvalue2 == undefined) selvalue2 =  0;
    	if(selvalue3 == undefined) selvalue3 = 0;
  
        
        	if(selvalue1!="0"){
        		$('#bundledContentAreaLastName').show();        		
        	}else {
        		
        		$('#bundledContentAreaLastName').hide();
        	}
     
        	// If first sort is student last name, do not display ?Sort second by? 
        	if(selvalue1=="legallastname"){	            	
        		 $("#sortSecondBy").prop('disabled', true);
        		 $("#sortLastBy").prop('disabled', true); 
        		$('#bundledContentAreaLastName').hide();
        		$('#bundledGradeLastName').hide();
        	} else if ($("#sortSecondBy").prop("disabled")=="disabled"){
        		$("#sortSecondBy").prop('disabled', false);
        		$("#sortLastBy").prop('disabled', false);
        	}	            	
         if(schoolLevel==0 || schoolLevel == null){
        	if(selvalue1=="0"){
        		$("#splitby1").html("Selected Option");        		
        		$("#splitby2").html("Selected Option");
        		$($("#splitby1").prev('input')).attr('title','Split district report into separate files by Selected Option');
        		$($("#splitby2").prev('input')).attr('title','Split school report into separate files by Selected Option');
        	}else {
        		if(selvalue1 == "legallastname"){
        			$("#splitby1").html("student last name");
        			$($("#splitby1").prev('input')).attr('title','Split district report into separate files by student last name');
        		}else{
        		  $("#splitby1").html(selvalue1);
        		  $($("#splitby1").prev('input')).attr('title','Split district report into separate files by '+selvalue1);
        		}
        		
        		if(selvalue1!="school"){
        			$("#splitby2").html($("#splitby1").text());
        			$($("#splitby2").prev('input')).attr('title','Split school report into separate files by '+$("#splitby1").text());
        		} else if(selvalue2!="0"){
            			$("#splitby2").html(selvalue2);
            			$($("#splitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue2);
        		} else {
            		$("#splitby2").html("Selected Option");
            		$($("#splitby2").prev('input')).attr('title','Split school report into separate files by Selected Option');
        		}
        	}
        }else{
        	if(selvalue1=="0"){
        		$("#splitby3").html("Selected Option");
        		$($("#splitby3").prev('input')).attr('title','Create separate bundled report for each Selected Option instead of a single PDF report file');
        	}else if (selvalue1 == "legallastname"){
        		$('#separateBundledReportSelect').attr('checked', false);
        		$('#separateBundledReportSelect').prop('disabled', true);
        		$('#separateBundledReportSelectDiv').addClass('separateSelectDiv');
    		    $('#separateBundledReportSelect').css({"cursor":"text"});
    		    
        		$("#splitby3").html("student last name");
        		$($("#splitby3").prev('input')).attr('title','Create separate bundled report for each student last name instead of a single PDF report file');
        	}else if (selvalue1 == "grouping1"){
        		$('#separateBundledReportSelect').attr('checked', false);
        		$('#separateBundledReportSelect').prop('disabled', true);
        		$('#separateBundledReportSelectDiv').addClass('separateSelectDiv');
    		    $('#separateBundledReportSelect').css({"cursor":"text"});
        	}else{
        		$("#splitby3").html(selvalue1);
        		$($("#splitby3").prev('input')).attr('title','Create separate bundled report for each '+selvalue1+' instead of a single PDF report file');
        		$('#separateBundledReportSelect').prop('disabled', false);
        		$('#separateBundledReportSelectDiv').removeClass('separateSelectDiv');
    		    $('#separateBundledReportSelect').css({"cursor":"pointer"});
        	}
        }
        if($("#sortSecondBy").attr("disabled")!="disabled") {
        	
        	$("#sortSecondBy option").each(function(){
        		if($(this).val()!=selvalue3){
        			if ( $("#sortSecondBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
        				$("#sortSecondBy option[value= "+$(this).val()+"]").unwrap();
        			}
        		}
        		
        		if(($(this).val()==selvalue1 && selvalue1!="0") || (selvalue1!="grouping1") && ($(this).val()=="grouping2")){        			
        			$("#sortSecondBy option[value= "+$(this).val()+"]").wrap('<span>');
        		} 
        	});
        }
        
        $("#sortLastBy option").each(function(){
        	if($(this).val()!=selvalue2) {
        		if ( $("#sortLastBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#sortLastBy option[value= "+$(this).val()+"]").unwrap();
    			}
        	}
        	if(($(this).val()==selvalue1 && selvalue1!="0")|| ($(this).val()=="grouping2")){		        		
        		$("#sortLastBy option[value= "+$(this).val()+"]").wrap('<span>');
        	}
        });
        
        var selectSecondBy = $("#sortSecondBy");
        selectSecondBy.val('').trigger('change.select2');
     
});
	
$(document).on('change', '#sortSecondBy', function(element, event){	
	
	$('#sortLastBy').val('').trigger('change.select2');
	$('#sortLastBy').prop('disabled', false);
	//$('#bundledGradeLastName').show();
    // refresh checkbox
	    $('#districtBundledSelect').attr('checked', false); 
	    $('#districtSeparateSelect').attr('checked', false); 
	    // second sort option disabled and add css
	    $('#districtSeparateSelect').prop('disabled', true); 
	    $('#districtSeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSeparateSelect').css({"cursor":"text"});
	    
		$('#schoolBundledSelect').attr('checked', false); 
		$('#schoolSeparateSelect').attr('checked', false); 
		$('#schoolSeparateSelect').prop('disabled', true);
		$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSeparateSelect').css({"cursor":"text"});
	    
		var selvalue1= $('#sortFirstBy option:selected').val();	      
        var selvalue2= $('#sortSecondBy option:selected').val();
        var selvalue3= $('#sortLastBy option:selected').val();
        var schoolLevel = $('select[data-data-type="school"] option:selected', getFilterElement()).val();	
        
       	if(selvalue1 == undefined) selvalue1 = 0;
    	if(selvalue2 == undefined) selvalue2 =  0;
    	if(selvalue3 == undefined) selvalue3 = 0;
    	
        if(selvalue1!="0" && selvalue2 != "0"){    		
    		$('#bundledGradeLastName').show();
    	}else {    		
    		$('#bundledGradeLastName').hide();
    	}
         
        if(selvalue2=="legallastname"){
    		$("#sortLastBy").prop('disabled', true);
    		$('#bundledGradeLastName').hide();
    		//$("#separateBundledReportSelect").attr('disabled',true);    		
    	} else if ($("#sortLastBy").attr("disabled")!="disabled"){
    		$("#sortSecondBy").prop('disabled', false);
    		//$("#separateBundledReportSelect").removeAttr("disabled");
    	}
        if(schoolLevel==0 || schoolLevel == null) {
        	  	if(selvalue1=="school" || selvalue1=="0"){
        		if(selvalue2=="legallastname"){
        			 $("#splitby2").html("student last name");
        			 $($("#splitby2").prev('input')).attr('title','Split school report into separate files by Studemt last name');
        		}else{        			
    			  $("#splitby2").html(selvalue2);
    			  $($("#splitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue2);
        		}
        	}else {        		
        		$("#splitby2").html(selvalue1);
        		$($("#splitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue1);
        	}
        	if(selvalue2=="0"){
        		if(selvalue1!="school" && selvalue1!="0"){	            		 
        			$("#splitby2").html(selvalue1);
        			$($("#splitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue1);
            	} else {
            		$("#splitby2").html("Selected Option");
            		$($("#splitby2").prev('input')).attr('title','Split school report into separate files by Selected Option');
            	}
    		}
        	  
        	
        }
        if($("#sortLastBy").attr("disabled")!="disabled") {	
        	
        	 $("#sortLastBy option").each(function(){
	        	if($(this).val()!=selvalue1 ){
	        		if ( $("#sortLastBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
        				$("#sortLastBy option[value= "+$(this).val()+"]").unwrap();
        			}
	        	}
	        	if(($(this).val()==selvalue2 && selvalue2!="0") || (selvalue2!="grouping1") && ($(this).val()=="grouping2") ) {	
	        		$("#sortLastBy option[value= "+$(this).val()+"]").wrap('<span>');
	        	}else if(selvalue2=="0" && $(this).val()!=selvalue1){
	        		if ( $("#sortLastBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
        				$("#sortLastBy option[value= "+$(this).val()+"]").unwrap();
        			}
	        	}
	        	
	        	
	        });
        }
        
        $("#sortFirstBy option").each(function(){
        	if($(this).val()!=selvalue3){
        		
        		if ( $("#sortFirstBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#sortFirstBy option[value= "+$(this).val()+"]").unwrap();
    			}
        	}
        	if($(this).val()==selvalue2 && selvalue2!="0"){
        		//$(this).parent().parent().hide();
        	} 
        });
        
        var selectLastBy = $("#sortLastBy");
        selectLastBy.val('').trigger('change.select2');
 
});
	
$(document).on('change', '#sortLastBy', function(element, event){	
	
	     var selvalue1= $('#sortFirstBy option:selected').val();	      
         var selvalue2= $('#sortSecondBy option:selected').val();
         var selvalue3= $('#sortLastBy option:selected').val();
         
         if(selvalue1 == undefined) selvalue1 = 0;
         if(selvalue2 == undefined) selvalue2 =  0;
         if(selvalue3 == undefined) selvalue3 = 0;
        	
         $("#sortSecondBy option").each(function(){    
        	if($(this).val()!=selvalue1 && selvalue1!="grouping1" && $(this).val()!="grouping2"){        		
        		if ( $("#sortSecondBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#sortSecondBy option[value= "+$(this).val()+"]").unwrap();
    			}
        	}
        	if($(this).val()==selvalue3 && selvalue3!="0"){
        		//$(this).parent().parent().hide();
        	} 
        });
        
        $("#sortFirstBy option").each(function(){
        	if($(this).val()!=selvalue2){
        		if ( $("#sortFirstBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#sortFirstBy option[value= "+$(this).val()+"]").unwrap();
    			}
        	}
        	if($(this).val()==selvalue3 && selvalue3!="0"){		        		
        		//$(this).parent().parent().hide();
        	}
        });
     
});

$(document).on('click', '#districtBundledSelect', function(element, event){	
	if($('#districtBundledSelect:checked').val()){
		var selvalue1= $('#sortFirstBy option:selected').val();
		if(selvalue1!="legallastname" && selvalue1!="grouping1"){
			$('#districtSeparateSelect').prop('disabled', false);
			$('#districtSeparateSelectDiv').removeClass('separateSelectDiv');
			$('#districtSeparateSelect').css({"cursor":"pointer"});
		}
	}else {
		$('#districtSeparateSelect').prop('disabled', true); 
	    $('#districtSeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSeparateSelect').css({"cursor":"text"});
		
		$('#districtSeparateSelect').prop('checked', false);
	}	
});

$(document).on('click', '#schoolBundledSelect', function(element, event){

	if($('#schoolBundledSelect:checked').val()){
		var selvalue1= $('#sortFirstBy option:selected').val();
		var selvalue2= $('#sortSecondBy option:selected').val();
		if(selvalue1!="legallastname" && selvalue1!="grouping1" && selvalue2!="legallastname" && selvalue2!="grouping1"){
			$('#schoolSeparateSelect').prop('disabled', false);
			$('#schoolSeparateSelectDiv').removeClass('separateSelectDiv');
			 $('#schoolSeparateSelect').css({"cursor":"pointer"});
		}
	}else {
		$('#schoolSeparateSelect').prop('disabled', true);
		$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSeparateSelect').css({"cursor":"text"});
		$('#schoolSeparateSelect').prop('checked', false);
	}
});

$(document).on('click', '#studentBundledPrev', function(element, event){

	clearTimeout(ref);
	ref = 'refreshed';
	$('.allStudentsReportsFile').show();
	$('.studentBundledReportStatus').hide();
	// refresh select option
	$('#studentBundledReportSchool').val('').trigger('change.select2');
	$('#studentBundledReportSchool').val('').trigger('change.select2');
	
	$('#studentBundledReportContentAreas').val('').trigger('change.select2');
	$('#studentBundledReportContentAreas').val('').trigger('change.select2');
	
	$('#studentBundledReportGrades').val('').trigger('change.select2');
	$('#studentBundledReportGrades').val('').trigger('change.select2');
	
	// refresh sort
	$('#sortFirstBy').val('').trigger('change.select2');
	$('#sortSecondBy').val('').trigger('change.select2');
	$('#sortLastBy').val('').trigger('change.select2');
	 
	// refresh checkbox
	$('#districtBundledSelect').attr('checked', false); 
	$('#districtSeparateSelect').attr('checked', false); 
	$('#schoolBundledSelect').attr('checked', false); 
	$('#schoolSeparateSelect').attr('checked', false); 
	$('#separateBundledReportSelect').attr('checked', false);
	
	if($('select[data-data-type="school"]', getFilterElement()).val() == 0 || $('select[data-data-type="school"]', getFilterElement()).val() == null ){
	  $('select[data-data-type="district"]', getFilterElement()).trigger("change");
	}else{
	  $('select[data-data-type="school"]', getFilterElement()).trigger("change");
	}
	
});

$(document).on('change', '#student-bundled_districtSelect, #student-summary-bundled_districtSelect', function(element, event){
	clearTimeout(ref);
	ref = 'refreshed';	
	if($('#orgLevel').val()!=70){
		selectChange('district', $(this));
	}
	
});

$(document).on('change', '#student-bundled_schoolSelect, #student-summary-bundled_schoolSelect', function(element, event){
	clearTimeout(ref);
	ref = 'refreshed';	
});

$(document).on('change', '#studentBundledReportSchool', function(element, event){
	checkRef = this;
	$('#studentBundledReportContentAreas').empty();
	$('#studentBundledReportContentAreas').val('').trigger('change.select2');
	$('#studentBundledReportGrades').empty();
	$('#studentBundledReportGrades').val('').trigger('change.select2');		
	//subject 
	loadStudentBundledReportContentAreas();	
	
});

$(document).on('change', '#studentSummaryBundledReportSchool', function(element, event){
	checkRef = this;
	$('#studentSummaryBundledReportGrades').empty();
	$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');		
	//subject 
	studentSummaryBundledReportGrade();
});


/*  sort option  */	
$(document).on('change', '#summarySortFirstBy', function(element, event){
    
	$('#summarySortSecondBy').val('').trigger('change.select2');
	$('#summarySortSecondBy').prop('disabled', false);
	$('#summarySortLastBy').val('').trigger('change.select2');
	$('#summarySortLastBy').prop('disabled', false);
	
	 // refresh checkbox
		$('#districtSummaryBundledSelect').attr('checked', false); 
		$('#districtSummarySeparateSelect').attr('checked', false); 
		$('#schoolSummaryBundledSelect').attr('checked', false); 
		$('#schoolSummarySeparateSelect').attr('checked', false); 
		$('#summarySeparateBundledReportSelect').attr('checked', false);
		$('#schoolSummarySeparateSelect').prop('disabled', true);
		$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
	    
		$('#districtSummarySeparateSelect').prop('disabled', true); 
		$('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
		$('#districtSummarySeparateSelect').css({"cursor":"text"});
		
    var selvalue1= $('#summarySortFirstBy option:selected').val();	      
    var selvalue2= $('#summarySortSecondBy option:selected').val();
    var selvalue3= $('#summarySortLastBy option:selected').val();
    var schoolLevel =  $('select[data-data-type="school"]', getFilterElement()).val();	    
    
    $('#bundledSummaryContentAreaLastName').hide();
    	if(selvalue1!="0"){
    		$('#bundledSummaryContentAreaLastName').show();        		
    	}
    	else {
    		
    		$('#bundledSummaryGradeLastName').hide();
    	}
 
    	// If first sort is student last name, do not display ?Sort second by? 
    	if(selvalue1=="legallastname"){	            	
    		 $("#summarySortSecondBy").prop('disabled', true);
    		 $("#summarySortLastBy").prop('disabled', true); 
    		$('#bundledSummaryContentAreaLastName').hide();
    		$('#bundledSummaryGradeLastName').hide();
    	} else if ($("#sortSecondBy").attr("disabled")=="disabled"){
    		$("#summarySortSecondBy").prop('disabled', false);
    		$("#summarySortLastBy").prop('disabled', false);
    	}	            	
     if(schoolLevel==0 || schoolLevel == null){
    	if(selvalue1=="0"){
    		$("#summarySplitby1").html("Selected Option");
    		$("#summarySplitby2").html("Selected Option");
    		$($("#summarySplitby1").prev('input')).attr('title','Split district report into separate files by Selected Option');
    		$($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by Selected Option');
    	}else {
    		if(selvalue1 == "legallastname"){
    			$("#summarySplitby1").html("student last name");
    			$($("#summarySplitby1").prev('input')).attr('title','Split district report into separate files by student last name');
    		}else{
    		  $("#summarySplitby1").html(selvalue1);
    		  $($("#summarySplitby1").prev('input')).attr('title','Split district report into separate files by '+selvalue1);
    		}
    		
    		if(selvalue1!="school"){
    			$("#summarySplitby2").html($("#summarySplitby1").text());
    			 $($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by '+$("#summarySplitby1").text());
    		} else if(selvalue2!="0" ){
        		$("#summarySplitby2").html(selvalue2);
        		 $($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue2);
    		} else {
        		$("#summarySplitby2").html("Selected Option");
        		$($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by Selected Option');
    		}
    	}
    }else{
    	if(selvalue1=="0"){
    		$("#summarySplitby3").html("Selected Option");
    		 $($("#summarySplitby3").prev('input')).attr('title','Create separate bundled report for each Selected Option instead of a single PDF report file');
    	}else if (selvalue1 == "legallastname"){
    		$('#summarySeparateBundledReportSelect').attr('checked', false);
    		$('#summarySeparateBundledReportSelect').prop('disabled', true);
    		$('#summarySeparateBundledReportSelectDiv').addClass('separateSelectDiv');
		    $('#summarySeparateBundledReportSelect').css({"cursor":"text"});
		    
    		$("#summarySplitby3").html("student last name");
    		 $($("#summarySplitby3").prev('input')).attr('title','Create separate bundled report for each student last name instead of a single PDF report file');
    	}else if (selvalue1 == "grouping1"){
    		$('#summarySeparateBundledReportSelect').attr('checked', false);
    		$('#summarySeparateBundledReportSelect').prop('disabled', true);
    		$('#summarySeparateBundledReportSelectDiv').addClass('separateSelectDiv');
		    $('#summarySeparateBundledReportSelect').css({"cursor":"text"});
    	}else{
    		$("#summarySplitby3").html(selvalue1);
    		 $($("#summarySplitby3").prev('input')).attr('title','Create separate bundled report for each '+selvalue1+' instead of a single PDF report file');
    		$('#summarySeparateBundledReportSelect').prop('disabled', false);
    		$('#summarySeparateBundledReportSelectDiv').removeClass('separateSelectDiv');
		    $('#summarySeparateBundledReportSelect').css({"cursor":"pointer"});
    	}
    }
    if($("#summarySortSecondBy").attr("disabled")!="disabled") {    	
    	 $("#summarySortSecondBy option").each(function(){
    		if($(this).val()!=selvalue3){
    			if ( $("#summarySortSecondBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#summarySortSecondBy option[value= "+$(this).val()+"]").unwrap();
    			}    			
    		}
    		if(($(this).val()==selvalue1 && selvalue1!="0") || (selvalue1!="grouping1") && ($(this).val()=="grouping2")){        			
    			$("#summarySortSecondBy option[value= "+$(this).val()+"]").wrap('<span>');
    		} 
    	});
    }
    
    $("#summarySortLastBy option").each(function(){
    	if($(this).val()!=selvalue2) {
    		if ( $("#summarySortLastBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
				$("#summarySortLastBy option[value= "+$(this).val()+"]").unwrap();
			}
    	}
    	if(($(this).val()==selvalue1 && selvalue1!="0")|| ($(this).val()=="grouping2")){		        		
    		$("#summarySortLastBy option[value= "+$(this).val()+"]").wrap('<span>');
    	}
    });
 
});

$(document).on('change', '#summarySortSecondBy', function(element, event){

$('#summarySortLastBy').val('').trigger('change.select2');
$('#summarySortLastBy').prop('disabled', false);
//$('#bundledGradeLastName').show();
// refresh checkbox
    $('#districtSummaryBundledSelect').attr('checked', false); 
    $('#districtSummarySeparateSelect').attr('checked', false); 
    // second sort option disabled and add css
    $('#districtSummarySeparateSelect').prop('disabled', true); 
    $('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
    $('#districtSummarySeparateSelect').css({"cursor":"text"});
    
	$('#schoolSummaryBundledSelect').attr('checked', false); 
	$('#schoolSummarySeparateSelect').attr('checked', false); 
	$('#schoolSummarySeparateSelect').prop('disabled', true);
	$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
	 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
    
	var selvalue1= $('#summarySortFirstBy option:selected').val();	      
    var selvalue2= $('#summarySortSecondBy option:selected').val();
    var selvalue3= $('#summarySortLastBy option:selected').val();
    var schoolLevel =  $('select[data-data-type="school"]', getFilterElement()).val();	
    
    if(selvalue1!="0" && selvalue2 != "0"){    		
		$('#bundledSummaryGradeLastName').show();
	}else {    		
		$('#bundledSummaryGradeLastName').hide();
	}
     
    if(selvalue2=="legallastname"){
		$("#summarySortLastBy").prop('disabled', true);
		$('#bundledSummaryGradeLastName').hide();
		//$("#separateBundledReportSelect").attr('disabled',true);    		
	} else if ($("#summarySortLastBy").attr("disabled")!="disabled"){
		$("#summarySortSecondBy").prop('disabled', false);
		//$("#separateBundledReportSelect").removeAttr("disabled");
	}
    if(schoolLevel==0 || schoolLevel == null) {
    	    	if(selvalue1=="school" || selvalue1=="0"){
    		if(selvalue2=="legallastname"){
    			 $("#summarySplitby2").html("student last name");
    			 $($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by student last name');
    		}else{        			
			  $("#summarySplitby2").html(selvalue2);
			  $($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue2);
    		}
    	}else {        		
    		$("#summarySplitby2").html(selvalue1);
    		 $($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue1);
    	}
    	if(selvalue2=="0"){
    		if(selvalue1!="school" && selvalue1!="0"){	            		 
    			$("#summarySplitby2").html(selvalue1);
    			$($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by '+selvalue1);
        	} else {
        		$("#summarySplitby2").html("Selected Option");
        		$($("#summarySplitby2").prev('input')).attr('title','Split school report into separate files by Selected Option');
        	}
		}
    	  
    	
    }
     
     if($("#summarySortLastBy").attr("disabled")!="disabled") {	
     	
    	 $("#summarySortLastBy option").each(function(){
        	if($(this).val()!=selvalue1 ){        		
        		if ( $("#summarySortLastBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#summarySortLastBy option[value= "+$(this).val()+"]").unwrap();
    			}
        	}
        	if(($(this).val()==selvalue2 && selvalue2!="0") || (selvalue2!="grouping1") && ($(this).val()=="grouping2") ) {	
        		$("#summarySortLastBy option[value= "+$(this).val()+"]").wrap('<span>');
        	}else if(selvalue2=="0" && $(this).val()!=selvalue1){
        		if ( $("#summarySortLastBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
    				$("#summarySortLastBy option[value= "+$(this).val()+"]").unwrap();
    			}
        	}
        	
        	
        });
    }
    
    $("#summarySortFirstBy option").each(function(){
    	if($(this).val()!=selvalue3){     		
     		if ( $("#summarySortFirstBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
				$("#summarySortFirstBy option[value= "+$(this).val()+"]").unwrap();
			}
    	}
    	if($(this).val()==selvalue2 && selvalue2!="0"){
    		//$(this).parent().parent().hide();
    	} 
    });

});

$(document).on('change', '#summarySortLastBy', function(element, event){
     var selvalue1= $('#summarySortFirstBy option:selected').val();	      
     var selvalue2= $('#summarySortSecondBy option:selected').val();
     var selvalue3= $('#summarySortLastBy option:selected').val();
     
    $("#summarySortSecondBy option").each(function(){ 
    	if($(this).val()!=selvalue1 && selvalue1!="grouping1" && $(this).val()!="grouping2"){ 
    		if ( $("#summarySortSecondBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
				$("#summarySortSecondBy option[value= "+$(this).val()+"]").unwrap();
			}
    	}
    	if($(this).val()==selvalue3 && selvalue3!="0"){
    		//$(this).parent().parent().hide();
    	} 
    });
   
    $("#summarySortFirstBy option").each(function(){ 
    	if($(this).val()!=selvalue2){
    		if ( $("#summarySortFirstBy option[value= "+$(this).val()+"]").parent().is( "span" ) ){
				$("#summarySortFirstBy option[value= "+$(this).val()+"]").unwrap();
			}    		
    	}
    	if($(this).val()==selvalue3 && selvalue3!="0"){		        		
    		//$(this).parent().parent().hide();
    	}
    });
 
});

$(document).on('change', '#studentSummaryBundledPrev', function(element, event){
	clearTimeout(ref);
	ref = 'refreshed';
	$('.allStudentsReportsFile').show();
	$('.studentSummaryBundledReportStatus').hide();
	// refresh select option
	$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');
	$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');

	$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
	$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');

	// refresh sort
	$('#summarySortFirstBy').val('').trigger('change.select2');
	$('#summarySortSecondBy').val('').trigger('change.select2');
	$('#summarySortLastBy').val('').trigger('change.select2');
	 
	// refresh checkbox
	$('#districtSummaryBundledSelect').attr('checked', false); 
	$('#districtSummarySeparateSelect').attr('checked', false); 
	$('#schoolSummaryBundledSelect').attr('checked', false); 
	$('#schoolSummarySeparateSelect').attr('checked', false); 
	$('#separateSummaryBundledReportSelect').attr('checked', false);

	if($('select[data-data-type="school"]', getFilterElement()).val() == 0 || $('select[data-data-type="school"]', getFilterElement()).val() == null){
		$('select[data-data-type="district"]', getFilterElement()).trigger("change");
	}else{
		$('select[data-data-type="school"]', getFilterElement()).trigger("change");	
	}

  });

$(document).on('click', '#submitSummaryBundledReport', function(element, event){
	
	var selectSchooltext=[];
	var selectSchoolNames=[];
	var schoolId = $('select[data-data-type="school"] option:selected', getFilterElement()).val();
	
	$("#studentSummaryBundledReportSchool option:selected").each(function(){
      	 var option = $(this);
       	 selectSchooltext.push(option.val());
         selectSchoolNames.push(option.text());	   	 
   	});
    
  	if(selectSchoolNames != '' && totalSchoolLength == selectSchooltext.length)
  		{
  			selectSchoolNames = [];
  			selectSchoolNames.push("All");
  		}
    
    var selectGradetext=[];
    var selectGradeNames=[];
    $("#studentSummaryBundledReportGrades option:selected").each(function(){
    	 var option = $(this);
    	 selectGradetext.push(option.val());
    	 selectGradeNames.push(option.text());
    });
  if(totalGradesLength == selectGradetext.length){
	  selectGradeNames=[];
	  selectGradeNames.push("All");
  }
  
if((schoolId == '0' || schoolId == null) && selectSchooltext == '')
	   {
	   $('#emptyStudentSummarybundlereportform').html("<span>Select School</span>");
		setTimeout(function(){ $("#emptyStudentSummarybundlereportform").html("&nbsp;"); },4000);
		return;
	   }
if(selectGradetext =='')
	   {
	   $('#emptyStudentSummaryBundledReportGrades').html("<span>Select Grade</span>");
	   setTimeout(function(){ $("#emptyStudentSummaryBundledReportGrades").html("&nbsp;"); },4000);
	   return;
	   }
    
//sort option
	var selectFirstSort= $('#summarySortFirstBy option:selected').val(); 
	var selectSecondSort= $('#summarySortSecondBy option:selected').val();
	var selectLastSort= $('#summarySortLastBy option:selected').val();
	
	if(selectFirstSort == undefined) selectFirstSort =  0;
	if(selectSecondSort == undefined) selectSecondSort =  0;
	if(selectLastSort == undefined) selectLastSort = 0;


	if(selectFirstSort == "0")
		{
		   $('#summaryEmptySortFirstBy').html("<span>Select First Sort</span>");
		   setTimeout(function(){ $("#summaryEmptySortFirstBy").html("&nbsp;"); },4000);
		   return;
		}
	if(selectSecondSort =="0" && selectFirstSort!="legallastname")
		{
		  $('#summaryEmptySortSecondBy').html("<span>Select Second Sort</span>");
		   setTimeout(function(){ $("#summaryEmptySortSecondBy").html("&nbsp;"); },4000);
		   return;
		}
	if(selectLastSort =="0" && (selectSecondSort!="legallastname" && selectFirstSort!="legallastname"))
		{
		  $('#summaryEmptySortLastBy').html("<span>Select Last Sort</span>");
		  setTimeout(function(){ $("#summaryEmptySortLastBy").html("&nbsp;"); },4000);
		  return;
		 }
	
	if(selectLastSort == undefined) selectLastSort = selectLastSort = 0;
    
   
//district combobox option select
	var  districtBundledSelect =  $('#districtSummaryBundledSelect').prop('checked');
	var districtSeparateSelect = $('#districtSummarySeparateSelect').prop('checked');
	var schoolBundledSelect = $('#schoolSummaryBundledSelect').prop('checked');
	var schoolSeparateSelect = $('#schoolSummarySeparateSelect').prop('checked');
	var separateBundledReportSelect = $('#summarySeparateBundledReportSelect').prop('checked');
	
	var organizationId  = '';
	var districtId = $('select[data-data-type="district"] option:selected', getFilterElement()).val();
	
	var assessmentProgId = $('#userDefaultAssessmentProgram option[selected="selected"]').val();
	 
	if(schoolId == 0 || schoolId == null){
		 organizationId = districtId;
	} else {
		 organizationId = schoolId;
		 
	}   
	
	if((schoolId == 0 || schoolId == null) && !districtBundledSelect && !schoolBundledSelect){
		
		 $('#studentSummaryBundledSelectError').html("<span>Select options for District/School bundled.</span>");
		  setTimeout(function(){ $("#studentSummaryBundledSelectError").html("&nbsp;"); },4000);
		  return;
	 } 	
	 var separateFile = false;
	 if(districtSeparateSelect || separateBundledReportSelect || schoolSeparateSelect){
		 separateFile = true;
	 }
	 if(schoolBundledSelect && selectSchooltext.length > 1){
		 separateFile = true; 
	 }
	 if(schoolBundledSelect && districtBundledSelect){
		 separateFile = true;
	 }
	 $('#submitBundledReport').prop('disabled', true);
	//showing popup        
    	$.ajax({
    		url: 'getEstimatedSummaryFileSizeForSelectedFilters.htm',
    		dataType: 'json',
    		data: {
    			organizationId : organizationId,
    			assessmentProgramId : assessmentProgId,
    			schoolIds : selectSchooltext,		
    			gradeIds : selectGradetext,
    			separateFile : separateFile
    			},
    			type: 'GET'
    		}).done(function(data){
    				data.totalSize = data.totalSize/1024;//Size will be converted from kb to mb
    				if(data.totalSize > 100){             
    		             $('#summaryBundledReportSize').empty().append('<p>Note - this file will be very large. A rough estimate is '+ Math.round(parseFloat(data.totalSize)) +' MB</p><p>');
    		            }else{
    		             $('#summaryBundledReportSize').empty();
    		            }
    		            if(data.inProgress){
    		             $('#summaryBundledReportStatus').empty().append('<p>Already one more request is in progress for same organization.</p>');
    		            }else{
    		             $('#summaryBundledReportStatus').empty();
    		            }
    		            
    				var byOrganization = false;
    				if(schoolId != 0 || districtBundledSelect){
    					byOrganization = true;
    				}
    				
    				var separateFile = false;
    				if(districtSeparateSelect || separateBundledReportSelect){
    					separateFile = true;
    				}
    				
    				if(byOrganization && schoolBundledSelect){
    					$('#summaryPopupText').empty().append('district and school(s)');
    				}else if(schoolId != 0){
    					$('#summaryPopupText').empty().append('school');
    				}else if(schoolId == 0){
    					$('#summaryPopupText').empty().append('district');
    				}        				
    				
    				$('#submitSummaryBundledReport').prop('disabled', false);
    				// popup         				
    				var dialog = $('#submitSummaryBundledReportDialog').dialog({
    					resizable: false,
    					width: 585,
    					modal: true,
    					autoOpen: true,
    					dialogClass: '_bcg',
    					title: 'Create new bundled report(s)',
    					create: function(event, ui){
    						$('#submitSummaryBundledReport').prop('disabled', false);
    						var widget = $(this).dialog("widget");    						
    						$(".ui-dialog-titlebar-close span", widget).removeClass('ui-button-icon-primary').removeClass('ui-button-text');
    					},
    				buttons: {

    					'Cancel': {            
    				            'click': function() {
    				            	$('#submitSummaryBundledReport').prop('disabled', false);
    	    						$(this).dialog('close');
    				            },
    				            'text' : 'Cancel',         
    				            'class': 'btn_blue'
    				     },
    				        

     					'Continue': {            
     				            'click': function() {

     	    						$.ajax({
     	    			        		url: 'requestForDynamicStudentSummaryBundle.htm',
     	    			        		dataType: 'json',
     	    			        		data: {
     	    			        			organizationId : organizationId,
     	    			        			assessmentProgramId : assessmentProgId,
     	    			        			schoolIds : selectSchooltext,		
     	    			        			gradeIds : selectGradetext,
     	    			        			schoolNames : selectSchoolNames,
     	    			        			gradeNames : selectGradeNames,
     	    			        			sort1 : selectFirstSort,
     	    			        			sort2 : selectSecondSort,
     	    			        			sort3 : selectLastSort,
     	    			        			byOrganization : byOrganization,
     	    			        			separateFile : separateFile,
     	    			        			bySchool : schoolBundledSelect,
     	    			        			separateFileForSchool : schoolSeparateSelect        			        			
     	    			        			},
     	    			        			type: 'POST'
     	    			        			}).done(function(data){
     	    			        				$('#submitSummaryBundledReport').prop('disabled', false);
     	    			        				clearTimeout(ref);
     	    			        				loadSummaryPdfInformation(assessmentProgId, organizationId);
     	    			        				// scrollTop       			        					        				
     	    			        				$(".allStudents").animate({ scrollTop: 0}, "fast");
     	    			        				
     	    			        			});
     	    						
     	    						$('#bundledSummaryContentAreaLastName').hide();
     	    						$('#bundledSummaryGradeLastName').hide();
     	    						
     	    						// close 
     	    					$(this).dialog('close');
     	    					// refresh select option
     	        					$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');

     	        					$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
     	        					
     	        					if(totalSchoolLength !=1){
     	        			             $('#studentSummaryBundledReportSchool').val('').trigger('change.select2');
     	        			          } 
     	        			        if(totalGradesLength !=1 )
     	        			          {
     	        			             $('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
     	        			          }
     	        					
     	        					
     	        					// refresh sort
     	        					$('#summarySortFirstBy').val('').trigger('change.select2');
     	        					$('#summarySortFirstBy').prop('disabled', false);
     	        					$('#summarySortSecondBy').val('').trigger('change.select2');
     	        					$('#summarySortSecondBy').prop('disabled', false);
     	        					$('#summarySortLastBy').val('').trigger('change.select2');
     	        					$('#summarySortLastBy').prop('disabled', false);
     	        					 
     	        					// refresh checkbox
     	        					$('#districtSummaryBundledSelect').attr('checked', false); 
     	        					$('#districtSummarySeparateSelect').attr('checked', false); 
     	        					$('#schoolSummaryBundledSelect').attr('checked', false); 
     	        					$('#schoolSummarySeparateSelect').attr('checked', false); 
     	        					$('#summarySeparateBundledReportSelect').attr('checked', false);
     	        					$('#submitSummaryBundledReport').prop('disabled', false);
     	    					
     	    					
     				            },
     				            'text' : 'Continue',         
     				            'class': 'btn_blue'
     				     }
    					
    				},
    				});
    			}).fail(function(){});
    
   });

$(document).on('click', '#districtSummaryBundledSelect', function(element, event){
	if($('#districtSummaryBundledSelect:checked').val()){
		var selvalue1= $('#summarySortFirstBy option:selected').val();
		if(selvalue1!="legallastname" && selvalue1!="grouping1"){
			$('#districtSummarySeparateSelect').prop('disabled', false);
			$('#districtSummarySeparateSelectDiv').removeClass('separateSelectDiv');
			$('#districtSummarySeparateSelect').css({"cursor":"pointer"});
		}
	}else {
		$('#districtSummarySeparateSelect').prop('disabled', true); 
	    $('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
	    $('#districtSummarySeparateSelect').css({"cursor":"text"});
		
		$('#districtSummarySeparateSelect').prop('checked', false);
	}	
});

$(document).on('click', '#schoolSummaryBundledSelect', function(element, event){
	if($('#schoolSummaryBundledSelect:checked').val()){
		var selvalue1= $('#summarySortFirstBy option:selected').val();
		var selvalue2= $('#summarySortSecondBy option:selected').val();
		if(selvalue1!="legallastname" && selvalue1!="grouping1" && selvalue2!="legallastname" && selvalue2!="grouping1"){
			$('#schoolSummarySeparateSelect').prop('disabled', false);
			$('#schoolSummarySeparateSelectDiv').removeClass('separateSelectDiv');
			 $('#schoolSummarySeparateSelect').css({"cursor":"pointer"});
		}
	}else {
		$('#schoolSummarySeparateSelect').prop('disabled', true);
		$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
		 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
		$('#schoolSummarySeparateSelect').prop('checked', false);
	}
});

$(document).on('click', '#studentSummaryBundledPrev', function(element, event){
	clearTimeout(ref);
	ref = 'refreshed';
	$('.allStudentsReportsFile').show();
	$('.studentSummaryBundledReportStatus').hide();
	// refresh select option
	$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');
	$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');

	$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');
	$('#studentSummaryBundledReportGrades').val('').trigger('change.select2');

	// refresh sort
	$('#summarySortFirstBy').val('').trigger('change.select2');
	$('#summarySortSecondBy').val('').trigger('change.select2');
	$('#summarySortLastBy').val('').trigger('change.select2');
	 
	// refresh checkbox
	$('#districtSummaryBundledSelect').attr('checked', false); 
	$('#districtSummarySeparateSelect').attr('checked', false); 
	$('#schoolSummaryBundledSelect').attr('checked', false); 
	$('#schoolSummarySeparateSelect').attr('checked', false); 
	$('#separateSummaryBundledReportSelect').attr('checked', false);

	if($('#schoolSelect').val() == 0){
	  $('#districtSelect').trigger("change");
	}else{
	  $('#schoolSelect').trigger("change");	
	}

	});


});

function loadSummaryPdfInformation(assessmentProgramId, organizationId){
	$('#studentReportInclude').hide();
	$('#studentBundledCreatedDate').hide();
	$('#studentBundledSortedBy').hide();
	var schoolSelect = $('select[data-data-type="school"]', getFilterElement()).val();
	if(schoolSelect != 0 && schoolSelect!=null){
		$('#gridSchoolName').hide();
	}
	$.ajax({
		url: 'getDynmaicBundleRequestForOrganization.htm',
		dataType: 'json',
		type: 'GET',
		data: {
			organizationId : organizationId,
			assessmentProgramId : assessmentProgramId,
			reportYear : $('select[data-data-type="reportYear"]', getFilterElement()).val(),
			reportCode : 'STUDENT_SUMMARY_BUNDLED'    			        			
			} 
	}).done(function(data){
				if(data.length > 0){
					$('#studentReportInclude').show();
					$('#studentBundledCreatedDate').show();
					$('#studentBundledSortedBy').show();
					
				   $('#studentReportSchool').empty().append('Schools: ').append(data[0].schoolNames);
				   $('#studentReportSubject').empty().append('Subjects: ').append(data[0].subjectNames);
				   $('#studentReportGrades').empty().append('Grades: ').append(data[0].gradeNames);
				
				
				   $('#submittedUser').empty().append(data[0].submittedUser);
				
				   var date = new Date(data[0].createdDate);
			       var opts = $.extend({}, $.jgrid.formatter.date, opts);
			        
			       $('#submittedDate').empty().append($.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts));
			       $('#studentReportSort').empty().append(data[0].sortString);
					
				 
                var studentBundledReportStatusLists = $('#studentSummaryBundledReportStatusLists');
				studentBundledReportStatusLists.empty();
				
				$.each(data, function(i, organizationBundleReport) {
					
					 date = new Date(organizationBundleReport.createdDate);
   			         opts = $.extend({}, $.jgrid.formatter.date, opts);
					var datas="<tr>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.submittedUser+"</td>";
					datas=datas+"<td style='text-align: center;'>"+ $.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.statusString+"</td>";
					if(schoolSelect == 0 || schoolSelect == null){
					  datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.schoolNames == null ? '' :organizationBundleReport.schoolNames)+"</td>";
					}
					//datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.subjectNames == null ? '' :organizationBundleReport.subjectNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.gradeNames == null ? '' :organizationBundleReport.gradeNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.sortString+"</td>";
					datas=datas+"</tr>";							
					studentBundledReportStatusLists.append(datas);
				});                    
				} else{
					$('#studentReportInclude').hide();
					$('#studentBundledCreatedDate').hide();
					$('#studentBundledSortedBy').hide();
				}
				if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){loadSummaryPdfInformation(assessmentProgramId,organizationId);}, 10000);
				}
		   });	
}

function studentSummaryBundledReportGrade(){	
	// Bundled Subject change		
	districtOrgId  = $('select[data-data-type="district"]', getFilterElement()).val();	
	var studentBundledReportSchool = $('#studentSummaryBundledReportSchool option:selected').val();
	var studentBundledReportGrades = $('#studentSummaryBundledReportGrades');
	
	// school selection
	 var selectSchooltext=[];
	 $("#studentSummaryBundledReportSchool option:selected" ).each(function(){
    		 selectSchooltext.push($(this).val());
     });	

    var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
		var schoolId = '';
		if(schoolLevel != 0 && schoolLevel !=null)
		{
			selectSchooltext.push($('select[data-data-type="school"]', getFilterElement()).val());
		}
		
	// Grades getGradesForReporting.htm
	if(studentBundledReportSchool != 0 && selectSchooltext != ''){
	$.ajax({
		url: 'getGradesForDynamicStudentSummaryBundledReport.htm',
		dataType: 'json',
		data: {
			subjectIds: $('#studentBundledReportContentAreas', getReportContentElement()).val(),
			reportType: reportsConfig.getReportType(),
			districtId: districtOrgId,
			schoolIds: selectSchooltext,			
			assessmentProgIds: $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
			assessmentProgCode: reportsConfig.getAssessmentProgramCode()
		},
		type: 'GET'
	}).done(function(grades){
			totalGradesLength=grades.length;
			studentBundledReportGrades.empty();
			$.each(grades, function(i, grade) {
				if(totalGradesLength != 1){
					studentBundledReportGrades.append($('<option></option>').attr("value", grade.id).text(grade.name));
				}else {
					studentBundledReportGrades.append($('<option selected="select"></option>').attr("value", grade.id).text(grade.name));
				}
			});	
			studentBundledReportGrades.val('').trigger('change.select2');
			if(totalGradesLength != 1){
			studentBundledReportGrades.val('').trigger('change.select2');
			studentBundledReportGrades.select2({placeholder:'Select'}); 	
			}
		}).fail(function(){});		
	
	}
			
	studentBundledReportGrades.val('').trigger('change.select2');
	
}

function selectChange(type, element){
	
	var $element = $(element);	

	if(element.val() === '0'){			
		var index = $element.data('filter-index');
		resetFiltersPast(index);
		disableFiltersPast(index);
		$('#' + type + 'Text').text('');
		if(type == 'reportYear'){
			$('#textLine').css({"border-bottom": "none"});
		}
		enableNextFilterPast(index-1);
		$('#reportContent').empty();
		
		$('#resetButton', getFilterElement()).hide();
		
		}
	// this "if" is part of the hack above mentioned in the "close" event of the multiselect
	else
	  {
		if (!$element.data('open')){
		$('#reportContent').empty();
		if (reportsConfig.getFiltersAvailable()[type]){ // check if that filter type is present, just as a safety measure
			var id = $element.val();
			var index = $element.data('filter-index');
			resetFiltersPast(index);
			disableFiltersPast(index);
			if (typeof (id) === 'object'){ // we're dealing with an array, so let's remove the silly default options
				if (id != null){
					var defaultOptions = ['', '0'];
					for (var x = 0; x < defaultOptions.length; x++){
						var indexOfDefaultOption = $.inArray(defaultOptions[x], id);
						if (indexOfDefaultOption > -1){
							id.splice(indexOfDefaultOption, 1);
						}
					}
					if (id.length === 0){
						id = '';
					}
				} else {
					id = '';
				}
			}
			if (id !== '' && id != '0'){
				var text = typeof (id) == 'object' && id.length > 1 ? id.length + ' Selected' : $('option:selected[value!="0"]', $element).text();
				$('#' + type + 'Text').text(text);
				enableNextFilterPast(index);
			}
			else if(id==-1 && (reportsConfig.getReportType()=='general_student_all' || reportsConfig.getReportType()=='alternate_student_all') || reportsConfig.getReportType()== 'kelpa_student_all' || reportsConfig.getReportType()=='alternate_student_summary_all' || reportsConfig.getReportType()=='alternate_school_summary_all')
			{
				$('#' + type + 'Text').text($('option:selected', $element).text());
				enableNextFilterPast(index);
			}
			else {
				$('#' + type + 'Text').text('');
				enableNextFilterPast(index - 1); // "re-enable" this filter, mostly make sure it's the right visual state (enabled versus complete)
			}
			
			// WTF is this for?
			// It's messing up order in which filters are enabled on certain reports (writing report, specifically tested)
			if($.inArray(reportsConfig.getReportType(), ['alternate_monitoring_summary', 'alternate_blueprint_coverage', 'alternate_school_summary','alternate_yearend_district_summary','general_district_summary','alternate_school_summary_all']) === -1 &&
					type == 'district' && (reportsConfig.getAssessmentProgramCode() == 'DLM' || reportsConfig.getAssessmentProgramCode() == 'KAP' || reportsConfig.getAssessmentProgramCode() == 'CPASS' || reportsConfig.getAssessmentProgramCode() == 'KELPA2') && ($('#orgLevel').val() != 'null' && $('#orgLevel').val() < 70)){
				enableNextFilterPast(index+1);
			}
		}
	}
  }
}

function studentBundledReportGrade(){ 
	
	// Bundled Subject change		
	districtOrgId  = $('select[data-data-type="district"]', getFilterElement()).val(); 
	var studentBundledReportSchool = $('#studentBundledReportSchool option:selected').val();
	var studentBundledReportContentAreas = $('#studentBundledReportContentAreas option:selected').val();
	var studentBundledReportGrades = $('#studentBundledReportGrades');
	
	// school selection
	 var selectSchooltext=[];
	 
	 var selectSchooltext=[];
	 $("#studentBundledReportSchool option:selected" ).each(function(){
    		 selectSchooltext.push($(this).val());
     });	
	    
     var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
		var schoolId = '';
		if(schoolLevel != 0 && schoolLevel !=null)
		{
			selectSchooltext.push($('select[data-data-type="school"]', getFilterElement()).val());
		}
	
		//subject selection
		var selectSubjecttext = $( "#studentBundledReportContentAreas" ).val();
	
	// Grades getGradesForReporting.htm
	if(studentBundledReportSchool != 0 && studentBundledReportContentAreas != 0 && selectSchooltext != '' && selectSubjecttext != null){
	$.ajax({
		url: 'getGradesForBundledReporting.htm',
		dataType: 'json',
		data: {			
			reportType :  reportsConfig.getReportType(),
			districtId : districtOrgId,
			schoolIds : selectSchooltext,
			subjectIds : selectSubjecttext,	
			assessmentProgIds : $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
			assessmentProgCode : reportsConfig.getAssessmentProgramCode(),
			reportCode : reportsConfig.getReportCode()
		},
		type: 'GET'
		}).done(function(grades){
			totalGradesLength=grades.length;
			studentBundledReportGrades.empty();
			//studentBundledReportGrades.append($('<option>Select</option>').attr("value", 0));
			$.each(grades, function(i, grade) {
				if(totalGradesLength != 1){
					studentBundledReportGrades.append($('<option></option>').attr("value", grade.id).text(grade.name));
				}else {
					studentBundledReportGrades.append($('<option selected="select"></option>').attr("value", grade.id).text(grade.name));
				}
			});	
			studentBundledReportGrades.val('').trigger('change.select2');
			if(totalGradesLength != 1){
			studentBundledReportGrades.val('').trigger('change.select2');
			studentBundledReportGrades.select2({placeholder:'Select'}); 	
			}
		}).fail(function(){});		
	
	}
			
	studentBundledReportGrades.val('').trigger('change.select2');
	

}
function loadBundledReportContent(data){
			
			$("#studentBundledReportContentAreas, #studentBundledReportGrades, #studentBundledReportSchool").select2({
		 		  placeholder:'Select',
				  multiple: true,
				  allowClear : true
			});
	
			
			$('#sortFirstBy, #sortSecondBy, #sortLastBy').select2({
				placeholder:'Select',
				multiple: false,
				allowClear : true
			});
			
	
			if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){$('select[data-data-type="district"]', getFilterElement()).trigger("change");}, 5000);
			}
			
			$('#districtSeparateSelect').prop('disabled', true);  
			$('#districtSeparateSelectDiv').addClass('separateSelectDiv');
			$('#districtSeparateSelect').css({"cursor":"text"});
		    
			$('#schoolSeparateSelect').prop('disabled', true);
			$('#schoolSeparateSelectDiv').addClass('separateSelectDiv');
			 $('#schoolSeparateSelect').css({"cursor":"text"});
			var organizationId = null;
			var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
			
			if(schoolLevel == 0 || schoolLevel == null){
				organizationId = $('select[data-data-type="district"]', getFilterElement()).val();
				$('#studentReportSchool').show();
			}else {
				organizationId = schoolLevel;
				$('#studentReportSchool').hide();
			}
			//Get information about pdf reports
			var assessmentProgId = $('#userDefaultAssessmentProgram option[selected="selected"]').val();
			clearTimeout(ref);
			ref ='refreshed';
			if (data != null && data.length > 0) {
				
				loadPdfInformation(data[0].assessmentProgramId, organizationId);
			
			 $('.allStudentsLinks').on("click", function () {				
				 var currentSelectRecord = $(this).attr('pdfRecoreId');	  
				 var reportyear = $('select[data-data-type="reportYear"]', getFilterElement()).val();
				 var programCode = reportsConfig.getAssessmentProgramCode();;	  
				 var assessmentProgramId = $('#userDefaultAssessmentProgram option[selected="selected"]').val();					
					$.ajax({
			    		url: 'isFilePresent.htm',
			    		dataType: 'json',
			    		data: {
			    			id : currentSelectRecord,
			    			organizationId : organizationId,
			    			assessmentProgramId : assessmentProgId,
		        			reportCode : reportsConfig.getReportCode()
			    			},
			    			type: 'GET'
			    	}).done(function(data){
			    				
			    				if(data){
								//get presigned url for downloading report
			    					 $.ajax({
				    			    		url: 'getAllStudentsReportFile.htm',
				    			    		data:{
				    			    			id: currentSelectRecord,
				    			    			reportYear: reportyear,
				    			    			assessmentProgCode: programCode,
				    			    			assessmentProgramId: assessmentProgramId
				    			    		},
									type: 'GET'
				    				}).done(function (s3response) {
				    			    			var downloadurl = s3response.downloadurl;
			    							var link=document.createElement('a');
						    				document.body.appendChild(link);
						    				link.href=downloadurl;
						    				link.click();
				    			    });
			    				}else {					
			    					
			    					var dialog = $('#studentBundledErrorLinksErrDialog').dialog({
			        					resizable: false,
			        					width: 480,
			        					modal: true,
			        					autoOpen: true,
			        					title: 'Bundled Report',
			        					create: function(event, ui){
			        						var widget = $(this).dialog("widget");
			        					},
			        				buttons: {			        					
			        					OK: function(){				        						
			        						if($('select[data-data-type="school"]', getFilterElement()).val() == 0 || $('select[data-data-type="school"]', getFilterElement()).val() == null){
			        							$('select[data-data-type="district"]', getFilterElement()).trigger("change");
			        							}else{
			        							$('select[data-data-type="school"]', getFilterElement()).trigger("change");	
			        							}
			        						// close 
			        					$(this).dialog('close');
			        					
			        					}
			        				},
			    					});
			    				}			    				
			    			});					 
				});			
			}
			
			$( "input[name*='sortSecondBy']" ).each(function(){
				if($(this).val()=="grouping2"){
					$(this).parent().parent().hide();
				}
			});
			$( "input[name*='sortLastBy']" ).each(function(){
				if($(this).val()=="grouping2"){
					$(this).parent().parent().hide();
				}
			});
			
		$('#studentBundledReportSchool').val('').trigger('change.select2');	
		$('#studentBundledReportContentAreas').val('').trigger('change.select2');	
}

function loadStudentSummaryBundledReportContent(data){
	
	$("#studentSummaryBundledReportContentAreas, #studentSummaryBundledReportGrades, #studentSummaryBundledReportSchool").select2({
 		  placeholder:'Select',
		  multiple: true,
		  allowClear : true
	});
	
	$('#summarySortFirstBy, #summarySortSecondBy, #summarySortLastBy').select2({
		placeholder:'Select',
		multiple: false,
		allowClear : true
	});
	
	//alt student summary
	if(ref != 'refreshed'){
		  ref = window.setTimeout(function(){$('select[data-data-type="district"]', getFilterElement()).trigger("change");}, 5000);
	}
	
	$('#districtSummarySeparateSelect').prop('disabled', true);  
	$('#districtSummarySeparateSelectDiv').addClass('separateSelectDiv');
	$('#districtSummarySeparateSelect').css({"cursor":"text"});
    
	$('#schoolSummarySeparateSelect').prop('disabled', true);
	$('#schoolSummarySeparateSelectDiv').addClass('separateSelectDiv');
	 $('#schoolSummarySeparateSelect').css({"cursor":"text"});
	var organizationId = null;
	var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
	if(schoolLevel == 0 || schoolLevel == null){
		organizationId = $('select[data-data-type="district"]', getFilterElement()).val();
		$('#studentReportSchool').show();
	}else {
		organizationId = schoolLevel;
		$('#studentReportSchool').hide();
	}
	//Get information about pdf reports
	var assessmentProgId =  $('#userDefaultAssessmentProgram option[selected="selected"]').val();
	clearTimeout(ref);
	ref ='refreshed';
	if (data != null && data.length > 0) {
		loadSummaryPdfInformation(data[0].assessmentProgramId, organizationId);
	
	 $('.allStudentsLinks').on("click", function () {		
		 
		 var currentSelectRecord = $(this).attr('pdfRecoreId');	  
		 var reportyear = $('select[data-data-type="reportYear"]', getFilterElement()).val();
		 var programCode = reportsConfig.getAssessmentProgramCode();;	  
		 var assessmentProgramId =  $('#userDefaultAssessmentProgram option[selected="selected"]').val();
		 
			$.ajax({
	    		url: 'isFilePresent.htm',
	    		dataType: 'json',
	    		data: {
	    			id : currentSelectRecord,
	    			organizationId : organizationId,
	    			assessmentProgramId : assessmentProgId,
        			reportCode : reportsConfig.getReportCode()
	    			},
	    			type: 'GET'
	    	}).done(function(data){
	    				
	    				if(data){
						//get presigned url for downloading report
	    					 $.ajax({
		    			    		url: 'getAllStudentsReportFile.htm',
		    			    		data:{
		    			    			id: currentSelectRecord,
		    			    			reportYear: reportyear,
		    			    			assessmentProgCode: programCode,
		    			    			assessmentProgramId: assessmentProgramId
		    			    		},
							type: 'GET'
		    				}).done(function (s3response) {
								var downloadurl = s3response.downloadurl;
		    			    			var link=document.createElement('a');
				    				document.body.appendChild(link);
				    				link.href=downloadurl;
				    				link.click();
		    			    	});
	    				}else {					
	    					
	    					var dialog = $('#studentBundledErrorLinksErrDialog').dialog({
	        					resizable: false,
	        					width: 480,
	        					modal: true,
	        					autoOpen: true,
	        					title: 'Bundled Report',
	        					create: function(event, ui){
	        						var widget = $(this).dialog("widget");
	        					},
	        				buttons: {			        					
	        					OK: function(){				        						
	        						if($('select[data-data-type="school"]', getFilterElement()).val() == 0){
	        								$('select[data-data-type="district"]', getFilterElement()).trigger("change");
	        							}else{
	        								$('select[data-data-type="school"]', getFilterElement()).trigger("change");	
	        							}
	        						// close 
	        					$(this).dialog('close');
	        					
	        					}
	        				},
	    					});
	    				}			    				
	    			});
			 
			
		});
	
	}

	$( "input[name*='summarySortSecondBy']" ).each(function(){
		if($(this).val()=="grouping2"){
			$(this).parent().parent().hide();
		}
	});
	
	$( "input[name*='summarySortLastBy']" ).each(function(){
		if($(this).val()=="grouping2"){
			$(this).parent().parent().hide();
		}
	});
	
	$('#studentSummaryBundledReportSchool').val('').trigger('change.select2');	

}

function loadPdfInformation(assessmentProgramId, organizationId){
	
	$('#studentReportInclude').hide();
	$('#studentBundledCreatedDate').hide();
	$('#studentBundledSortedBy').hide();
	var schoolSelect = $('select[data-data-type="school"]', getFilterElement()).val();
	if(schoolSelect != 0 && schoolSelect!=null){
		$('#gridSchoolName').hide();
	}
	$.ajax({
		url: 'getDynmaicBundleRequestForOrganization.htm',
		dataType: 'json',
		type: 'GET',
		data: {
			organizationId : organizationId,
			assessmentProgramId : assessmentProgramId,
			reportCode : reportsConfig.getReportCode(),
			reportYear : $('select[data-data-type="reportYear"]', getFilterElement()).val()
			}
	}).done(function(data){
				if(data.length > 0){
					$('#studentReportInclude').show();
					$('#studentBundledCreatedDate').show();
					$('#studentBundledSortedBy').show();
					
				   $('#studentReportSchool').empty().append('Schools: ').append(data[0].schoolNames);
				   $('#studentReportSubject').empty().append('Subjects: ').append(data[0].subjectNames);
				   $('#studentReportGrades').empty().append('Grades: ').append(data[0].gradeNames);
				
				
				   $('#submittedUser').empty().append(data[0].submittedUser);
				
				   var date = new Date(data[0].createdDate);
			       var opts = $.extend({}, $.jgrid.formatter.date, opts);
			        
			       $('#submittedDate').empty().append($.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts));
			       $('#studentReportSort').empty().append(data[0].sortString);
					
				 
                var studentBundledReportStatusLists = $('#studentBundledReportStatusLists');
				studentBundledReportStatusLists.empty();
				
				$.each(data, function(i, organizationBundleReport) {
					
					 date = new Date(organizationBundleReport.createdDate);
   			         opts = $.extend({}, $.jgrid.formatter.date, opts);
					var datas="<tr>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.submittedUser+"</td>";
					datas=datas+"<td style='text-align: center;'>"+ $.fmatter.util.DateFormat("", date, 'm/d/Y h:i:s A', opts)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.statusString+"</td>";
					if(schoolSelect == 0 || schoolSelect == null){
					  datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.schoolNames == null ? '' :organizationBundleReport.schoolNames)+"</td>";
					}
					datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.subjectNames == null ? '' :organizationBundleReport.subjectNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+(organizationBundleReport.gradeNames == null ? '' :organizationBundleReport.gradeNames)+"</td>";
					datas=datas+"<td style='text-align: center;'>"+organizationBundleReport.sortString+"</td>";
					datas=datas+"</tr>";							
					studentBundledReportStatusLists.append(datas);
				});                    
				} else{
					$('#studentReportInclude').hide();
					$('#studentBundledCreatedDate').hide();
					$('#studentBundledSortedBy').hide();
				}
				if(ref != 'refreshed'){
				  ref = window.setTimeout(function(){
					  loadPdfInformation(assessmentProgramId,organizationId);
				  }, 10000);
				}
		    });	

}



function loadStudentBundledReportContentAreas(){
	
	checkRef = this;
	
	//Subject
	var studentBundledReportContentAreas = $('#studentBundledReportContentAreas');
	
	// subject event
	districtOrgId  =  $('select[data-data-type="district"]', getFilterElement()).val();
	studentBundledReportSchool = $('#studentBundledReportSchool option:selected').val();

	var selectSchooltext=[];
	 $("#studentBundledReportSchool option:selected" ).each(function(){
		 selectSchooltext.push($(this).val());
	 });	
     
 	var schoolLevel = $('select[data-data-type="school"]', getFilterElement()).val();
	var schoolId = '';
	if(schoolLevel != 0 && schoolLevel!=null )
	{
		selectSchooltext.push($('select[data-data-type="school"]', getFilterElement()).val());
	}
	
	if(studentBundledReportSchool != 0  && studentBundledReportSchool != '' && selectSchooltext != 0 ){
	$.ajax({
	url: 'getContentAreaForBundledReport.htm',
	dataType: 'json',
	data: {
		reportType : reportsConfig.getReportType(),
		schoolIds : selectSchooltext,			
		assessmentProgId : $('#userDefaultAssessmentProgram option[selected="selected"]').val(),
		assessmentProgCode : reportsConfig.getAssessmentProgramCode()
		},
		type: 'GET'
	}).done(function(subjects){
			totalSubjectLength = subjects.length;
			studentBundledReportContentAreas.empty();
			//studentBundledReportContentAreas.append($('<option>Select</option>').attr("value", 0));
			$.each(subjects, function(i, subject) {
				if(totalSubjectLength != 1){
				studentBundledReportContentAreas.append($('<option></option>').attr("value", subject.id).text(subject.name));
				}else {
					studentBundledReportContentAreas.append($('<option selected="select"></option>').attr("value", subject.id).text(subject.name));
				}
			});
			
			studentBundledReportContentAreas.val('').trigger('change.select2');
			if(totalSubjectLength != 1){
			studentBundledReportContentAreas.val('').trigger('change.select2');
			studentBundledReportContentAreas.select2({placeholder:'Select'}); 	
			}else {
				$('#studentBundledReportGrades').empty();
				$('#studentBundledReportGrades').val('').trigger('change.select2');
				studentBundledReportGrade();
			}
		}).fail(function(){});

}
	studentBundledReportContentAreas.val('').trigger('change.select2');
}

