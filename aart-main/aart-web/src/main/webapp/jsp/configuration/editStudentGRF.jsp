<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>

 .editGRFStudentInfo{	
	border: 1px solid #B8B8B8;
 }	
 
 .editStudentInformLabel{
 	text-align: center;
 	font-size: 25px;
 	color: #0e76bc;
 	margin-bottom: 20px;
 }
 .grfSaveBtn{
 	width: 90px;
 	margin-right: 100px !important;
 	float: right;
 }
 .invalidateDiv{
    width: 50%;
 }
 .grfSearchBtn{
 	border-radius: 4px; 
 	border: 0;
 	display: inline-block;
 	padding: 10px 20px;
 	color: white !important;
	text-decoration: none;
	font-size: 1em;
	font-weight: 300;
	line-height: 20px; 
	transition: all .3s ease-in-out;
	margin-left: 30px; 
	background: #0e76bc none no-repeat center center;
 }
 .studentIdErr{
 	margin-left: 90px;
    float: left;
    color: red;
}
.notValidRowId{
	margin-left: 90px;
    float: left;
    color: red;
}
.notValidRowIdStateStudentId{
	margin-left: 90px;
    float: left;
    color: red;
}
.searchStudentGrfMand{
	margin-left: 90px;
    float: left;
    color: red;
}
/* .invalidSubject{
	margin-left: 90px;
    float: left;
    color: red;
} */
.txtFields, .text{
    height: 24px;
    line-height: 24px;
    width: 203px;
}
 .div-col-3{
  	float: left;
  	width:28.33% !important; 
 }
 
 .div-col-1{
  	float: left;
  	width:100% !important; 
 }
 .div-header{
 	float:left;
 	width:100%;
 }

 .div-txtbx-width-1{
 	float: left;
 	width: 38%;
 }
 .div-txt-width-2{
 	line-height: 30px;
 	float: left;
 	width: 62%;
 }
 .editStudentGrfInfoSuccess{
 	font-size: 20px;
    color: green;
    padding-bottom: 10px;
    display:none;
 }
 .editStudentGrfInfoError{
 	font-size: 20px;
    padding-bottom: 10px;
    display:none;
 }
 .curpointer{
 	cursor: pointer;
 }
 .text-header{
 	margin-left:22px;
 }
 .editGRFStudentContent{
 	width:98%;
 	float: left;
 }
 
</style> 



<div id="editGRFStudentInformation">
	<div class="editStudentInformLabel">
		Edit GRF Student Information<br />
	</div>
	<br /> 
	
	<label style="float: left; margin-right: 50px; color: #0e76bc; margin-left: 90px;">UNIQUE ROW IDENTIFIER<span class="lbl-required">*</span><br />
	<br />
	
	
	<input type="text" id="manageGRFUniqueRowIdentifier" name="manageGRFUniqueRowIdentifier" value="<c:out value="${searchCriteriaMap['uniqueRowIdentifier']}"/>" style="float: left;" class="txtFields editStudentNumeric" />
	</label> <label style="float: left; margin-right: 50px; color: #0e76bc;"><br />
	<br />OR</label> <label	style="float: left; margin-right: 50px; color: #0e76bc;">STATE STUDENT ID<span class="lbl-required">*</span><br />
	<br />
	<input type="text" id="manageGRFStateStudentID"	name="manageGRFStateStudentID" style="float: left;" class="txtFields"  value="<c:out value="${searchCriteriaMap['stateStudentIdentifier']}"/>" maxlength="50"/>
	</label> 
	<label	style="float: left; margin-right: 42px; margin-left: -42px; color: #0e76bc;"></label>
	<label style="float: left; margin-right: 25px; color: #0e76bc;">SUBJECT<span class="lbl-required">*</span></label><br />
	<br /> 
	
	<div class="form-fields" style="float: left;">
					<select id="manageGRFSubject" name="manageGRFSubject" class="bcg_select">
						 <option value="">Select</option> 
			     		 <c:if test="${fn:length(subjectLists) > 0 }">
							<c:forEach items="${subjectLists}" var="subject" varStatus="index">
								<option value="${subject.id }" <c:if test="${subject.id == searchCriteriaMap['subjectId']}">selected</c:if> >${subject.name}</option>
							</c:forEach>					
						</c:if>
					</select>					
	</div>
	<div style="float: left;margin-top: -4px;" >
		<button	class="grfSearchBtn curpointer" id="searchEditGRFStudent" onclick="searchButtonClick()">Search</button>
	</div>
	
