function createOrganizationInit(){
	gCreateOrganizationLoadOnce = true;
	var orgTypeCodes = [];
	var multiselectOpts = {
			placeholder:'Select',
			multiple: false
		};
	
	$(".timeDiv").hide();
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : DE7852 : Add Org Manually: Able to enter a start date that is > the end date.
	 * Added below validation menthod to make sure that end date is always greater than start date
	 */
	$.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required') || element.attr('type') == 'file') {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});

	$.validator.addMethod("endDateGreaterThanStartDate", 
			function(value, element, params) {
			    if (!/Invalid|NaN/.test(new Date(value))) {
			    	if($("#contractingOrgFlag").val() == "yes"){
				        return new Date(value) >= new Date($(params).val());			    		
			    	} else {
			    		return true;
			    	}
			    }
			    if($("#contractingOrgFlag").val() == "yes"){
			    	return isNaN(value) && isNaN($(params).val()) 
			        || (Number(value) > Number($(params).val())); 
			    } else {
			    	return true;
			    }
			    
			},'Must be greater than start date.');
	
	$.validator.addMethod("dateUS",
		    function(value, element) {
		        return value.match(/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/[0-9]{4}$/);
		    },
		"Please enter a correct date.");
	
	$.validator.addMethod("timeFormatValidator", 
			function(value) {
				var inputTime = value.split(" ");
				var timeDigit = inputTime[0].split(":");
				if(inputTime.length != 2 || timeDigit.length != 2){
					return false;
				}
				else if(inputTime[1].trim() != 'AM' && inputTime[1].trim() != 'PM'){
					return false;
				}
				else if(timeDigit[0] > 12 || timeDigit[0] < 1 || timeDigit[1] > 59){
					return false;
				}
				else{
					return true;
				}
			},'Please enter a valid time format - HH:MM AM/PM');
	
		$('#createOrganizationForm').validate({
			ignore: "",
			rules: {
				organizationName: {required: true},
				organizationDisplayId: {required: true},
				endDate: {required: {
						depends: function (element) {
							if($("#contractingOrgFlag").val() == "yes"){
								return true;	
							}
							else 
							{
								return false;	
							}
						}
				},endDateGreaterThanStartDate: "#startDate", dateUS: {
					depends: function (element) {
						if(!$(element).is(":hidden")){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			}},
				startDate: {required: {
					depends: function (element) {
						if($("#contractingOrgFlag").val() == "yes"){
							return true;
						}
						else{
							return false;
						}
					}
				}
				, dateUS: {
					depends: function (element) {
						if(!$(element).is(":hidden")){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			}},
				reportYear: {required: {
					depends: function (element) {
						if($("#contractingOrgFlag").val() == "yes"){
							return true;
						}
						else{
							return false;
						}
					}
				}},
				testEndTime: {timeFormatValidator: {
					depends: function (element) {
						if(!$(element).is(":hidden") && $(element).val()!=''){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			}},
				testBeginTime: {timeFormatValidator: {
					depends: function (element) {
						if(!$(element).is(":hidden") && $(element).val()!=''){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			}}
			}
		});
		$('#createOrgStateFilter').orgFilter({
			'containerClass': '',
			'url': 'getStatesOrgsForUser.htm',
			'childOrgUrl': 'getChildOrgsForFilter.htm',
			'requiredLevels': []
		});
		$('#createOrgStateFilter').orgFilter('option','requiredLevels', [10]);
		filteringOrganizationSet($('#createOrgStateFilter'));
		
		$('#contractingOrgFlag').addClass('required').select2(multiselectOpts);
		$('#orgType').addClass('required').select2(multiselectOpts);
		$('#parentOrg').select2(multiselectOpts);
		$('#buldingunique').select2(multiselectOpts);
 		$("#startDate").datepicker({
 		    dateFormat: "mm/dd/yy"
 		}).datepicker(); 		
 		$("#endDate").datepicker({
 		    dateFormat: "mm/dd/yy"
 		}).datepicker();
 	    $('#testingModelSelect').select2(multiselectOpts);

	 $("#createOrgSave").off('dblclick');
	   
	   $("#createOrgSave").dblclick(function(e){
		  e.stopPropagation();
		  e.preventDefault();
		  return false;
	   });
	    
	   $("#createOrgSave").on("click",function(){
		   createOrgSaveHandler();
	   });
			    
	   
	   $('#createOrgReset').on("click",function(e){
		   $("#contractingOrgFlag").val("").trigger('change.select2'); 
		   $("#orgType").val("").trigger('change.select2'); 
			$("#organizationName_create").val('');
			$("#organizationDisplayId").val('');
			$(".dateDiv").hide();
			$("#assessmentProgramsDiv").hide();  
			$("#testingModelDiv").hide();  
			$('#createOrgStateFilter').orgFilter('reset');
			$('#createOrgStateFilter').hide();
			$("#parentOrgDiv").hide();
			$("#orgStructureDiv").hide();
			$("label.error").html('');
			$("#message").html('');
			$('#createOrganizationForm')[0].reset();
			$("#buldinguniqueDiv").hide();
			$("#programHeaderDiv").hide();
			$("#statusSchoolYearHeaderDiv").hide();
			$("#statusDateHeaderDiv").hide();
			$("#reportYearDiv").hide();		
			$(".timeDiv").hide();
			e.preventDefault();
	    });
	   	   
	$('#contractingOrgFlag').on("change",function() {
		var multAp='';
		var defaultAp=$('#hiddenCurrentAssessmentProgramId').attr('value');
		var options=$('#assessmentProgramSelect').find('option');
		var contractingOrgFlag = $("#contractingOrgFlag").val();
		$("#assessmentProgramSelect").removeClass('required');
		$('#assessmentProgramSelect').select2({placeholder:'Select', multiple: true}); 
		if(contractingOrgFlag != null){
			
			 if(contractingOrgFlag == "yes"){
				  for(var i=0;i<options.length;i++)	{
	        	  	multAp=$(options[i]).val();	        	  
	        	  	  	if(multAp === defaultAp){	        	  	  		
		          		$($('#assessmentProgramSelect').find('option')[i]).attr('selected','selected');
				  	} 
	        	  	
			} 
				 $('#assessmentProgramSelect').trigger('change.select2');
				 $(".dateDiv").show();
				 $("#statusDateHeaderDiv").hide();
				 $(".timeDiv").hide();
				 $("#assessmentProgramsDiv").show();
                 $("#assessmentProgramSelect").addClass('required');
				 $("#programHeaderDiv").show();
				 $("#statusSchoolYearHeaderDiv").show();		
				 $("#statusDateHeaderDiv").hide();
				 $("#reportYearDiv").show();
				 $("#reportYear").addClass('numericOnly');
				 $("#reportYear").addClass('validReportYear');
				 if($("#orgType").val() == "ST" ){
					 $("#orgStructureDiv").show();
					 $(".timeDiv").show();
				 }
				 $('.select2-hidden-accessible').removeAttr("aria-hidden");
				 showHideDLmTestingModel();
				 
				 var orgType = $("#orgType").val();				 
				 if(orgType == "ST"){
					    $("#orgStructureSelect").val('').trigger('change.select2');
			    		var valArr = ['CONS','ST'];
						for(var i=0; i < valArr.length ; i++) {
							$("#orgStructureSelect").select2('open').find(":checkbox[value='"+valArr[i]+"']").prop("disabled","disabled");
								$("#orgStructureSelect").select2('open').find(":checkbox[value='"+valArr[i]+"']").prop("checked","checked");						
								$("#orgStructureSelect option[value='" + valArr[i] + "']").attr("selected", 1);	  					
								$("#orgStructureSelect").addClass('required').trigger('change.select2');
							}
						
						$("#orgStructureSelect").addClass('orgStructureSelectClass');
						$('.orgStructureSelectClass').find('input').each(function(){
						    if($.inArray($(this).val(), valArr) > -1)
								$(this).prop('disabled', true);
						});
				 }
			 } else{	
				 $("#buldinguniqueDiv").hide();
				 $('#buldingunique').removeClass('required');
				 $("#orgStructureDiv").hide();	
				 $(".dateDiv").hide();
				 $(".timeDiv").hide();
				 $("#statusDateHeaderDiv").hide();
				 $("#assessmentProgramsDiv").hide();
				 $("#programHeaderDiv").hide();
				 $("#statusSchoolYearHeaderDiv").hide();
				 $("#reportYearDiv").hide();
				 $("#testingModelDiv").hide();
				 $('#testingModelSelect').removeClass('required');
				 $("#reportYear").removeClass('validReportYear');
				 $("#reportYear").removeClass('numericOnly');
				 var orgType = $("#orgType").val();
				 if(orgType == "SCH"){
					 $(".timeDiv").show();
					 $("#statusDateHeaderDiv").show();
				 }
			 }
		 }
	});
			
	$("#orgStructureSelect").on('select2:unselecting', function (e) {
	    var id = e.params.args.data.id; //your id	
	    if(id == 'CONS' || id == 'ST'){
	    	e.preventDefault();
	    }
	});
	
	
	   $('#orgType').on("change",function() {
			var orgType = $("#orgType").val();
			$('#parentOrg').removeClass('required');
			$("#orgStructureSelect").removeClass('required');
			if(orgType != null) {				
				resetOrgSelection("orgType");
				  
				if(orgType == "ST") {
		    		var valArr = ['CONS','ST'];
					for(var i=0; i < valArr.length ; i++) {			
							$("#orgStructureSelect option[value='" + valArr[i] + "']").prop("selected", 1);	 					
							$("#orgStructureSelect").addClass('required').trigger('change.select2');
						}
					
					$("#orgStructureSelect").addClass('orgStructureSelectClass');
					$('.orgStructureSelectClass').find('input').each(function(){
					    if($.inArray($(this).val(), valArr) > -1)
							$(this).prop('disabled', true);
					});
				}
				 if(orgType == "ST") {
					 if($("#contractingOrgFlag").val() == "yes" ){
						 $("#orgStructureDiv").show();
						 $(".timeDiv").show();
					 }else{
						 $("#statusDateHeaderDiv").hide();
						 $(".timeDiv").hide();
					 }
					 $("#parentOrgDiv").show();
					 $("#orgHierarchyDiv").hide();
					 $("#createOrgStateFilter_state").removeClass("required");
					 $('#parentOrg').addClass('required');
					 $('#parentOrg').trigger('change.select2');
					 $("#createOrgStateFilter_region").removeClass("required");
					 $("#createOrgStateFilter_area").removeClass("required");
					 $("#createOrgStateFilter_district").removeClass("required");
					 $("#createOrgStateFilter_building").removeClass("required");
				 } else if(orgType == "CONS") {
					 orgTypeCodes.push("CONS");
					 $("#orgStructureDiv").hide();
					 $("#buldinguniqueDiv").hide();
					 $("#parentOrgDiv").hide();
					 $('#createOrgStateFilter').hide();
					 $("#createOrgStateFilter_state").removeClass("required");
					 $("#createOrgStateFilter_region").removeClass("required");
					 $("#createOrgStateFilter_area").removeClass("required");
					 $("#createOrgStateFilter_district").removeClass("required");
					 $("#createOrgStateFilter_building").removeClass("required");
					 $(".timeDiv").hide();
				 }

				 else if(orgType == "") {
					 $("#orgHierarchyDiv").hide();
					 $("#createOrgStateFilter_state").removeClass("required");
					 $("#buldinguniqueDiv").hide();
					 $('#buldingunique').removeClass('required');
					 $(".timeDiv").hide();
				 }				 
				 else {
					 orgTypeCodes.push("ST");
					 $("#orgStructureDiv").hide();
					 $("#buldinguniqueDiv").hide();
					 $("#parentOrgDiv").hide();
					 $("#orgHierarchyDiv").show();
					 $("#createOrgStateFilter_state").addClass("required");
					 $('#createOrgStateFilter').show();
					 $('#createOrgStateFilter').orgFilter('reset');
					 $('#createOrgStateFilter').orgFilter('setOption', 'showLevelsUntil', $('#orgType').find(":selected").data('level')-10);
					 $('#createOrgStateFilter').orgFilter('setOption', 'requiredLevels', [$('#orgType').find(":selected").data('level')-10]);
					 $(".timeDiv").hide();
				 }				 
				 if(orgType == "SCH"){
					 if($("#contractingOrgFlag").val() == "no" ){
						 $("#statusDateHeaderDiv").show();
						 $(".timeDiv").show();
					 }else{
						 $("#statusDateHeaderDiv").hide();
						 $(".timeDiv").hide();
					 }
				 }
				 if(orgType == "RG"){
					 $("#createOrgStateFilter_region").removeClass("required");
					 $("#createOrgStateFilter_area").removeClass("required");
					 $("#createOrgStateFilter_district").removeClass("required");
					 $("#createOrgStateFilter_building").removeClass("required");
				 }
				 
				 if(orgType == "AR"){
					 $("#createOrgStateFilter_area").removeClass("required");
					 $("#createOrgStateFilter_district").removeClass("required");
					 $("#createOrgStateFilter_building").removeClass("required");
				 }				 

				 if(orgType == "DT"){					
					 $("#createOrgStateFilter_district").removeClass("required");
					 $("#createOrgStateFilter_building").removeClass("required");
				 }
				 
			 }			
		});
			
			
		$("#orgStructureSelect").on("change",function() {
			var orgStructureSelect = [];
			var vals = [];
			var textvals = [];	
			$('#orgStructureSelect :selected').each( function( i, selected ) {
				textvals[i] = $( selected ).text();
			});
			
			$('#buldingunique').html('');  
			$('#buldingunique').removeClass('required');
			orgStructureSelect = $("#orgStructureSelect").val();
					
				if($.inArray("BLDG", orgStructureSelect) >= 0) {
					$('#buldingunique').append(new Option("Select",""));
					if($.inArray("CONS", orgStructureSelect) == -1) $('#buldingunique').append(new Option("Consortia","CONS"));
					if($.inArray("ST", orgStructureSelect) == -1) $('#buldingunique').append(new Option("State","ST"));					
					$("#buldinguniqueDiv").show();						
					for(var i=0;i<orgStructureSelect.length;i++){					
						$('#buldingunique').append(new Option(textvals[i],orgStructureSelect[i]));
					}
					$('#buldingunique').addClass('required');
				} else {
					$("#buldinguniqueDiv").hide();
				}
				$("#buldingunique").trigger('change.select2');
			});
		
		$("#assessmentProgramSelect").on("change",function() {			
			showHideDLmTestingModel();
		});
		
		 $("#tabs_organization #message").css("margin-left", "32px");
		
	};
	
	function showHideDLmTestingModel(){
		 var checkTestingModel=false;
		 $("#assessmentProgramSelect option:selected").each(function(){
			    	if($(this).attr('data-abbName')=='DLM')	checkTestingModel=true;			    	
		 });	
			
		if(checkTestingModel){			 
				$("#testingModelDiv").show();		
				$('#testingModelSelect').addClass('required');
		}else{
			   	$('#testingModelSelect').val('').trigger('change.select2');
				$("#testingModelDiv").hide();
				$('#testingModelSelect').removeClass('required');
		}		 
	}
	
	
	$.validator.addMethod("validReportYear", function(value, element) {
	    return (parseInt(value) > 2014);
	}, "Report year should be greater than 2014");
	
	$.validator.addMethod('numericOnly', function (value) {
	       return /^[0-9]+$/.test(value);
	}, '4 digits year is required');
	
	function createOrgSaveHandler(e){
		$("#createOrgSave").addClass('ui-state-disabled');
		if($('#createOrganizationForm').valid()) {
			$('#message').html('');
			var assessmentPrograms = [];
				assessmentPrograms = $("#assessmentProgramSelect").val();
				var orgType = $("#orgType").val();
				var orgStructureSelect = [];
				if($("#orgStructureSelect").val() != null && $("#orgStructureSelect").val() != '')
					orgStructureSelect = $("#orgStructureSelect").val();
				
				var orgName = $("#organizationName_create").val();
				var orgDisplayId = $("#organizationDisplayId").val();
				var contractingOrgFlag = $("#contractingOrgFlag").val();
				var buldingUniqueness = $('#buldingunique').val();
				if(buldingUniqueness == null || buldingUniqueness == "undefined") {
					buldingUniqueness = "";
				}
				
				var parentOrg = $("#parentOrg").val();
				if(parentOrg == null || parentOrg == "undefined") {
					parentOrg = "";
				}
				
			var startDate = $('#startDate').val();
			if(startDate == null || startDate == "undefined") {
				startDate = "";
				}
			
			var endDate = $('#endDate').val();
			if(endDate == null || endDate == "undefined") {
				endDate = "";
				}
			
			var state = $('#createOrgStateFilter_state').val();
			if(state == null || state == "undefined") {
				state = "";
				}
			
			var region = $('#createOrgStateFilter_region').val();
			if(region == null || region == "undefined") {
				region = "";
				}
			
			var area = $('#createOrgStateFilter_area').val();
			if(area == null || area == "undefined") {
				area = "";
				}
			
			var district = $('#createOrgStateFilter_district').val();
			if(district == null || district == "undefined") {
				district = "";
				}
			
			var building = $('#createOrgStateFilter_building').val();
			if(building == null || building == "undefined") {
				building = "";
				}
	
			var expirePasswords = $("#expirePasswordsFlagSelect").val();
			if(expirePasswords == "" || expirePasswords == "undefined" || expirePasswords == undefined ) {
				expirePasswords = null;
			}
			
			var expirationDateType = $('#expirationDateTypeSelect').val();
			
			if(expirationDateType == "" || expirationDateType == "undefined" ||  expirationDateType == undefined) {
				expirationDateType = null;
			}		
			
			var reportYear = $('#reportYear').val();
			if(reportYear == null || reportYear == "undefined" || reportYear == undefined) {
				reportYear = "";
			}
			
			var testingModel = $('#testingModelSelect').val();
			if(testingModel == null || testingModel == "undefined"  || testingModel == undefined) {
				testingModel = "";
			}
			
			var testBeginTime = $('#testBeginTime').val();
			if(testBeginTime == null || testBeginTime == "undefined") {
				testBeginTime = "";
				}
			
			var testEndTime = $('#testEndTime').val();
			if(testEndTime == null || testEndTime == "undefined") {
				testEndTime = "";
				}

			var testDays = $("input[name='testDays']:checked").val(); 
			if(testDays == null || testDays == "undefined") {
				testDays = "";
				}

			$.ajax({
				url: 'createOrganization.htm',					
		            type: "POST",
				data:{
					orgName: orgName,
					orgDisplayId: orgDisplayId,
					orgType: orgType,
					assessmentProgramIds: assessmentPrograms,
					organizationStructure: orgStructureSelect,
					contractingOrgFlag: contractingOrgFlag == 'yes' ? true : false,
					buldingUniqueness: buldingUniqueness,						
					parentOrg: parentOrg,
					startDate: startDate,
					endDate: endDate,
					state: state,
					region: region,
					area: area,
					district: district,
					building: building,
					expirePasswords: expirePasswords,
					expirationDateType: expirationDateType,
					testingModel: testingModel,
					reportYear: reportYear,
					testBeginTime: testBeginTime,
					testEndTime: testEndTime,
					testDays: testDays
				}
				}).done(function(response) {
					if(response.result == "success") {						
						$('#message').html('<span class="info_message ui-state-highlight">Successfully created organization.</span>').show();
						setTimeout(resetValue, 2000);
					} else if(response.result == "failed") {
						$('#message').html('<span class="error_message ui-state-error">Failed to create Organization.</span>').show();
						$(this).off('click');
					} else if(response.result == "duplicate") {
						$('#message').html('<span class="error_message ui-state-error">Organization already exists with same display identifier.</span>').show();
						$(this).off('click');
					} else if(response.result == 'invalidOrgType') {
						$('#message').html('<span class="error_message ui-state-error">Invalid Organization Type specified.</span>').show();
						$(this).off('click');
					}
		    		$("#createOrgSave").removeClass('ui-state-disabled');
		    		$("#profileHeaderDiv").hide();
				}).fail(function() {
					$('#message').html('<span class="error_message ui-state-error">Failed to create Organization.</span>').show();
					$(this).off('click');
		    		$("#createOrgSave").removeClass('ui-state-disabled');
				});
			
		} else {
			$('#message').html('<span class="error_message ui-state-error">Correct validation errors.</span>').show();
			$(this).off('click');
			$("#createOrgSave").removeClass('ui-state-disabled');
		};
};