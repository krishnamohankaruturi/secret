<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<script type="text/javascript"
	src="<c:url value='/js/tools/studentMerge.js'/>"></script>

<div class="form-fields" id="mergeStudentStateContainer"
	style="margin-top: 7%">
	<label for="stateForStudentMerging" class="field-label" style="display: block;color: #0e76bc;">State:</label>
	<select id="stateForStudentMerging" class="bcg_select"
		name="stateForStudentMerging">
		<option value="">Select</option>
	</select>
	<span style="float: right; margin-right: 56%;">
		<button class="btn_blue" id="mergeStudentButton">Search</button>
	</span>
</div>
<style>
#mergeStudentDetailsL div {
    padding-bottom: 0%;
}
#mergeStudentDetailsR div {
    padding-bottom: 0%;
}
#mergeStudentDetailsR table {
    margin-left: 0%;
}
#studentsTestSessionGridL  tr td, #studentsTestSessionGridR  tr td{
    padding-bottom: 0px !important;
}

</style>

<div style="margin-left: 2%; margin-top: 2%;">
	<button class="btn_blue" id="mergeStudentNextButton">Next</button>
</div>

<div id="mergeStudentGridContainer" class="kite-table">
	<table class="responsive" id="mergeStudentGridTableId"></table>
	<div id="pmergeStudentGridTableId" style="width: auto;"></div>
</div>


