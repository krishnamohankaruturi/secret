<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.input-large {
  width: 250px !important;
}

#addStudentsForm .form-fields{
 margin : 0px 20px 5px 25px;
}
 .bcg_select {
	width: 250px !important;
	position: relative;
	vertical-align: middle;
	white-space: nowrap;
	height: 35px;
} 
.select2-selection--multiple .select2-search--inline{
 display: none;
}

.addStudentDialog .ui-dialog-title{
 color:black;
}
</style>

<div id="addStudentsFormDiv">
	<form style="border-style: solid; border-width: 3px;"
		id="addStudentsForm" name="addStudentsForm" class="form">

		<input type="hidden" name="stateSchoolId" id="stateSchoolId" value="" />

		<div id="addStudent_student">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">Student</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">
				<div id="legalFirstNameDiv" class="form-fields">
					<label for="legalFirstName" class=" field-label isrequired">Legal
						First Name:<span class="lbl-required">*</span>
					</label> <input type="text" id="legalFirstName" name="legalFirstName"
						class="input-large" maxlength="50" />
				</div>
				<div id="legalMiddleNameDiv" class="form-fields">
					<label for="legalMiddleName" class=" field-label isrequired">Legal
						Middle Name:</label> <input type="text" id="legalMiddleName"
						name="legalMiddleName" class="input-large" maxlength="50" />
				</div>
				<div id="legalLastNameDiv" class="form-fields">
					<label for="legalLastName" class=" field-label isrequired">Legal
						Last Name:<span class="lbl-required">*</span>
					</label> <input type="text" id="legalLastName" name="legalLastName"
						class="input-large" maxlength="50" />
				</div>
				<div id="generationDiv" class="form-fields">
					<label for="generation" class=" field-label isrequired">Generation:</label>
					<select id="generation" title="Generation" name="generation" class="bcg_select">
						<option value="">Select</option>
					</select>
				</div>
			</div>
		</div>
		<div id="addStudent_demographic">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">Demographic</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">
				<div id="gender" class="form-fields">
					<label for="genderSelect" class="field-label isrequired">Gender:<span
						class="lbl-required">*</span></label> <select id="genderSelect"
						class="bcg_select required" title="Gender" name="gender">
						<option value="">Select</option>
					</select>
				</div>
				<div id="dobDiv" class="form-fields">
					<label for="dobDate" class="field-label isrequired">Date of
						Birth:<span class="lbl-required">*</span>
					</label> <input id="dobDate" name="dobDate" class="input-large"
						placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
				</div>

				<div id="firstLanguageDiv" class="form-fields">
					<label for="firstLanguageSelect" class="field-label">First
						Language:</label> <select id="firstLanguageSelect" title="First Language" name="firstLanguage"
						class="bcg_select">
						<option value="">Select</option>
					</select>

				</div>

				<div id="comprehensiveRaceDiv" class="form-fields">
					<label for="comprehensiveRaceSelect" class="field-label isrequired">Comprehensive
						Race:<span class="lbl-required">*</span>
					</label> <select id="comprehensiveRaceSelect" title="Comprehensive Race" name="comprehensiveRace"
						class="bcg_select required">
						<option value="">Select</option>
					</select>

				</div>

				<div id="hispanicEthnicityDiv" class="form-fields">
					<label for="hispanicEthnicitySelect" class="field-label isrequired">Hispanic
						Ethnicity:<span class="lbl-required">*</span>
					</label> <select id="hispanicEthnicitySelect" title="Hispanic Ethnicity" name="hispanicEthnicity"
						class="bcg_select required">
						<option value="">Select</option>
					</select>
				</div>
			</div>
		</div>

		<div id="addStudent_profile">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">Profile</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">
				<div id="primaryDisabilityDiv" class="form-fields">
					<label for="primaryDisabilitySelect" class="field-label isrequired">Primary
						Disability Code:<span class="lbl-required">*</span>
					</label> <select id="primaryDisabilitySelect" title="Primary Disability Code" name="primaryDisability"
						class="bcg_select required">
						<option value="">Select</option>
					</select>

				</div>

				<div id="giftedStudentDiv" class="form-fields">
					<label for="giftedStudentSelect" class="field-label isrequired">Gifted
						Student:</label> <select id="giftedStudentSelect" title="Gifted Student" name="giftedStudent"
						class="bcg_select">
						<option value="">Select</option>
					</select>
				</div>

				<div id="assessmentProgramsStudentDiv" class="form-fields">
					<label for="assessmentProgramsStudent"
						class="isrequired field-label">Assessment Program:<span
						class="lbl-required">*</span></label> <select
						id="assessmentProgramSelectStudent" title="Assessment Program"
						name="assessmentProgramsStudent" class="bcg_select required"
						multiple="multiple">
					</select>
				</div>

				<div id="esolParticipationCodeDiv" class="form-fields">
					<label for="esolParticipationCodeSelect"
						class="field-label isrequired">ESOL Participation Code:<span
						class="lbl-required">*</span></label> <select
						id="esolParticipationCodeSelect" title="ESOL Participation Code" name="esolParticipationCode"
						class="bcg_select required">
						<option value="">Select</option>
					</select>
				</div>

				<div id="esolProgramEntryDateDiv" class="form-fields">
					<label for="esolProgramEntryDate" class="field-label">ESOL
						Entry Date:</label> <input id="esolProgramEntryDate"
						name="esolProgramEntryDate" class="input-large"
						placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
				</div>

				<div id="usaEntryDateDiv" class="form-fields">
					<label for="usaEntryDate" class="field-label">USA Entry
						Date:</label> <input id="usaEntryDate" name="usaEntryDate"
						class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
				</div>
			</div>
		</div>
		<div id="addStudentDemographicWarningMessagePopup" style="display:none;height:200px;">
			<div id="addStudentDemographicWarningMessage" style="width: 10%;float: left;"><img alt="Orange-Flag" src="images/orange-flag.png"></div>
			<div style="width: 90%;float: left;margin-left: 5%;margin-top: -4%;"><p><fmt:message key='label.settings.student.demographic.alert.message'/></p>
			</div>
			</div>

		<div id="addStudent_SchoolEnrollment">
			<div>
				<hr class="extraFilterSection" style="margin-top: 0%;">
				<label style="margin-left: 2%; font-weight: bold;">School
					Enrollment for <span id="currentSchoolYear"></span>
				</label>
				<hr class="extraFilterSection" style="margin-bottom: 0%;">
			</div>
			<div style="background-color: whitesmoke;">
			  
			  <div>
				
				<div id="addStudentCurrentDistrictDiv" class="form-fields">
					<label for="addStudentCurrentDistrictSelect" class="field-label">District:<span class="lbl-required">*</span></label>	
					<select id="addStudentDistrict" class="bcg_select required" title="District" name="addStudentDistrict">
						<option value="">Select</option>
					</select>					
				</div>	

				<div id="addStudentCurrentSchoolDiv" class="form-fields">
					<label for="addStudentCurrentSchoolSelect" class="field-label">School:<span class="lbl-required">*</span></label>	
					<select id="addStudentSchool" class="bcg_select required" title="School" name="addStudentSchool">
						<option value="">Select</option>
					</select>
				</div>			
				
				<div id="currentGradeDiv" class="form-fields">
					<label for="currentGradeSelect" class="field-label">Grade:<span
						class="lbl-required">*</span></label> <select id="currentGradeSelect"
						title="Grade" name="currentGrade" class="bcg_select currentGrade required">
						<option value="">Select</option>
					</select>
				</div>
			  </div>
			
			  <div id="accountabilityDistrictStudentDiv" class="form-fields">
					<label for="accountabilityDistrictStudent"
						class="field-label">Accountability District:</label> <select
						id="accountabilityDistrictSelectStudent" title="Accountability District"
						name="accountabilityDistrictStudent" class="bcg_select">
						<option value="">Select</option>
					</select>
				</div>

				<div id="accountabilitySchoolStudentDiv" class="form-fields">
					<label for="accountabilitySchoolStudent"
						class="field-label">Accountability School:</label> <select
						id="accountabilitySchoolSelectStudent" title="Accountability School"
						name="accountabilitySchoolStudent" class="bcg_select">
						<option value="">Select</option>
					</select>
				</div>

				<div id="addStudentLocalSchoolIdDiv" class="form-fields">
					<label for="localSchoolId" class="field-label isrequired">Local
						Student Identifier:</label> <input type="text" id="localSchoolId"
						name="localSchoolId" class="input-large" maxlength="20" />
				</div>

				<div id="stateEntryDateDiv" class="form-fields">
					<label for="stateEntryDate" class="field-label">State Entry
						Date:</label> <input id="stateEntryDate" name="stateEntryDate"
						class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
				</div>

				<div id="districtEntryDateDiv" class="form-fields">
					<label for="districtEntryDate" class="field-label isrequired">District
						Entry Date:</label> <input id="districtEntryDate" name="districtEntryDate"
						class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
				</div>

				<div id="schoolEntryDateDiv" class="form-fields">
					<label for="schoolEntryDate" class="field-label isrequired">School
						Entry Date:<span class="lbl-required">*</span>
					</label> <input id="schoolEntryDate" name="schoolEntryDate"
						class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy" />
				</div>

				<div id="residenceDistrictIdDiv" class="form-fields"
					style="display: none;">
					<label for="residenceDistrictId" class=" field-label">Attendance
						District Identifier:</label> <input type="text" id="residenceDistrictId"
						name="residenceDistrictId" disabled="disabled" class="input-large"
						maxlength="60" />
				</div>

				<div id="attendanceSchoolIdDiv" class="form-fields"
					style="display: none;">
					<label for="attendanceSchoolId" class=" field-label">Attendance
						School Identifier:</label> <input type="text" id="attendanceSchoolId"
						name="attendanceSchoolId" disabled="disabled" class="input-large"
						maxlength="60" />
				</div>

			</div>
		</div>

		<security:authorize access="hasAnyRole('PERM_STUDENTRECORD_CREATE')">
			<div class="btn-bar"
				style="margin-right: 0%; margin-left: 0%; background-color: whitesmoke;">
				<a class="panel_btn" href="#" id="createStudentsSave"
					style="margin-left: 75%;">Save</a> <a class="panel_btn" href="#"
					id="createStudentsReset">Reset</a>
			</div>
			<div id="addStudentprogressbar"></div>
		</security:authorize>
	</form>
</div>



<script type="text/javascript">
var gAddStudentsLoadOnce = false;
</script>