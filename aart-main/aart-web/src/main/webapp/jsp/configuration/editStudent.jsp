<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	#editStudentContent input.text {
		width: 96%;
	}
	
	#editStudentContent .form-fields {
		vertical-align:top;
		width:320px;
	}
	#editStudentGradeChangePopup .rightYesButton {
		left: 10px !important;
		position: absolute;
	}

	#editStudentGradeChangePopup .rightNoButton {
	}
	h3 {
    display: block;
    font-size: 1.17em;
    -webkit-margin-before: 1em;
    -webkit-margin-after: 1em;
    -webkit-margin-start: 0px;
    -webkit-margin-end: 0px;
    font-weight: bold;
}

hr {
    display: block;
    unicode-bidi: isolate;
    -webkit-margin-before: 0.5em;
    -webkit-margin-after: 0.5em;
    -webkit-margin-start: auto;
    -webkit-margin-end: auto;
    overflow: hidden;
    border-style: inset;
    border-width: 1px;
    width: 100%;
    height: 2px;
    background: #B8B8B8;
    border: 0;
    clear: both;

}
.select2-selection--multiple .select2-search--inline{
 display: none;
}

.editStudentDialog .ui-dialog-title{
 color:black;
}
</style>

<div id="editStudentContent" class="_bcg config">
	<label class="hidden error" id="messageEditStudent"></label>
	<br/>
	<form:form id="editStudentForm" commandName="student" class="form">
		<input id="studentIdForEdit" type="hidden" value="${studentId}" name="id"/>
		<div id="editStudentInfo">
			<h3>Student</h3>
			<hr/>
				<div class="form-fields">
					<label class="field-label" for="editStudentLegalFirstName">Legal First Name:<span class="lbl-required">*</span></label>
					<form:input type="text" class="text input-large" path="legalFirstName" id="editStudentLegalFirstName" title="Edit Student Legal First Name" />
				</div>
				<div class="form-fields">
					<label class="field-label" for="editStudentLegalMiddleName">Legal Middle Name:</label>
					<form:input type="text" class="text input-large" path="legalMiddleName" id="editStudentLegalMiddleName" title="Edit Student Legal Middle Name" />
				</div>
				<div class="form-fields">
					<label class="field-label" for="editStudentLegalLastName">Legal Last Name:<span class="lbl-required">*</span></label>
					<form:input type="text" class="text input-large" path="legalLastName" id="editStudentLegalLastName" title="Edit Student Legal Last Name" />
				</div>
				<div class="form-fields">
					<label class="field-label">Generation:</label>
					<form:select title = "Generation" path="generationCode" class = "bcg_select">
						<form:option value="" label="Select"/>
						<form:options items="${generationOptions}"/>				 	
					</form:select> 
				</div>
				
				<div class="form-fields">
					<label class="field-label">State Student ID:<span class="lbl-required">*</span></label>
					<form:input type="text" id="editStudentStateStudentId" class="text input-large" path="stateStudentIdentifier" title="State Student Identifier" />
				</div>
		</div>
		<div id="editStudentDemographic">
			<h3>Demographic</h3>
			<hr/>
				<div class="form-fields">
					<label class="field-label" for="editStudentGender">Gender:<span class="lbl-required">*</span></label>
					<form:select path="gender"  id="editStudentGender" name="gender" class="bcg_select gender required" title="Gender" >
						<form:option value="" label="Select"/>
						<form:options items="${genderOptions}"/>
					</form:select>
				</div>
				<div class="form-fields">
					<label class="field-label" for="editStudentDateOfBirth">Date of Birth:<span class="lbl-required">*</span></label>
					<fmt:formatDate value="${student.dateOfBirth}" type="date" pattern="MM/dd/yyyy" var="studentDateOfBirth"/>
					<form:input type="text" id="editStudentDateOfBirth" path="dateOfBirth" value="${studentDateOfBirth}" class="text input-large" placeholder="mm/dd/yyyy" title="Date Of Birth" />
				</div>
				<div class="form-fields">
					<label class="field-label">First Language:</label>
					<form:select path="firstLanguage" class = "bcg_select" title="First Language">
						<form:option value="" label="Select"/>
						<form:options items="${languageOptions}"/>
					</form:select>
				</div>
				<div class="form-fields">
					<label class="field-label">Comprehensive Race:<span class="lbl-required">*</span></label>
					<form:select path="comprehensiveRace" class="bcg_select required" title="Comprehensive Race">
						<form:option value="" label="Select"/>
						<form:options items="${comprehensiveRaceOptions}"/>
					</form:select>
				</div>
				<div class="form-fields">
					<label class="field-label">Hispanic Ethnicity:<span class="lbl-required">*</span></label>
					<form:select path="hispanicEthnicity" class="bcg_select required" title="Hispanic Ethnicity">
						<form:option value="" label="Select"/>
						<form:options items="${hispanicEthinicityOptions}" />						
					</form:select>
				</div>
		</div>
		<div id="editStudentProfile">
			<h3>Profile</h3>
			<hr/>
				<div class="form-fields">
					<label class="field-label">Primary Disability:<span class="lbl-required">*</span></label>
					<form:select path="primaryDisabilityCode" class="bcg_select required" title="Primary Disability">
						<form:option value="" label="Select"/>
						<form:options items="${primaryDisabilityOptions}" />
					</form:select>
				</div>
				
				
				<%-- <div class="form-fields">
					<label class="field-label">Assessment Program::<span class="lbl-required">*</span></label>
					<form:select path="studentAssessmentPrograms"  class="bcg_select required" multiple="multiple">
						<form:option value="" label="Select"/>
						 <form:options items="${fillAssessmentProgramComboMapOptions}"/>
						</form:select>
				</div> --%>
				
				<div id="assessmentProgramStudentEditDiv"  class="form-fields">
					<label for="assessmentProgramStudentEdit" class="isrequired field-label">Assessment Program:<span class="lbl-required">*</span></label>
					<select id="assessmentProgramStudentEdit" title="Assessment Program" name="assessmentProgramStudentEdit" class="bcg_select required" multiple="multiple">
					</select>
				</div>
				
				<div class="form-fields">
					<label class="field-label">Gifted Student:</label>
					<form:select path="giftedStudentEnrollment" class = "bcg_select" title="Gifted Student">
							<form:option value="" label="Select"/>
							<form:options items="${giftedStudentOptions}" />
					</form:select>
				</div>
				
					<br/>
				<div id="editStudentESOLDiv">
					<div class="form-fields">
						<label class="field-label">ESOL Participation:<span class="lbl-required">*</span></label>
						<form:select path="esolParticipationCode" class="bcg_select required" title="Esol Participation">
							<form:option value="" label="Select"/>
							<form:options items="${esolParticipationOptions}" />				 	
						</form:select>
					</div>
					<div id="editStudentESOLEntryDateDiv" class="form-fields">
						<label class="field-label">ESOL Entry Date:</label>
						<fmt:formatDate value="${student.esolProgramEntryDate}" type="date" pattern="MM/dd/yyyy" var="studentESOLEntryDate" />
						<form:input type="text" id="editStudentESOLEntryDate" path="esolProgramEntryDate" value="${studentESOLEntryDate}" class="text input-large" placeholder="mm/dd/yyyy" title="editStudentESOLEntryDate"/>
					</div>
					<div id="editStudentUSAEntryDateDiv" class="form-fields">
						<label class="field-label">USA Entry Date:</label>
						<fmt:formatDate value="${student.usaEntryDate}" type="date" pattern="MM/dd/yyyy" var="studentUSAEntryDate" />
						<form:input type="text" id="editStudentUSAEntryDate" path="usaEntryDate" value="${studentUSAEntryDate}" class="text input-large" placeholder="mm/dd/yyyy" title="editStudentUSAEntryDate"/>
					</div>
				</div>
			</div>
			
			<div id="editStudentDemographicWarningMessagePopup" style="display:none;height:200px;">
			<div id="editStudentDemographicWarningMessage" style="width: 10%;float: left;"><img alt="Orange-Flag" src="images/orange-flag.png"></div>
			<div style="width: 90%;float: left;margin-left: 5%;margin-top: -4%;"><p><fmt:message key='label.settings.student.demographic.alert.message'/></p>
			</div>
			</div>
			
			<div id="schoolEnrollment" >
				<h3>School Enrollment for Year ${student.currentSchoolYearEnrollment}</h3>
				<hr/>
				<%-- <div class="hidden">
					<div class="form-fields">
						<label class="field-label">CURRENT SCHOOL YEAR:<span class="lbl-required">*</span></label>
						<form:select path="currentSchoolYearEnrollment" class="bcg_select currentSchoolYear required">
							<form:option value="" label="Select"/>
							 <form:option value="${student.currentSchoolYearEnrollment}"/>
							</form:select>
					</div>
				</div> --%>
					
				<div id="districtDiv" class="form-fields">
           			<label for="district" class=" field-label isrequired">DISTRICT:</label>
            		&nbsp;${student.residenceDistrictIdentifier} - ${student.districtName}
        		</div>
        		
        		<div id="schoolDiv" class="form-fields">
           			<label for="school" class=" field-label isrequired">SCHOOL:</label>
            		&nbsp;${student.aypSchoolIdentifier} - ${student.schoolName}
            		<form:input type="text" class="text input-large" value="${student.stateId}" path="stateId" id="editStudentStateId" hidden="hidden"/>
        		</div>
        		
				<div class="form-fields">
					<label class="field-label">GRADE :<span class="lbl-required">*</span></label>
						<form:select path="currentGradeLevelEnrollment" name="currentGradeLevelEnrollment" class="bcg_select currentGrade required" title="Grade">
						<form:option value="" label="Select"/>
						<form:options items="${currentGradeOptions}"/>
						</form:select>
				</div>
				
				</br>
				
				<div id="accountabilityDistrictDiv" class="form-fields">
           			<label for="district" class=" field-label isrequired">ACCOUNTABILITY DISTRICT:</label>&nbsp;
            		<c:if test="${not empty student.accountabilityDistrictIdentifier && not empty student.accountabilityDistrictName}">
            			${student.accountabilityDistrictIdentifier} - ${student.accountabilityDistrictName}
            		</c:if>            		
        		</div>
        		
        		<div id="accountabilitySchoolDiv" class="form-fields">
           			<label for="school" class=" field-label isrequired">ACCOUNTABILITY SCHOOL:</label>&nbsp;
           			<c:if test="${not empty student.accountabilitySchoolIdentifier && not empty student.accountabilitySchoolName}">
           				${student.accountabilitySchoolIdentifier} - ${student.accountabilitySchoolName}
           			</c:if>
        		</div>
				
				<div id="editStudentLocalSchoolIdDiv" class="form-fields">
		            <label for="localStudentIdentifier" class="field-label isrequired">LOCAL STUDENT IDENTIFIER:</label>
		            <input type="text" id="localStudentIdentifier" name="localStudentIdentifier" class="text input-large" maxlength="20" value="${student.localStudentIdentifier}" />
		        </div>
		        
				</br>
				
				<div>
					<div class="form-fields">
						<label class="field-label" for="editStudentStateEntryDate">STATE ENTRY DATE:</label>
						 <fmt:formatDate value="${student.stateEntryDateEnrollment}" type="date" pattern="MM/dd/yyyy" var="studentStateEntryDateEnrollment"/>
						<form:input type="text" id="editStudentStateEntryDate" path="stateEntryDateEnrollment" value="${studentStateEntryDateEnrollment}" class="text input-large" placeholder="mm/dd/yyyy"/>
	 				</div>
				
					<div class="form-fields">
						<label class="field-label" for="editStudentDistrictEntryDate">DISTRICT ENTRY DATE:</label>
						 <fmt:formatDate value="${student.districtEntryDateEnrollment}" type="date" pattern="MM/dd/yyyy" var="studentDistrictEntryDateEnrollment"/>
						<form:input type="text" id="editStudentDistrictEntryDate" path="districtEntryDateEnrollment" value="${studentDistrictEntryDateEnrollment}" class="text input-large" placeholder="mm/dd/yyyy"/>
	 				</div>
				
					<div class="form-fields">
						<label class="field-label" for="editStudentSchoolEntryDate">SCHOOL ENTRY DATE:<span class="lbl-required">*</span></label>
						 <fmt:formatDate value="${student.schoolEntryDateEnrollment}" type="date" pattern="MM/dd/yyyy" var="studentSchoolEntryDateEnrollment"/>
						<form:input type="text" id="editStudentSchoolEntryDate" path="schoolEntryDateEnrollment" value="${studentSchoolEntryDateEnrollment}" class="text input-large" placeholder="mm/dd/yyyy"/>
	 				</div>
				</div>
				
			</div>
			<input type="hidden" id="DLMOnly" value="${isDLMOnly}"/>
			<input type="hidden" id="doesStudentHaveTestSessions" value="${doesStudentHaveTestSessions}"/>
			<input type="hidden" id="unchangedGradeLevel" value="${student.currentGradeLevelEnrollment}"/>
		</form:form>
	<br/>
	<button type="button" id="saveStudent" class="btn_blue save" style="width: 90px; margin-left: 24px;float:right;"> <fmt:message key="button.save"/> </button>
</div>

<div id="editStudentGradeChangePopup" title="Grade Change" class="hidden">
  	<div id="editStudentGradeChangePopupMessage" >
  		<p><fmt:message key='label.student.save.grade.message'/></p>
  		<p><fmt:message key='label.student.save.grade.yes'/></p>
  		<p><fmt:message key='label.student.save.grade.no'/></p>
  	</div>
</div>


<script type="text/javascript"
 src="<c:url value='/js/configuration/editStudent.js'/>"> </script>
