
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
#activeStudentContent input.text {
	width: 96%;
}

#activeStudentContent .form-fields {
	vertical-align: top;
	width: 320px;
}

#activeStudentForm {
	border-style: solid;
	border-width: 3px;
}

.activeStudentLabel {
	color: black !important;
	text-transform: unset !important;
	font-weight: bold;
}

.activeStudLabel {
	color: #0e76bc;
	font: bold 16px Arial, Helvetica, sans-serif;
}

.confirmationTitle {
	background: rgba(0, 0, 0, 0) url("./images/conformation.png") no-repeat
		scroll 165px 1px;
}
.activateStudentHeaderInfo{
	margin: 10px 0px 30px 0px;
	color: black !important;
	text-transform: unset !important;
	font-weight: bold;
}

.stateStudentIdentifierLabel{
	color: #0E76BC;
    display: block;
    font-size: 0.9em;
    margin: 8px 0 5px;
    float: left;
    text-transform: uppercase;
}

.activateStudentBtn{
	width: 8%;
    text-align: center;
}


</style>

<div id="activeStudentContent" class="_bcg config">

<label class="hidden error" id="messageActivateStudent" style="text-align: left;" ></label>
	<br/>

<div style="margin: 20px 0px 20px 0px;" >
	<label class="field-label stateStudentIdentifierLabel" for="activatestateStudentIdentifier">STATE STUDENT IDENTIFIER</label>&nbsp;
	<input type="text" id="activatestateStudentIdentifier" name="activatestateStudentIdentifier" class="text input-large"  style="width:110px;" maxlength="10" value="${stateStudentId}" readonly="readonly" />
</div>

<div class="activateStudentHeaderInfo" >Verify all information is accurate and complete. Select "Save" to enroll the student for the current school year.</div>