</div>

	<input type="hidden" id="uploadGrfId" value="${studentGrf.id}" />
	
<c:if test="${empty studentGrf}">
	<div id="studentIdErr" class="error studentIdErr" style="display: none;">This State Student ID and subject combination does not exist.</div>
	<div id="notValidRowId" class="error notValidRowId" style="display: none;">This Unique Row Identifier does not exist.</div>
	<div id="notValidRowIdStateStudentId" class="error notValidRowIdStateStudentId" style="display: none;">This Unique Row Identifier or State Student ID and subject combination does not exist.</div>
</c:if>
<div id="searchStudentGrfMand" class="error searchStudentGrfMand" style="display: none;">Unique Row Identifier or State Student ID and Subject is mandatory.</div>
<!-- <div id="invalidSubject" class="error invalidSubject" style="display: none;">Selected student has no subject, cannot edit.</div> -->

<c:if test="${not empty studentGrf}">
 
<div id="editGRFStudentContent" class="_bcg config hidden editGRFStudentContent" >
	<label class="hidden error" id="messageEditStudentGrf"></label> <br />
	
	<form:form id="editGRFStudentForm" commandName="studentGrf" class="form" >
		
	 <div id="editStudentGrfInfoSuccess" class="editStudentGrfInfoSuccess" >Update saved</div>
	 <div id="editStudentGrfInfoError" class="error editStudentGrfInfoError" ></div>
				
	<input type="hidden" id="originalStateStudentId" value="${studentGrf.stateStudentIdentifier}" />
	<input type="hidden" id="originalCurrentGradeLevel" value="${studentGrf.currentGradelevel}" />	
	<input type="hidden" id="grfStudentId" name="grfStudentId" value="${studentGrf.studentId}" />
	<input type="hidden" id="grfSubjectId" name="grfSubjectId" value="${studentGrf.subjectId}" />
	<input type="hidden" id="grfReportYear" name="grfReportYear" value="${studentGrf.reportYear}" />	

			
		<div id="editGRFStudentInfo" class="div-header editGRFStudentInfo" >
		
			<div class="invalidateDiv" ><h3 class="text-header" style="float: left;">STUDENT</h3></div>
		
			<div style="float: left;" >
				<label class="field-label"
					style="float: left; margin-top: 20px; margin-left: 365px;" for="invalidate">INVALIDATE:<span
					class="lbl-required">*</span></label> <label class="field-label"
					style="float: left; margin-top: 22px;">
					<input type="radio"	name="invalidate" title="Yes" id="invalidate" value="1" style="float: left; margin-top: -2px;"
					   <c:if test="${studentGrf.invalidationCode == 1}">
                                checked="true" 
                            </c:if> 
					><div style="margin-top: -4px;float: left;">&nbsp;Yes&nbsp;&nbsp;</div></label>
				<label class="field-label" style="float: left; margin-top: 22px;">
				<input type="radio" title="No" name="invalidate" value="0"
					style="float: left; margin-top: -2px;"
					 <c:if test="${studentGrf.invalidationCode == 0}">
                                checked="true" 
                     </c:if> 
					><div style="margin-top: -4px;float: left;">&nbsp;No</div></label>			
			</div>
						
			<div class="div-header" >
			
				<div class="form-fields div-col-3" >
					<label class="field-label" for="legalFirstName">LEGAL FIRST NAME:<span
						class="lbl-required">*</span></label>
					<form:input type="text" class="text" path="legalFirstName" />
				</div>
				<div class="form-fields div-col-3" >
					<label class="field-label" for="legalMiddleName">LEGAL MIDDLE NAME:</label>
					<form:input type="text" class="text" path="legalMiddleName" />
				</div>
				<div class="form-fields div-col-3" >
					<label class="field-label" for="legalLastName">LEGAL LAST NAME:<span
						class="lbl-required">*</span></label>
					<form:input type="text" class="text" path="legalLastName" />
				</div>
			
			</div>
			
			<div class="div-header" >
				<div class="form-fields div-col-3" >
					<label class="field-label">GENERATION:</label>
					<form:select path="generationCode" title="Generation">
						<form:option value="" label="Select" />
						<form:option value="JR" label="Jr" />
						<form:option value="SR" label="Sr" />
						<form:option value="II" label="II" />
						<form:option value="III" label="III" />
						<form:option value="IV" label="IV" />
						<form:option value="V" label="V" />
						<form:option value="VI" label="VI" />
						<form:option value="VII" label="VII" />
						<form:option value="VIII" label="VIII" />
						<form:option value="IX" label="IX" />
					</form:select>
				</div>
				<div class="form-fields div-col-3" >
					<label class="field-label" for="editStudentGrfDateOfBirth">DATE OF BIRTH:<!-- <span
						class="lbl-required">*</span> --></label>
					<fmt:formatDate value="${studentGrf.dateOfBirth}" type="date"
						pattern="MM/dd/yyyy" var="studentDateOfBirth" />
					<form:input type="text" id="editStudentGrfDateOfBirth" 
						path="dateOfBirth" value="${studentDateOfBirth}" class="text"
						placeholder="mm/dd/yyyy" />
				</div>
				<div class="form-fields div-col-3">
					<label class="field-label" for="stateStudentIdentifier">STATE STUDENT ID:<span
						class="lbl-required">*</span></label>
					<form:input type="text" class="text" id="stateStudentIdentifier" path="stateStudentIdentifier" />
				</div>
				
				<hr />
		</div>
		
		<div id="editGRFStudentDemographic" class="div-header"  >
			<h3 class="text-header" >DEMOGRAPHIC</h3>
			
			<div class="div-header" >
				<div class="form-fields div-col-3" >
					<label class="field-label" for="gender">GENDER:<!-- <span class="lbl-required">*</span> --></label>
					<form:select path="gender" id="gender" name="gender" title="Gender">
						<form:option value="" label="Select" />
						<form:option value="Female" label="Female" />
						<form:option value="Male" label="Male" />
					</form:select>
				</div>
				<div class="form-fields div-col-3" >
					<label class="field-label" for="comprehensiveRace">COMPREHENSIVE RACE:<!-- <span
						class="lbl-required">*</span> --></label>
					<form:select path="comprehensiveRace" title="Comprehensive Race">
						<form:option value="" label="Select" />
						<form:options items="${comprehensiveRaceOptions}" />
					</form:select>
				</div>
				<div class="form-fields div-col-3">
					<label class="field-label" for="hispanicEthnicity">HISPANIC ETHNICITY:</label>
					<form:select path="hispanicEthnicity" title="Hispanic Ethnicity">
					    <form:option value="" label="Select" />
						<form:option value="0" label="0 - Yes" />
						<form:option value="1" label="1 - No" />
					</form:select>
				</div>
			</div>
			
			<div class="div-header" >
				<div class="form-fields div-col-1">
					<label class="field-label" for="firstLanguage">FIRST LANGUAGE:</label>
					<form:select path="firstLanguage" title="First Language">
						<form:option value="" label="Select" />
						<form:options items="${languageOptions}" />
					</form:select>
				</div>
			</div>
			<hr />
		</div>
		<div id="editGRFStudentProfile" class="div-header" >
			<h3 class="text-header" >PROFILE</h3>
			
			<div class="div-header" >
				<div class="form-fields div-col-3">
					<label class="field-label" for="primaryDisabilityCode">PRIMARY DISABILITY:<!-- <span
						class="lbl-required">*</span> --></label>
					<form:select path="primaryDisabilityCode" title="Primary Disability">
						<form:option value="" label="Select" />
						<form:options items="${primaryDisabilityOptions}" />
					</form:select>
				</div>
				<div class="form-fields div-col-3">
					<label class="field-label" for="esolParticipationCode">ESOL PARTICIPATION:<!-- <span
						class="lbl-required">*</span> --></label>
					<form:select path="esolParticipationCode" title="ESOL Participation">
						<form:option value="" label="Select" />
						<form:option value="0" label="0 - Not Eligible" />
						<form:option value="1" label="1 - Title III Funded" />
						<form:option value="2" label="2 - State ESOL/Bilingual Funded" />
						<form:option value="3" label="3 - Title III and State ESOL/Bilingual Funded" />
						<form:option value="4" label="4 - Monitored ESOL Student" />
						<form:option value="5" label="5 - Eligible but not supported" />
						<form:option value="6" label="6 - ESOL but not funded" />
					</form:select>
				</div>
				<div class="form-fields div-col-3"></div>
			</div>
			<hr />
		</div>
		
		
		<div id="schoolEnrollmentGRF" style="float: left;">
			<div class="div-header" >
				<div style="width: 50% !important;float: left;">
					<h3 style="float: left;" class="text-header" >SCHOOL AND ROSTER (for reporting purposes)</h3>
				</div>
				<div style="width: 50% !important;float: left;padding-top: 22px;" >
					<label style="color: #0e76bc;">SUBJECT:</label>
					<c:if test="${empty studentGrf.subject}">
						<label>No subject</label>
					</c:if>
					<c:if test="${not empty studentGrf.subject}">		
						<label>${studentGrf.subject}</label>
					</c:if>
				</div>
			</div>
			
			
			<div class="div-header" >
				<div class="form-fields  div-col-3" >
					<label class="field-label">CURRENT GRADE :<span
						class="lbl-required">*</span></label>
					<form:select path="currentGradelevel" name="currentGradelevel" class="bcg_select currentgradelevel required">
						<form:option value="" label="Select" />
					 <form:options items="${currentGradeOptions}" />					
					</form:select>
				</div>
				<div class="form-fields  div-col-3" >
					<label class="field-label">ATTENDANCE SCHOOL PROGRAM IDENTIFIER:</label>
					<div class="div-txtbx-width-1" >
						<form:input type="text" class="text" path="attendanceSchoolProgramIdentifier" title="Attendance School Program Identifier" style="width: 100px" />
					</div>
					<div class="div-txt-width-2" >
						<label>${studentGrf.attendanceSchoolProgramName}</label>
					</div>					
				</div>
				<div class="form-fields  div-col-3" >
					<label class="field-label" for="aypSchoolIdentifier">AYP SCHOOL:</label>
					<div class="div-txtbx-width-1" >
						<form:input type="text" class="text" path="aypSchoolIdentifier" style="width: 100px" />
					</div>
					<div class="div-txt-width-2" >
						<label style="">${studentGrf.aypSchoolName}</label>
					</div>
				</div>
			</div>
			<div class="div-header" >
				<div class="form-fields div-col-3">
					<label class="field-label" for="schoolIdentifier">SCHOOL CODE:<span class="lbl-required">*</span></label>
					<div class="div-txtbx-width-1" >
						<form:input type="text" class="text" path="schoolIdentifier" style="width: 100px" />
					</div>
					<div class="div-txt-width-2" >
						<label>${studentGrf.schoolName}</label>
					</div>
				</div>
				<div class="form-fields" style="width:63%;">
					<label class="field-label" for="residenceDistrictIdentifier" for="residenceDistrictIdentifier">DISTRICT CODE:<span class="lbl-required">*</span></label>
					<div style="float:left;width:17%;" >
						<form:input type="text" class="text" path="residenceDistrictIdentifier" style="width: 100px" />
					</div>
					<div style="line-height: 30px;float: left;width: 83%;" >
						<label>${studentGrf.districtName}</label>
					</div>
				</div>
			</div>
			<div class="div-header" >
				<div class="form-fields div-col-3" >
					<label class="field-label" for="editStudentGrfInformationSchoolEntryDate">SCHOOL ENTRY DATE:</label>
					<fmt:formatDate value="${studentGrf.schoolEntryDate}"
						type="date" pattern="MM/dd/yyyy" var="schoolEntryDateEnrollment" />
					<form:input type="text" id="editStudentGrfInformationSchoolEntryDate"
						path="schoolEntryDate"
						value="${schoolEntryDateEnrollment}" class="text"
						placeholder="mm/dd/yyyy" />
				</div>
				<div class="form-fields div-col-3" >
					<label class="field-label" for="editStudentGrfInformationDistrictEntryDate">DISTRICT ENTRY DATE:</label>
					<fmt:formatDate value="${studentGrf.districtEntryDate}"
						type="date" pattern="MM/dd/yyyy" var="districtEntryDateEnrollment" />
					<form:input type="text"
						id="editStudentGrfInformationDistrictEntryDate"
						path="districtEntryDate"
						value="${districtEntryDateEnrollment}" class="text"
						placeholder="mm/dd/yyyy" />
				</div>
				<div class="form-fields div-col-3" >
					<label class="field-label" for="editStudentGrfInformationStateEntryDate">STATE ENTRY DATE:</label>
					<fmt:formatDate value="${studentGrf.stateEntryDate}"
						type="date" pattern="MM/dd/yyyy" var="stateEntryDateEnrollment" />
					<form:input type="text" id="editStudentGrfInformationStateEntryDate"
						path="stateEntryDate"
						value="${stateEntryDateEnrollment}" class="text"
						placeholder="mm/dd/yyyy" />
				</div>
			</div>
			<div class="div-header" >
				<div class="form-fields div-col-1" >
					<label class="field-label" for="educatorIdentifier">EDUCATOR IDENTIFIER:<span class="lbl-required">*</span></label>
					<div style="float:left;width:20.3%;" >
						<form:input type="text" class="text" path="educatorIdentifier" />
					</div>
					<div style="line-height: 30px;float: left;width: 78%;" >
						<label>${studentGrf.educatorFirstName} ${studentGrf.educatorLastName}</label>
					</div>	
				</div>
			</div>
		</div>
		
		<c:if test="${studentGrf.stateCode eq 'IA'}">
			<div class="div-header" >
				<h3 style="float: left;" class="text-header" >STATE SPECIFIC</h3>
				<hr />
				
				<div class="div-header" >
					<div class="form-fields div-col-3" style="float: left; width: 250px;">
						<label class="field-label">EXIT WITHDRAWAL DATE:</label>
						<fmt:formatDate value="${studentGrf.exitWithdrawalDate}"
							type="date" pattern="MM/dd/yyyy" var="exitWithdrawalDateEnrollment" />
						<form:input type="text"
							id="editStudentGrfInformationExitWithdrawalDate"
							path="exitWithdrawalDate"
							value="${exitWithdrawalDateEnrollment}" class="text"
							placeholder="mm/dd/yyyy" />
					</div>
					<div class="form-fields div-col-3">				
						<label class="field-label">EXIT CODE:</label>
							<form:select path="exitWithdrawalType" name="exitWithdrawalType" class="bcg_select">
								<form:option value="" label="Select" />
								<form:option value="1" label="01- Transfer to public school, same district" />
								<form:option value="2" label="02- Transfer to public school, different district, same state" />
								<form:option value="3" label="03- Transfer to public school, different state" />
								<form:option value="4" label="04- Transfer to an accredited private school" />
								<form:option value="5" label="05- Transfer to a non-accredited private school" />
								<form:option value="6" label="06- Transfer to home schooling" />
								<form:option value="7" label="07- Matriculation to another school" />
								<form:option value="8" label="08- Graduated with regular diploma" />
								<form:option value="9" label="09- Completed school with other credentials (e.g., district-awarded GED)" />
								<form:option value="10" label="10- Student death" />
								<form:option value="11" label="11- Student illness" />
								<form:option value="12" label="12- Student expulsion (or long-term suspension)" />
								<form:option value="13" label="13- Reached maximum age for services" />
								<form:option value="14" label="14- Discontinued schooling" />
								<form:option value="15" label="15- Transfer to accredited or non-accredited juvenile correctional facility—educational services provided" />
								<form:option value="16" label="16- Moved within the United States, not known to be enrolled in school" />
								<form:option value="17" label="17- Unknown educational services provided" />
								<form:option value="18" label="18- Student data claimed in error/never attended" />
								<form:option value="19" label="19- Transfer to an adult education facility (i.e., for GED completion)" />
								<form:option value="20" label="20- Transfer to a juvenile or adult correctional facility—no" />
								<form:option value="21" label="21- Student moved to another country, may or may not be continuing enrollment" />
								<form:option value="30" label=" 30- Student no longer meets eligibility criteria for alternate assessment" />
								<form:option value="98" label="98- Unresolved exit" />
							</form:select>							
					</div>
					<div class="form-fields div-col-3"></div>
				</div>
			</div>		
		</c:if>		
      	<c:if test="${studentGrf.stateCode eq 'NY'}">
	        <div class="div-header" >
				<h3 style="float: left;" class="text-header" >STATE SPECIFIC</h3>
				<hr />
				<div class="form-fields div-col-3">
					<label class="field-label">LOCAL STUDENT IDENTIFIER:</label>
					<form:input type="text" class="text" id="localStudentIdentifier" path="localStudentIdentifier" />
				</div>
			</div>	
	   </c:if>	
	</div>
	</form:form>
	<br />
	
	<div style="float:right;margin-top: 10px;" >
		<button type="button" id="saveGRFStudentInformation" class="btn_blue save grfSaveBtn curpointer" >
			<fmt:message key="button.save" />
		</button>
	</div>