<div id="viewMergeStudentDetailsDiv" style="display: none">
	<div id="mergeStudentDetailsSucess" style="text-align: left; color: green"></div>
	<div id="studentMergeDetails" style="width: 100%;">
		<form id="validateMergeStudentDetails">
			<div id="mergeStudentDetailsL" style="width: 48%; float: left;">
				<table>
					<tr>
						<td><label>StudentToRetain:</label> <input
							type="radio" id="StudentIdL" name="StudentDetails" /></td>
					</tr>
					<tr>
						<td><label>State ID:</label></td>
						<td><span id="stateStudentIdentifierL"></span></td>
					</tr>
					<tr>
						<td><label>FirstName:</label></td>
						<td><span id="firstNameL"></span></td>
					</tr>
					<tr>
						<td><label>LastName:</label></td>
						<td><span id="lastNameL"></span></td>
					</tr>
					<tr>
						<td><label>DateOfBirth:</label></td>
						<td><span id="dateOfBirthL"></span></td>
					</tr>
					<tr>
						<td><label>Gender:</label></td>
						<td><span id="genderL"></span></td>
					</tr>
					<tr>
						<td><label>CurrentSchoolYear:</label></td>
						<td><span id="currentSchoolYearL"></span></td>
					</tr>
					<tr>
						<td><label>AssessmentProgram:</label></td>
						<td><span id="assessmentProgramL"></span></td>
					</tr>
					<tr>
						<td><label>Hispanic Ethnicity:</label></td>
						<td><span id="hispanicethnicityL"></span></td>
					</tr>
					<tr>
						<td><label>Primary Disability Code:</label></td>
						<td><span id="primaryDisabilityCodeL"></span></td>
					</tr>
					<tr>
						<td><label>Comprehensive Race:</label></td>
						<td><span id="comprehensiveraceL"></span></td>
					</tr>
					<tr>
						<td><label>Current Grade:</label></td>
						<td><span id="currentGradeL"></span></td>
					</tr>
					<tr>
						<td><label>PNP Settings to retain:</label></td>
						<td><input type="radio" id="checkPNPOptionL" 
							name="pnpOptionCheck" /> <span id="accessProfileL"></span></td>
					</tr>
					<tr>
						<td><label>First Contact Survey to retain:</label></td>
						<td><input type="radio" id="checkFcsL" name="fcsOptionCheck" />
							<span id="firstContactSurveyL"></span></td>
					</tr>
					<tr>
						<td><label>RosterName: </label></td>
						<td><input type="radio" id="checkRosterL"
							name="rosterOptionCheck" /> <span id="rosterL"> </span></td>
					</tr>
					<tr>
						<td><label>RosterId: </label></td>
						<td><span id="rosterIdL"> </span></td>
					</tr>
					<tr>
						<td><label>EducatorName: </label></td>
						<td><span id="educatorNameL"> </span></td>
					</tr>
				</table>
				<div style="width:100%; overflow:auto;">
	            		<table id="studentsTestSessionGridL" style=" width:100%;"></table>
	            		<div id="studentsTestSessionGridPagerL" style="width: auto;"></div></div>
			</div>
			
			<div id="mergeStudentDetailsR" style="width: 48%; float: left;">
				<table>
					<tr>
						<td><label>StudentToRetain:</label>
						<input type="radio" id="StudentIdR"
								name="StudentDetails" /></td>
					</tr>
					<tr>
						<td><label>State ID:</label></td>
						<td><span id="stateStudentIdentifierR"></span></td>
					</tr>
					<tr>
						<td><label>FirstName:</label></td>
						<td><span id="firstNameR"></span></td>
					</tr>
					<tr>
						<td><label>LastName:</label></td>
						<td><span id="lastNameR"></span></td>
					</tr>
					<tr>
						<td><label>DateOfBirth:</label></td>
						<td><span id="dateOfBirthR"></span></td>
					</tr>
					<tr>
						<td><label>Gender:</label></td>
						<td><span id="genderR"></span></td>
					</tr>
					<tr>
						<td><label>CurrentSchoolYear:</label></td>
						<td><span id="currentSchoolYearR"></span></td>
					</tr>
					<tr>
						<td><label>AssessmentProgram:</label></td>
						<td><span id="assessmentProgramR"></span></td>
					</tr>
					<tr>
						<td><label>Hispanic Ethnicity:</label></td>
						<td><span id="hispanicethnicityR"></span></td>
					</tr>
					<tr>
						<td><label>Primary Disability Code:</label></td>
						<td><span id="primaryDisabilityCodeR"></span></td>
					</tr>
					<tr>
						<td><label>Comprehensive Race:</label></td>
						<td><span id="comprehensiveraceR"></span></td>
					</tr>
					<tr>
						<td><label>Current Grade:</label></td>
						<td><span id="currentGradeR"></span></td>
					</tr>
					<tr>
						<td><label>PNP Settings to retain:</label></td>
						<td><input type="radio" id="checkPNPOptionR"
							name="pnpOptionCheck" /> <span id="accessProfileR"></span></td>
					</tr>
					<tr>
						<td><label>First Contact Survey to retain:</label></td>
						<td><input type="radio" id="checkFcsR" name="fcsOptionCheck" />
							<span id="firstContactSurveyR"></span></td>
					</tr>
					<tr>
						<td><label>RosterName: </label></td>
						<td><input type="radio" id="checkRosterR"
							name="rosterOptionCheck" /> <span id="rosterR"> </span></td>
					</tr>
					<tr>
						<td><label>RosterId: </label></td>
						<td><span id="rosterIdR"> </span></td>
					</tr>
					<tr>
						<td><label>EducatorName: </label></td>
						<td><span id="educatorNameR"> </span></td>
					</tr>
				</table>
					<div style="width:100%;  margin-left:10px; overflow:auto;">
	            		<table id="studentsTestSessionGridR" style="width:100%;"></table>
	            		<div id="studentsTestSessionGridPagerR" style="width: auto;"></div>   </div>    
				
			</div>
			<div width="100%">
				<input type="button" value="Merge" class="btn_blue" id="mergeButton">
			</div>
		</form>
	</div>
</div>

<div id="firstContactViewDiv"></div>
<div id="MergeStudentAccessProfileDiv"></div>
<div id="viewStudentDetailsDiv"></div>
<div id="editMergeStudentDiv"></div>
<div id="accessProfileDiv" ></div>

<script>
students_merge_tab();
var viewFirstContactSurvey = false;
<security:authorize access="hasRole('VIEW_FIRST_CONTACT_SURVEY')">
		viewFirstContactSurvey = true; 
</security:authorize>

var editFirstContactSurvey = false;
<security:authorize access="hasRole('EDIT_FIRST_CONTACT_SURVEY')">
		editFirstContactSurvey = true; 
</security:authorize>
</script>