<form:form id="activeStudentForm" name="activeStudentForm" commandName="student" class="form">

	<input id="activeEnrollmentId" type="hidden" value="${enrollmentId}" name="activeEnrollmentId"/>
	<input id="activateStudentId" type="hidden" value="${studentId}" name="activateStudentId"/>
	<input type="hidden" id="activateschoolId" value="${schoolId}" />
	<input type="hidden" id="activatedistrictId" value="${districtId}" />
	<input type="hidden" id="activateaccountabilityDistrictId" value="${student.accountabilityDistrictId}" />
	<input type="hidden" id="activateaccountabilitySchoolId" value="${student.accountabilitySchoolId}" />

		<div id="activateStudent_student">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">Student</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>

			<div style="background-color: whitesmoke;">
				<div id="activateStudentLegalFirstNameDiv" class="form-fields">
					<label for="activateStudentLegalFirstName"
						class=" field-label isrequired">Legal First Name:<span
						class="lbl-required">*</span></label>
						<form:input type="text" class="text input-large" path="legalFirstName" id="activateStudentLegalFirstName" maxlength="50" title="Activate Student Legal First Name" />
				</div>

				<div id="activateStudentLegalMiddleNameDiv" class="form-fields">
					<label for="activateStudentLegalMiddleName"
						class=" field-label isrequired">Legal Middle Name:</label> 
						<form:input type="text" class="text input-large" path="legalMiddleName" id="activateStudentLegalMiddleName" maxlength="50" title="Activate Student Legal Middle Name" />
				</div>

				<div id="activateStudentLastNameDiv" class="form-fields">
					<label for="activateStudentLegalLastName"
						class=" field-label isrequired">Legal Last Name:<span
						class="lbl-required">*</span></label> 
						<form:input type="text" class="text input-large" path="legalLastName" id="activateStudentLegalLastName"  maxlength="50" title="Activate Student Legal Last Name" />
				</div>

			 	<div id="activateStudentGenerationDiv" class="form-fields">
					<label for="activateStudentGeneration"
						class=" field-label">Generation:</label> 
					<form:select id="activateStudentGeneration" path="generationCode" title="Generation" class="bcg_select" >
						<form:option value="" label="Select"/>
						<form:options items="${generationOptions}"/>
					</form:select>
				</div> 
			</div>
		</div>


		<div id="activateStudent_demographic">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">Demographic</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">
				<div id="activateStudentGender" class="form-fields">
					<label for="activateStudentGenderSelect" class="field-label isrequired">Gender:<span
						class="lbl-required">*</span></label> 	
					<form:select path="gender" id="activateStudentGenderSelect" title="Gender" name="gender" class="bcg_select gender required">
						<form:option value="" label="Select"/>
						<form:options items="${genderOptions}"/>
					</form:select>				
				</div>
				<div id="activateStudentDobDiv" class="form-fields">
					<label for="activateStudentDobDate" class="field-label isrequired">Date of
						Birth:<span class="lbl-required">*</span>
					</label> 
					<fmt:formatDate value="${student.dateOfBirth}" type="date" pattern="MM/dd/yyyy" var="studentDateOfBirth"/>
					<form:input type="text" id="activateStudentDobDate" path="dateOfBirth" value="${studentDateOfBirth}" class="text input-large" placeholder="mm/dd/yyyy" title="Date Of Birth" />
				</div>

				<div id="activateStudentFirstLanguageDiv" class="form-fields">
					<label for="activateStudentFirstLanguageSelect" class="field-label">First
						Language:</label>
					<form:select id="activateStudentFirstLanguage" title="First Language" path="firstLanguage" class="bcg_select" >
						<form:option value="" label="Select"/>
						<form:options items="${languageOptions}"/>
					</form:select>
				</div>

				<div id="activateStudentComprehensiveRaceDiv" class="form-fields">
					<label for="activateStudentComprehensiveRaceSelect" class="field-label isrequired">Comprehensive
						Race:<span class="lbl-required">*</span>
					</label>
					<form:select id="activateStudentComprehensiveRace" path="comprehensiveRace" title="Comprehensive Race" class="bcg_select required">
						<form:option value="" label="Select"/>
						<form:options items="${comprehensiveRaceOptions}"/>
					</form:select>
				</div>

				<div id="activateStudentHispanicEthnicityDiv" class="form-fields">
					<label for="activateStudentHispanicEthnicitySelect" class="field-label isrequired">Hispanic
						Ethnicity:<span class="lbl-required">*</span>
					</label>
					<form:select id="activateStudentHispanicEthnicity" title="Hispanic Ethnicity" path="hispanicEthnicity" class="bcg_select required">
						<form:option value="" label="Select"/>
						<form:options items="${hispanicEthinicityOptions}" />						
					</form:select>
				</div>
			</div>
		</div>

		<div id="activateStudent_profile">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">Profile</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">
				<div id="activateStudentPrimaryDisabilityDiv" class="form-fields">
					<label for="activateStudentPrimaryDisabilitySelect" class="field-label isrequired">Primary
						Disability Code:<span class="lbl-required">*</span>
					</label> 
					<form:select id="activateStudentPrimaryDisabilitySelect" title="Primary Disability Code" path="primaryDisabilityCode" class="bcg_select required">
						<form:option value="" label="Select"/>
						<form:options items="${primaryDisabilityOptions}" />
					</form:select>
				</div>

				<div id="activateStudentGiftedStudentDiv" class="form-fields">
					<label for="activateStudentGiftedStudentSelect" class="field-label isrequired">Gifted
						Student:</label> 
					<form:select path="giftedStudentEnrollment"  title="Gifted Student" class="bcg_select" >
							<form:option value="" label="Select"/>
							<form:options items="${giftedStudentOptions}" />
					</form:select>
				</div>

				<div id="activateStudentAssessmentProgramsStudentDiv" class="form-fields">
					<label for="activateStudentAssessmentProgramsStudent"
						class="isrequired field-label">Assessment Program:<span
						class="lbl-required">*</span></label> <select
						id="activateStudentAssessmentProgramSelectStudent" title="Assessment Program"
						name="assessmentProgramsStudent" class="bcg_select required"
						multiple="multiple">
					</select>
				</div>

				<div id="activateStudentEsolParticipationCodeDiv" class="form-fields">
					<label for="activateStudentEsolParticipationCodeSelect"
						class="field-label isrequired">ESOL Participation Code:<span
						class="lbl-required">*</span></label>						
						<form:select id="activateStudentEsolParticipationCodeSelect" title="ESOL Participation Code" path="esolParticipationCode" class="bcg_select required">
							<form:option value="" label="Select"/>
							<form:options items="${esolParticipationOptions}" />				 	
						</form:select>					
				</div>

				<div id="activateStudentEsolProgramEntryDateDiv" class="form-fields">
					<label for="activateStudentEsolProgramEntryDate" class="field-label">ESOL
						Entry Date:</label>
						<fmt:formatDate value="${student.esolProgramEntryDate}" type="date" pattern="MM/dd/yyyy" var="studentESOLEntryDate" />
						<form:input type="text" id="activateStudentEsolProgramEntryDate" path="esolProgramEntryDate" value="${studentESOLEntryDate}" class="text input-large" placeholder="mm/dd/yyyy"/>
				</div>

				<div id="activateStudentUsaEntryDateDiv" class="form-fields">
					<label for="activateStudentUsaEntryDate" class="field-label">USA Entry
						Date:</label>
						<fmt:formatDate value="${student.usaEntryDate}" type="date" pattern="MM/dd/yyyy" var="studentUSAEntryDate" />
						<form:input type="text" id="activateStudentUsaEntryDate" path="usaEntryDate" value="${studentUSAEntryDate}" class="text input-large" placeholder="mm/dd/yyyy"/>
				</div>
			</div>
		</div>

		<div id="activateStudent_SchoolEnrollment">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">School Enrollment for Year ${student.currentSchoolYearEnrollment}
				</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">

				<div id="activateStudentCurrentDistrictDiv" class="form-fields">
					<label for="activateStudentCurrentDistrictSelect" class="field-label">District:<span class="lbl-required">*</span></label>	
					<security:authorize access="hasRole('PERM_STUDENTRECORD_MODIFY')">	
							<select id="activeStudentDistrict" class="bcg_select required" name="activeDistrict" title="District">
									<option value="">Select</option>
							</select>						
					</security:authorize>
					
					<security:authorize access="!hasRole('PERM_STUDENTRECORD_MODIFY')">	
								<select id="activeStudentDistrict" class="bcg_select required" title="District" name="activeDistrict" disabled="disabled">
									<option value="">Select</option>
								</select>
					</security:authorize>
				</div>
				
				<div id="activateStudentCurrentSchoolDiv" class="form-fields">
					<label for="activateStudentCurrentSchoolSelect" class="field-label">School:<span class="lbl-required">*</span></label>	
				
					<security:authorize access="hasRole('PERM_STUDENTRECORD_MODIFY')">
									<select  id="activeStudentSchool" title="School" class="bcg_select required" name="activeStudentSchool">
										<option value="">Select</option>
									</select>
					</security:authorize>
					
					<security:authorize access="!hasRole('PERM_STUDENTRECORD_MODIFY')">
									<select id="activeStudentSchool" class="bcg_select required" title="School" name="activeStudentSchool" disabled="disabled">
										<option value="">Select</option>
									</select>
					</security:authorize>	
				</div>						
						
				<div id="activateStudentCurrentGradeDiv" class="form-fields">
					<label for="activateStudentCurrentGradeSelect" class="field-label">Grade:<span class="lbl-required">*</span></label>	
							
					<security:authorize access="hasRole('PERM_STUDENTRECORD_MODIFY')">
							<form:select id="activateStudentCurrentGradeSelect" title="Grade" path="currentGradeLevelEnrollment" name="currentGradeLevelEnrollment" class="bcg_select currentGrade required">
								<form:option value="" label="Select"/>
								<form:options items="${currentGradeOptions}"/>
							</form:select>
					</security:authorize>		
					
					<security:authorize access="!hasRole('PERM_STUDENTRECORD_MODIFY')">
							<form:select id="activateStudentCurrentGradeSelect" title="Grade" path="currentGradeLevelEnrollment" name="currentGradeLevelEnrollment" class="bcg_select currentGrade" disabled="disabled" >
								<form:option value="" label="Select"/>
								<form:options items="${currentGradeOptions}"/>
							</form:select>		
					</security:authorize>
					
				</div>
							
				<!-- <div id="activateStudentOrgFilter"></div> 

				<div id="activateStudentCurrentGradeDiv" class="form-fields">
					<label for="activateStudentCurrentGradeSelect" class="field-label">Grade:<span
						class="lbl-required">*</span></label> 
						<form:select id="activateStudentCurrentGradeSelect" path="currentGradeLevelEnrollment" name="currentGradeLevelEnrollment" class="bcg_select currentGrade required">
						<form:option value="" label="Select"/>
						<form:options items="${currentGradeOptions}"/>
						</form:select>
				</div>
				-->

				<div id="activateStudentAccountabilityDistrictStudentDiv" class="form-fields">
					<label for="activateStudentAccountabilityDistrictStudent"
						class="field-label">Accountability District:</label> <select title="Accountability District"
						id="activateStudentAccountabilityDistrictSelectStudent"
						name="accountabilityDistrictStudent" class="bcg_select">
						<option value="">Select</option>
					</select>
				</div>

				<div id="activateStudentAccountabilitySchoolStudentDiv" class="form-fields">
					<label for="activateStudentAccountabilitySchoolStudent"
						class="field-label">Accountability School:</label> <select title="Accountability School"
						id="activateStudentAccountabilitySchoolSelectStudent"
						name="accountabilitySchoolStudent" class="bcg_select">
						<option value="">Select</option>
					</select>
				</div>

				<div id="activateStudentLocalSchoolIdDiv" class="form-fields">
					<label for="activateStudentLocalSchoolId" class="field-label isrequired">Local
						Student Identifier:</label> 
						<form:input type="text" id="activateStudentLocalSchoolId"  maxlength="20"  path="localStudentIdentifier" class="text input-large" />
				</div>

				<div id="activateStudentStateEntryDateDiv" class="form-fields">
					<label for="activateStudentStateEntryDate" class="field-label">State Entry
						Date:</label> 
						<fmt:formatDate value="${student.stateEntryDateEnrollment}" type="date" pattern="MM/dd/yyyy" var="studentStateEntryDateEnrollment"/>
						<form:input type="text" id="activateStudentStateEntryDate" path="stateEntryDateEnrollment" value="${studentStateEntryDateEnrollment}" class="text input-large" placeholder="mm/dd/yyyy"/>
				</div>

				<div id="activateStudentDistrictEntryDateDiv" class="form-fields">
					<label for="activateStudentDistrictEntryDate" class="field-label isrequired">District
						Entry Date:</label> 
					    <fmt:formatDate value="${student.districtEntryDateEnrollment}" type="date" pattern="MM/dd/yyyy" var="studentDistrictEntryDateEnrollment"/>
						<form:input type="text" id="activateStudentDistrictEntryDate" path="districtEntryDateEnrollment" value="${studentDistrictEntryDateEnrollment}" class="text input-large" placeholder="mm/dd/yyyy"/>
				</div>

				<div id="activateStudentSchoolEntryDateDiv" class="form-fields">
					<label for="activateStudentSchoolEntryDate" class="field-label isrequired">School
						Entry Date:<span class="lbl-required">*</span></label>
						<fmt:formatDate value="${student.schoolEntryDateEnrollment}" type="date" pattern="MM/dd/yyyy" var="studentSchoolEntryDateEnrollment"/>
						<form:input type="text" id="activateStudentSchoolEntryDate" path="schoolEntryDateEnrollment" value="${studentSchoolEntryDateEnrollment}" class="text input-large" placeholder="mm/dd/yyyy"/>
				</div>

			</div>
		</div> 

		<security:authorize access="hasAnyRole('PERM_STUDENTRECORD_MODIFY')">
			<div class="btn-bar"
				style="margin-right: 0%; margin-left: 0%; background-color: whitesmoke;">
				<a class="panel_btn btn_blue activateStudentBtn" href="#" id="saveEnrollment"
					style="margin-left: 78%;margin-right: 5px;"><fmt:message key="button.save" /></a> <a
					class="panel_btn btn_blue activateStudentBtn" href="#" id="activateStudentsReset">Reset</a>
			</div>
		</security:authorize>

	</form:form>
