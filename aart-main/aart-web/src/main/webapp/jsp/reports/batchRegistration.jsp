<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<security:authorize access="hasRole('PERM_BATCH_REGISTER')">
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	

	<div id="createOrganization" style="padding:20px;">
		<div id="ARTSmessages" class="messages">

		</div>
	    <div>
			<label for="assessmentPrograms">Assessment Program:</label>&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="assessmentPrograms" title="Assessment Program" class="bcg_select" name="assessmentPrograms">
				<option value="">Select Assessment Program</option>
			</select>
		</div>
		<div>
			<label for="testingPrograms">Testing Program:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="testingPrograms" title="Testing Program" class="bcg_select" name="testingPrograms">
				<option value="">Select Testing Program</option>
			</select>
		</div>
        <div>
			<label for="assessments">Assessment:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="assessments" title="Assessment" class="bcg_select" name="assessments">
				<option value="">Select Assessment</option>
			</select>
		</div>
		<div>
			<label for="testTypes">Test Type:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="testTypes" title="Test Type" class="bcg_select" name="testTypes">
				<option value="">Select Test Type</option>
			</select>
		</div>		
		<div>
			<label for="courses">Course:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="courses" title="Course" class="bcg_select" name="courses">
				<option value="">Select Course</option>
			</select>
		</div>
		<div>
			<label for="grades">Grade:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="grades" title="Grade" class="bcg_select" name="grades">
				<option value="">Select Grade</option>
			</select>
		</div>
        <div id="register" style="text-align:center;">
	        <span>
	            <button class ="btn_blue" id="registerBtn">Register</button>
	        </span>
        </div>
    </div>
    
    <script>   	
	    $(function() {
	    	populateAssessmentPrograms();
	    });
	    
		function populateAssessmentPrograms() {
			var me = this;
			var apSelect = $('#assessmentPrograms'), optionText='';
			$('.messages').html('').hide();
			apSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
			
			$.ajax({
				url: 'getAssessmentProgramsForAutoRegistration.htm',
				dataType: 'json',
				type: "GET",
				success: function(assessmentPrograms) {				
					if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
						$.each(assessmentPrograms, function(i, assessmentProgram) {
							optionText = assessmentPrograms[i].programName;
							apSelect.append($('<option></option>').val(assessmentProgram.id).html(optionText));
						});
						if (assessmentPrograms.length == 1) {
							apSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
							apSelect.prop('disabled', true);
	    					$("#assessmentPrograms").trigger('change');
	    				}
					} else {
						$('body, html').animate({scrollTop:0}, 'slow');
						$('.messages').html(me.options.newreport_no_assessmentprogram).show();
					}
				}
			});
			
			$('#assessmentPrograms').change(function() {
				$('#testingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#assessments').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#testTypes').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				var assessmentProgramId = $('#assessmentPrograms').val();
				if (assessmentProgramId != 0) {
					$.ajax({
				        url: 'getTestingProgramsForAutoRegistration.htm',
				        data: {
				        	assessmentProgramId: assessmentProgramId
				        	},
				        dataType: 'json',
				        type: "GET",
				        success: function(testingPrograms) {
							$.each(testingPrograms, function(i, testingProgram) {
								$('#testingPrograms').append($('<option></option>').attr("value", testingProgram.id).text(testingProgram.programName));
							});
							
							if (testingPrograms.length == 1) {
								$("#testingPrograms option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#testingPrograms").trigger('change');
							}
				        }
					});
				}
			});

			$('#testingPrograms').change(function() {
				$('#assessments').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#testTypes').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				var testingProgramId = $('#testingPrograms').val();
				var assessmentProgramId = $('#assessmentPrograms').val();
				if (testingProgramId != 0  && assessmentProgramId != 0) {
					$.ajax({
				        url: 'getAssessmentsForAutoRegistration.htm',
				        data: {
				        	testingProgramId: testingProgramId,
				        	assessmentProgramId: assessmentProgramId
				        	},
				        dataType: 'json',
				        type: "GET",
				        success: function(assessments) {
							$.each(assessments, function(i, assessment) {
								$('#assessments').append($('<option></option>').attr("value", assessment.id).text(assessment.assessmentName));
							});
							
							if (assessments.length == 1) {
								$("#assessments option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#assessments").trigger('change');
							}
				        }
					});
				}
			});

			$('#assessments').change(function() {
				$('#testTypes').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				var assessmentId = $('#assessments').val();
				if (assessmentId != 0) {
					$.ajax({
				        url: 'getTestTypeByAssessmentId.htm',
				        data: {
				        	assessmentId: assessmentId
				        	},
				        dataType: 'json',
				        type: "GET",
				        success: function(testTypes) {
							$.each(testTypes, function(i, testType) {
								$('#testTypes').append($('<option></option>').attr("value", testType.id).text(testType.testTypeName));
							});
							
							if (testTypes.length == 1) {
								$("#testTypes option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#testTypes").trigger('change');
							}
				        }
					});
				}
			});
			
			$('#testTypes').change(function() {
				$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				var testTypeId = $('#testTypes').val();
				if (testTypeId != 0) {
					$.ajax({
				        url: 'getSubjectAreaByTestType.htm',
				        data: {
				        	testTypeId: testTypeId
				        	},
				        dataType: 'json',
				        type: "GET",
				        success: function(subjectAreas) {
							$.each(subjectAreas, function(i, subjectArea) {
								$('#courses').append($('<option></option>').attr("value", subjectArea.id).text(subjectArea.subjectAreaName));
							});
							
							if (subjectAreas.length == 1) {
								$("#courses option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#courses").trigger('change');
							}
				        }
					});
				}
			});			

			$('#courses').change(function() {
				$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				var subjectAreaId = $('#courses').val();
				var testTypeId = $('#testTypes').val();
				if (subjectAreaId != 0) {
					$.ajax({
				        url: 'getGradeCoursesByTestTypeIdSubjectAreaId.htm',
				        data: {
				        	subjectAreaId: subjectAreaId,
				        	testTypeId : testTypeId
				        	},
				        dataType: 'json',
				        type: "GET",
				        success: function(gradeCourses) {
							$.each(gradeCourses, function(i, gradeCourse) {
								$('#grades').append($('<option></option>').attr("value", gradeCourse.id).text(gradeCourse.name));
							});
							
							if (gradeCourses.length == 1) {
								$("#grades option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#grades").trigger('change');
							}
				        }
					});
				}
			});			
			
			$('#registerBtn').on("click",function() {
				var assessmentProgramId = $('#assessmentPrograms').val();
				var testingProgramId = $('#testingPrograms').val();
				var assessmentId = $('#assessments').val();
				var testTypeId = $('#testTypes').val();
				var subjectAreaId = $('#courses').val();
				var gradeCourseId = $('#grades').val();
				if (assessmentProgramId != 0 
						&& testingProgramId != 0 
						&& assessmentId != 0
						&& testTypeId != 0
						&& subjectAreaId != 0
						&& gradeCourseId != 0) {
					$.ajax({
				        url: 'processBatchRegistration.htm',
				        data: {
				        	testTypeId : testTypeId,
				        	subjectAreaId : subjectAreaId,
				        	gradeCourseId : gradeCourseId
				        	},
				        dataType: 'json',
				        type: "POST",
				        success: {}
					});
				}
			});			
		}

    </script>
</security:authorize>
