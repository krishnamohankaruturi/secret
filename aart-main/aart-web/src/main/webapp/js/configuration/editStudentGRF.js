$(function() {

					$('.editStudentNumeric').on('input', function (event) { 
					    this.value = this.value.replace(/[^0-9]/g, '');
					});			
									
					$.validator.addMethod("greaterThanCurrentDate", function (value, element) {						
						 var now = new Date();
						 var myDate = new Date(value);
						 return this.optional(element) || myDate < now;
					});
					
					$.validator.addMethod("lessThanGrfYear", function (value, element) {						
						 var now = new Date('12/31/0999');
						 var myDate = new Date(value);
						 return this.optional(element) || myDate > now;
					});
					
					var today = new Date();
					$('#manageGRFSubject').select2({
						placeholder : 'Select',
						multiple : false
					});
					
					
					var form = $('#editGRFStudentForm');

					$('select', form).select2({
						placeholder : 'Select',
						multiple : false
					});
					
					$('#editStudentGrfDateOfBirth, #editStudentGrfInformationExitWithdrawalDate',
						form).datepicker({
						dateFormat : 'mm/dd/yy',
						//maxDate: today,
						changeMonth : true,
						changeYear : true
					});
					
					$('#editStudentGrfInformationSchoolEntryDate, #editStudentGrfInformationDistrictEntryDate, #editStudentGrfInformationStateEntryDate').datepicker({
						dateFormat : 'mm/dd/yy',
						//yearRange: '1990:' + (today.getFullYear()),
						//maxDate: today,
						changeMonth : true,
						changeYear : true
					});
					
					$.validator.addMethod("dateUS",
						    function(value, element) {
						        return value.match(/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/[0-9]{4}$/);
						    },
						    "Please enter a correct date.");

					form.validate({
						ignore : '',
						onkeyup : false,
						submitHandler : function() {

						},
						errorPlacement : function(error, element) {
							if (element.hasClass('required')
									|| element.attr('type') == 'file') {
								error.insertAfter(element.next());
							} else {
								error.insertAfter(element);
							}
						},
						rules : {
							legalFirstName : {
								required : true,
								maxlength : 80
							},
							legalMiddleName : {
								maxlength : 80
							},
							legalLastName : {
								required : true,
								maxlength : 80
							},	
							dateOfBirth : {
								//required : true,
								lessThanGrfYear :true,
								dateUS : true
							},							
							stateStudentIdentifier : {
								required : true,
								maxlength : 50
							},
							/*gender : {
								required : true
							},
							comprehensiveRace : {
								required : true
							},*/
							/*hispanicEthnicity : {
								required : true
							},*/
							/*primaryDisabilityCode : {
									required : true
							},
							esolParticipationCode : {
									required : true
							},*/
							gradeId : {
									required : true
							},
							attendanceSchoolProgramIdentifier : {
								maxlength : 100
							},
							aypSchoolIdentifier : {
								maxlength : 100
							},
							schoolIdentifier : {
								required : true,
								maxlength : 100
							},
							residenceDistrictIdentifier : {
								required : true,
								maxlength : 100
							},							
							schoolEntryDate : {
								//required : true,
								dateUS: true,
								//greaterThanCurrentDate: true,
								lessThanGrfYear :true
							},
							districtEntryDate : {								
								dateUS: true,
								//greaterThanCurrentDate: true,
								lessThanGrfYear :true
							},
							stateEntryDate : {								
								dateUS: true,
								//greaterThanCurrentDate: true,
								lessThanGrfYear :true
							},
							educatorIdentifier : {
								required : true
							},
							exitWithdrawalDate : {
								dateUS: true,
								lessThanGrfYear :true
							},
							/*localStudentIdentifier : {								
								dateUS: true,
								greaterThanCurrentDate: true,
								lessThanGrfYear :true
							},*/
						},
						messages: {	
							   schoolEntryDate: { 				                  
				                    greaterThanCurrentDate: 'Date Should be less than current date.',
				                    lessThanGrfYear : 'Date should be greater than year 1000'
				               },
				               districtEntryDate: { 				                  
				                    greaterThanCurrentDate: 'Date Should be less than current date.',
				                    lessThanGrfYear : 'Date should be greater than year 1000'
				               },
				               stateEntryDate: { 				                  
				                    greaterThanCurrentDate: 'Date Should be less than current date.',
				                    lessThanGrfYear : 'Date should be greater than year 1000'
				               },
				               dateOfBirth: { 				                  
				                    greaterThanCurrentDate: 'Date Should be less than current date.',
				                    lessThanGrfYear : 'Date should be greater than year 1000'
				               }
							
						}
					});

					$('#saveGRFStudentInformation').click(function() {
						
							if($('#editGRFStudentForm').valid()){ 
							
							var originalStateStudentId = $("#originalStateStudentId").val();
							var originalCurrentGradeLevel = $("#originalCurrentGradeLevel").val();
							var stateStudentIdentifier = $("#stateStudentIdentifier").val();
							if(stateStudentIdentifier!=null && stateStudentIdentifier!='') stateStudentIdentifier =  stateStudentIdentifier.trim()
							var currentGradelevel = $("#currentGradelevel").val();
							
							$("#saveGRFStudentInformationPopup #errorMessageAcknowledge").hide();
							
							if(($('input[name="invalidate"]:checked').val()=='1') ||
									(originalStateStudentId != stateStudentIdentifier) || 
									(originalCurrentGradeLevel != currentGradelevel)){
								
								$('#saveGRFStudentInformationPopup').dialog({
											autoOpen : false,
											modal : true,
											width : 900,																	
											create : function(
													event, ui) {
												var widget = $(this).dialog("widget");
											},
	
											buttons : {
												"Continue" : function() {
													
													if($('#saveGRFStudentInformationPopup #editStudentAckGRF').is(':checked')){
														$("#saveGRFStudentInformationPopup #errorMessageAcknowledge").hide();
														saveStudentGrf();
														$(this).dialog("close");
													}else{
														$("#saveGRFStudentInformationPopup #errorMessageAcknowledge").show();
													}												
												},
												"Cancel" : function() {
													$(this).dialog("close");												
												}
											},
	
											beforeClose : function() {
											
											},
											open : function(){
												$('#saveGRFStudentInformationPopup #editStudentAckGRF').prop("checked", false);
												$("#ivalidateAckContent, #ivalidateAckContent hr").hide();
												$("#stateStudentIdAckContent, #stateStudentIdAckContent hr").hide();
												$("#gradeAckContent, #gradeAckContent hr").hide();
																							
												if($('input[name="invalidate"]:checked').val()=='1')
													$("#ivalidateAckContent, #ivalidateAckContent hr").show();
												else
													$("#ivalidateAckContent, #ivalidateAckContent hr").hide();
												
												if(originalStateStudentId != stateStudentIdentifier)
													$("#stateStudentIdAckContent, #stateStudentIdAckContent hr").show();
												else
													$("#stateStudentIdAckContent, #stateStudentIdAckContent hr").hide();
													
												if(originalCurrentGradeLevel != currentGradelevel)
													$("#gradeAckContent, #gradeAckContent hr").show();
												else
													$("#gradeAckContent, #gradeAckContent hr").hide();												
											}
										}).dialog('open');
								}
							else{
								saveStudentGrf();
							}
	
						}else{
							$('#editStudentGrfInfoError').html('Please enter all mandatory fields');
							$('#editStudentGrfInfoError').show();
							$('#editGRFStudentDiv').animate({
								scrollTop: $("#editStudentGrfInfoError").offset().top+$("body, html").offset().top
							}, 1500);			
							$('body, html').animate({scrollTop:0}, 'slow');
							setTimeout(function(){ $("#editStudentGrfInfoError").hide(); },3000);
					}						
						
					});

				});
	
	function saveStudentGrf(){
			
			var formData = $('#editGRFStudentForm').serializeArray();			
				
			var assessmentProgramId = $('#userDefaultAssessmentProgram').val();
			var stateId = $('#userDefaultOrganization').val();
			var reportYear = $('#manageGrfReportYear').val();
			var uniqueRowIdentifier = $("#manageGRFUniqueRowIdentifier").val();
			var stateStudentIdentifier = $("#manageGRFStateStudentID").val();
			var subjectId = $("#manageGRFSubject").val();
			
			if(stateStudentIdentifier!='' && stateStudentIdentifier!=null && stateStudentIdentifier!=undefined){
				stateStudentIdentifier = $("#stateStudentIdentifier").val();
			}
			
			$('#editGRFStudentDiv').animate({
				scrollTop: $("#editStudentGrfInfoSuccess").offset().top+$("body, html").offset().top
			}, 1500);			
			$('body, html').animate({scrollTop:0}, 'slow');
			
			$.ajax({
				url: 'saveStudentGrfInf.htm',					
				dataType: 'json',
		        type: "POST",
				data:formData,
				success: function(data){
					if(data.success)
					{			
						 $("#editGRFStudentDiv").load('editStudentGrfInformation.htm', {
							   assessmentProgramId:assessmentProgramId,
					           stateId:stateId,
					           reportYear:reportYear,
					           uniqueRowIdentifier : uniqueRowIdentifier,
					           stateStudentIdentifier:stateStudentIdentifier,
					           subjectId:subjectId
							 
						 }, function(){
								$("#editGRFStudentContent").show();		
								$('#editStudentGrfInfoSuccess').show();	
								setTimeout(function(){ $("#editStudentGrfInfoSuccess").hide(); },7000);
						 }).dialog('open');
										
					}
					else{
						$('#editStudentGrfInfoError').html(data.error);
						$('#editStudentGrfInfoError').show();			
					
						$('#editGRFStudentDiv').animate({
							scrollTop: $("#editStudentGrfInfoError").offset().top+$("body, html").offset().top
						}, 1500);			
						$('body, html').animate({scrollTop:0}, 'slow');
						
						setTimeout(function(){ $("#editStudentGrfInfoError").hide(); },3000);
					}					
				},
				error: function() {
					$('#editStudentGrfInfoError').html('Unable to update');
					$('#editStudentGrfInfoError').show();
					$('#editGRFStudentDiv').animate({
						scrollTop: $("#editStudentGrfInfoError").offset().top+$("body, html").offset().top
					}, 1500);			
					$('body, html').animate({scrollTop:0}, 'slow');
					setTimeout(function(){ $("#editStudentGrfInfoError").hide(); },3000);
				}
				});
			
	}