</div>

<div id="activateStudentConfirmation" style="width: 510px !important;"
	title="Confirmation">
	<div>
		<p>Student has been successfully activated for the current school
			year.</p>
	</div>
</div>

<script type="text/javascript"
 src="<c:url value='/js/configuration/activateStudent.js'/>"> </script>


<script type="text/javascript">

$(document).ready(function(){
	activeStudentInit(${studentId});
});

var districtOrgSelect = $('#activateStudentAccountabilityDistrictSelectStudent'); 		 

$.ajax({
		url: 'getAccountabilityDistricts.htm',
		dataType: 'json',
		data: { 				
	    	orgType:'DT'
	    	},				
		type: "POST",
		success: function(districtOrgs) {	
			districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			districtOrgSelect.trigger('change.select2');
			if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
				$.each(districtOrgs, function(i, districtOrg) {
					optionText = districtOrgs[i].displayIdentifier+" - "+districtOrgs[i].organizationName;							
					orgId = districtOrg.id;
					var selectedDistrictId = $("#activateaccountabilityDistrictId").val();
					
					if(!isEmpty(selectedDistrictId) && orgId == selectedDistrictId){					
						districtOrgSelect.append($('<option selected="selected" ></option>').val(districtOrg.id).attr("code",districtOrg.displayIdentifier).html(optionText));
						activateStudentAccountabilitySchoolSelection();
					}else{
						districtOrgSelect.append($('<option></option>').val(districtOrg.id).attr("code",districtOrg.displayIdentifier).html(optionText));	
					}
				});
			} 
			$('#activateStudentAccountabilitySchoolSelectStudent, #activateStudentAccountabilityDistrictSelectStudent').trigger('change.select2');
		}
	});