</div>
</c:if>


<div id="saveGRFStudentInformationPopup" class="hidden">
	<div id="saveGRFStudentInformationPopupMessage"	style="margin-left: 50px; margin-right: 50px;line-height: 21px;">
		
		<div id="ivalidateAckContent" >
			<p>
				You have chosen to invalidate this record. Invalidating a student
				record will result in the student's<br /> assessment results being
				excluded from aggregate reporting, and the student will NOT receive
				the<br /> Individual Student Score Report.
			</p>
			<hr style="margin-top: 2%; margin-bottom: 2%;" class="extraFilterSection">
		</div>
		
		<div id="gradeAckContent" >
			<p>
				You have chosen to update the current grade. Updating the current
				grade will cause the student's<br /> assessment results to be removed
				and all 9's to be reported in the GRF.
			</p>
			<hr style="margin-top: 2%; margin-bottom: 2%;" class="extraFilterSection">
		</div>
		
		<div id="stateStudentIdAckContent" >
			<p>
				You have chosen to update the State Student ID for this student's
				<label>${studentGrf.subject}</label> GRF record. Changing this field only impacts this
				subject.
			</p>	
			<hr style="margin-top: 2%; margin-bottom: 2%;" class="extraFilterSection">	
			
			NOTE: Changing the State Student ID only
				updates the ID in the GRF and Individual Student Score Report.<br />
				This change will NOT be made to the student's enrollment information
				in Educator Portal or to any entry for<br /> that student reported in
				a supplemental file.
				
			<hr style="margin-top: 2%; margin-bottom: 2%;" class="extraFilterSection">	
		</div>
						
		<p id="errorMessageAcknowledge" style="color: red;" class="hidden">You must
			acknowledge that you understand the impact of the updates.</p>
		<p>
			<input type="checkbox" name="editStudentAckGRF" id="editStudentAckGRF" value="true">
			I acknowledge that I understand the impact of the updates.
		</p>
	</div>
</div>


<script type="text/javascript"
	src="<c:url value='/js/configuration/editStudentGRF.js'/>">
</script>
