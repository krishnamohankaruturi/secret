
$(document).ready(function(){
	$.ajax({
			url: 'getstatestudentidentifierlength.htm',					
	        type: "POST",
			success: function(stateStudentIdentifierLength){
			$('#stateStudentId').attr("maxlength", stateStudentIdentifierLength);
			}
});
});
	
	$("#stateStudentId").on("keypress",function(event){
	    if(event.keyCode == 13){
	    	event.preventDefault();
	        $("#stateStudentIdentifierAdd").click();
	    }
	});
	
	$('#checkStateStudentIdentifierForm').validate({
		ignore: "",	
		rules: {
			stateStudentId: {required: true}
		}
	});

	$('#stateStudentId').on('input',function(e){	  
	    if($(this).val()==''){
	    	hideErrorMessages();
	    }
	});

	$('#stateStudentIdentifierActivate').on("click",function() {
		var stateStudentId = $("#stateStudentIdentifierToActivate").val();
		var enrollmentId = $("#enrollmentId").val();
		var studentId = $("#studentIdToActivate").val();
		var gradeId = $("#gradeId").val();
		var districtId = $("#districtId").val();
		var schoolId = $("#schoolId").val();
		openActivationStudent(stateStudentId,studentId,districtId,schoolId,gradeId,enrollmentId);		
	});
	
	$('#stateStudentId').on("click",function() {
		$(".studentARTSmessages").hide();
		$('#successMessage').hide();
	});
	
	
function checkStateStudentIdentifierInit(){
	
	    $("#stateStudentId").val('');
	
		$('#stateStudentIdentifierAdd').on("click",function() {

			hideErrorMessages();
			
			if($('#checkStateStudentIdentifierForm').valid()) {
			
				var stateStudentId = $("#stateStudentId").val().trim();
				
				$.ajax({
	 				url: 'findStudentIfExist.htm',					
	 				dataType: 'json',
	 		        type: "POST",
	 		       data:{
	 		    	  stateStudentId : stateStudentId	 		    	  
					},
	 				success: function(response){
						if(response.status=='success')
						{
							$("#stateSchoolId").val(stateStudentId);
							$('#addStudentsPage').show();
							resetAddStudentSelection();
							$("#stateStudentId").attr("disabled", true);
							$("#stateStudentIdentifierAdd").addClass('ui-state-disabled');
							if(!gAddStudentsLoadOnce) addStudentsInit();
							
							getContractionOrg();
							
						}
						else if(response.status=='error'){
							$(".errorMessagesAddStudent").show(); 				
	 						setTimeout(function(){ $(".errorMessagesAddStudent").hide(); },3000);
						}
						else{
							
							if(response.status=='add_student_exist_not_active_find'){
								$('#stateStudentIdentifierActivateBtnDiv').show();	
								$("#stateStudentIdentifierToActivate").val(response.enrollment.student.stateStudentIdentifier);
								$("#enrollmentId").val(response.enrollment.id);
								$("#studentIdToActivate").val(response.enrollment.studentId);								
								$("#gradeId").val(response.enrollment.currentGradeLevel);
								$("#schoolId").val(response.enrollment.attendanceSchoolId);
								$("#districtId").val(response.enrollment.attendanceSchoolDistrictId);
							}							
							
							$('#addStudentErrorMessages').html(response.message);
							$('#addStudentErrorMessages').show();
							$('.errorMessagesAddStudent').show();			
	 						
			
						}
						
	 				},
	 				error: function() {
	 					$(".errorMessagesAddStudent").show(); 				
	 					setTimeout(function(){ $(".errorMessagesAddStudent").hide(); },3000);
	 				}
	 			});		
				
			}
		});		
}

function hideErrorMessages(){
	$('#stateStudentIdentifierActivateBtnDiv').hide();
	$("#enrollmentId").val('');
	$("#studentIdToActivate").val('');								
	$("#gradeId").val('');
	$("#schoolId").val('');
	$("#districtId").val('');
	$('#addStudentErrorMessages').hide();
	$('.errorMessagesAddStudent').hide();	
	$('#addStudentsPage').hide();
	$("#stateStudentId").attr("disabled", false);	
	$("#stateStudentIdentifierAdd").removeClass('ui-state-disabled');
	$(".studentARTSmessages").hide();
	$('#successMessage').hide();
	$('#addStudentsForm').find('label.error').remove();
}

function resetAddStudentIdentifierSearch(){
	$("#stateStudentId").val('');
	hideErrorMessages();
}