//Accountability Distirct change function	
$('#activateStudentAccountabilityDistrictSelectStudent').on("change",function(){	
	activateStudentAccountabilitySchoolSelection();	
}); 
  
 
function activateStudentAccountabilitySchoolSelection(){
	$('#activateStudentAccountabilitySchoolSelectStudent').html("");
	$('#activateStudentAccountabilitySchoolSelectStudent').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		var districtOrgId = $('#activateStudentAccountabilityDistrictSelectStudent').val();
		if(!isEmpty(districtOrgId)){
		$.ajax({
			url: 'getAccountabilitySchools.htm',
			dataType: 'json',
			data: { 
				orgId : districtOrgId	
			},
			async:false,
			type: "GET",
			success: function(schoolOrgs) {
				$.each(schoolOrgs, function(i, schoolOrg) {
					var selectedSchoolId = $("#activateaccountabilitySchoolId").val();
					if(!isEmpty(selectedSchoolId) && schoolOrg.id == selectedSchoolId){
						$('#activateStudentAccountabilitySchoolSelectStudent').append($('<option selected="selected" ></option>').attr("value", schoolOrg.id).attr("code",schoolOrg.displayIdentifier).text(schoolOrg.displayIdentifier+" - "+schoolOrg.organizationName));
					}else{
						$('#activateStudentAccountabilitySchoolSelectStudent').append($('<option></option>').attr("value", schoolOrg.id).attr("code",schoolOrg.displayIdentifier).text(schoolOrg.displayIdentifier+" - "+schoolOrg.organizationName));
					}							
				});
				$('#activateStudentAccountabilitySchoolSelectStudent').trigger('change.select2');
			}
		});
	}
		
}


var activateStudentDistrictOrgSelect = $('#activeStudentDistrict');

var stateId = ${user.contractingOrganization.id};
if(globalUserLevel == 20)
	 {
	$.ajax({
		url: 'getChildOrgsWithParentForFilter.htm',
		dataType: 'json',
		data: {
			orgId : stateId,
	    	orgType:'DT'
	    	},				
		type: "POST",
		success: function(districtOrgs) {	
			activateStudentDistrictOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			activateStudentDistrictOrgSelect.trigger('change.select2');
			if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
				$.each(districtOrgs, function(i, districtOrg) {
					optionText = districtOrgs[i].displayIdentifier+" - "+districtOrgs[i].organizationName;
					orgId = districtOrg.id;
					var selectedDistrictId = ${districtId};
					
					if(orgId == selectedDistrictId){					
						activateStudentDistrictOrgSelect.append($('<option selected="selected"></option>').val(districtOrg.id).html(optionText));	
					
					activeStudentDistrictChange();
					
					}else{
						activateStudentDistrictOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));	
					}
				});
		   if (districtOrgs.length == 1) {
			   activateStudentDistrictOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');				
			} 
			} 
			$('#activeStudentSchool, #activeStudentDistrict').trigger('change.select2');
		}
	});
}

if(globalUserLevel == 50 || globalUserLevel == 70 ){
	 $.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
			data: {
				orgId : stateId,
	        	orgType:'DT',
	        	orgLevel: 50	
	    	},
			dataType: 'json',
			type: "GET",
			async: false,
			success: function(districtOrgs){
				
				activateStudentDistrictOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				activateStudentDistrictOrgSelect.trigger('change.select2');
				if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
					$.each(districtOrgs, function(i, districtOrg) {
						optionText = districtOrgs[i].displayIdentifier+" - "+districtOrgs[i].organizationName;
						orgId = districtOrg.id;
						var selectedDistrictId = ${districtId};
						
						if(orgId == selectedDistrictId){					
							activateStudentDistrictOrgSelect.append($('<option selected="selected"></option>').val(districtOrg.id).html(optionText));	
						activeStudentDistrictChange();
						}else{
							activateStudentDistrictOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));	
						}
					});
			   if (districtOrgs.length == 1) {
				   activateStudentDistrictOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');				
				} 
				}
				$('#activeStudentSchool, #activeStudentDistrict').trigger('change.select2');
				
				
			}
	 });
}
	
	activateStudentSchoolSelection();	
	
	function activateStudentSchoolSelection(){
		$('#activeStudentSchool').html("");
		$('#activeStudentSchool').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			var districtOrgId = $('#activeStudentDistrict').val();
			if(!isEmpty(districtOrgId)){
			$.ajax({
				url: 'getOrgsBasedOnUserContext.htm',
				dataType: 'json',
				data: {
					orgId : districtOrgId,
		        	orgType:'SCH',
		        	orgLevel: 70	
				},
				async:false,
				type: "GET",
				success: function(schoolOrgs) {
					$.each(schoolOrgs, function(i, schoolOrg) {
						var selectedSchoolId = ${schoolId};
						
						if(schoolOrg.id == selectedSchoolId){
						
						$('#activeStudentSchool').append($('<option selected="selected"></option>').attr("value", schoolOrg.id).text(schoolOrg.displayIdentifier+" - "+schoolOrg.organizationName));
						}else{
							$('#activeStudentSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.displayIdentifier+" - "+schoolOrg.organizationName));	
								}
					
					});		
					if (schoolOrgs.length == 1) {
						$("#activeStudentSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
						$("#activeStudentSchool").trigger('change');
					}
					$('#activeStudentSchool').trigger('change.select2');
				}
			});}
			
	}
	
	//distirct change function	
	$('#activeStudentDistrict').on("change",function(){
		activateStudentSchoolSelection();	
	});	
	
	function activeStudentDistrictChange (){
		activateStudentSchoolSelection();	
	}


</script>

