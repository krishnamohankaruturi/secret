<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
#gradeIdForEnrolledStudentScore{
font-size:14px; 
color: #0e76bc; 
font-weight: bold;
}
#kelpa2gradeIdForEnrolledStudentScore{
font-size:14px; 
color: #0e76bc; 
font-weight: bold;
}
.displayRedColor{
	color:red;
}
</style>
<div>
	<div><h1 class="panel_head sub">Standard data extracts</h1></div>
	<div>
		<img src="<c:url value='/images/icons/noteicon.png'/>" title="Note" alt="Note"/>
		Student data extracts include <span style="font-weight:bold">Personally Identifiable Information (PII)</span>, so please take the appropriate precautions to <span style="font-weight:bold">protect</span> saved files.
	</div>
	<div id="extractsSection" class="kite-table">
		<table class="responsive" id="extractsTable" role='presentation'></table>
		<div id="extractsPager"></div>
	</div>
</div>
<br>

<div id="dataExtractFilterDialog" style="display:none; height: auto;">
	<div id="summaryLevelExtractFilter">
		<label>Summary Level<font color="red">*</font>:</label>&nbsp;
		<span id="extractSummaryFilterStateSpan"><input type="radio" value="20" name="extractSummaryLevel" title="<fmt:message key='label.extract.summary.level.state'/>"/><fmt:message key='label.extract.summary.level.state'/>&nbsp;</span>
		<span id="extractSummaryFilterDistrictSpan"><input type="radio" value="50" name="extractSummaryLevel" title="<fmt:message key='label.extract.summary.level.district'/>"/><fmt:message key='label.extract.summary.level.district'/>&nbsp;</span>
		<span id="extractSummaryFilterSchoolSpan"><input type="radio" value="70" name="extractSummaryLevel" title="<fmt:message key='label.extract.summary.level.school'/>"/><fmt:message key='label.extract.summary.level.school'/>&nbsp;</span>
		<div>&nbsp;</div>
		<div id="extractSummaryErrorMsg" style="color: red;"></div>
	</div>
	<div id="testFormAssignmentLevelExtractFilter">
		<div id="assessmentProgramExtractFilter"  class="form-fields">
			<label for="extractAssessmentProgram" class="isrequired field-label">Assessment Program:<span class="lbl-required">*</span></label>
			<select id="extractAssessmentProgram" name="extractAssessmentProgram" class="select-plugin" multiple="multiple" title="Click to select Assessment Program">
			</select>
		</div>
		<br/>
		<div id="qcCompleteExtractFilter"  style="margin-bottom:5%;">
			<label for="extractQCCompleteStatus">QC Complete status<font color="red">*</font>:</label>&nbsp; 
			<select id="extractQCCompleteStatus" name="extractQCCompleteStatus" class="select-plugin">
				<option value="" title="Click to select QC Complete status">Select</option>
				<option value="true">TRUE</option>
				<option value="false">FALSE</option>
				<option value="both">BOTH</option>
			</select>
		</div>
		
		
		<div>
        <table style="margin-bottom:5%;" role='presentation'>
			<tr><td><label for="fromDate" class="field-label isrequired">Begin Date:</label></td>
				<td><input id="fromDate" name="fromDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/></td>			        
			    <td><label for="toDate" class="field-label isrequired">End Date:</label></td>
				<td><input id="toDate" name="toDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/></td>
			</tr>
		</table>
	</div>	
		<div id="testFormWindow" style="margin-bottom:5%;">
				<input type="checkbox" id="media" title="Check to Only Include Test Forms Without Resources" /> Only Include Test Forms Without Resources
		</div>
	</div>
	<div id="extractFilterDropdowns">
		<div id="extractSubFilter" class="">
		<div id="stateExtractFilter" style="margin-bottom:5%;">
			<label for="extractStateId">State<font color="red">*</font>:</label>&nbsp;<select id="extractStateId" class="select-plugin"><option value="" title="Click to select State">Select</option></select>
		</div>
		<div id="assessmentProgramForStudentExtractFilter"  class="form-fields" style="margin: 10px 0px 2% 2%;" >
		    <label for="extractAssessmentProgramId" class="isrequired field-label">Assessment Program:<span class="lbl-required">*</span></label>
	        <select id="extractAssessmentProgramId" name="extractAssessmentProgramId" class="select-plugin" multiple="multiple" title="Click to select Assessment Program">
	        </select>
	   </div>
		<div id="districtExtractFilter" style="margin: 10px 0px 2% 2%;">
					
			<label for="extractDistrictId">District:</label>
			<font id="pltwFont" color="red" hidden>*</font>&nbsp;<select id="extractDistrictId" class="select-plugin"><option value="" title="Click to select District">Select</option></select>
		</div>
		<div id="schoolExtractFilter" style="margin: 10px 0px 2% 2%;">
			<label for="extractSchoolId">School:</label>&nbsp;<select id="extractSchoolId" class="select-plugin"><option value="" title="Click to select School">Select</option></select>
		</div>	
		<c:choose>
			<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
				<div id="gradeExtractFilter" style="margin: 10px 0px 2% 2%;">
					<label for="extractGradeId">Grade:</label>&nbsp;<select id="extractGradeId" class="select-plugin"><option value="" title="Click to select Grade">Select</option></select>
				</div>
			</c:when>
		</c:choose>
		<div id="subjectExtractFilter" style="margin: 10px 0px 2% 2%;">
		<label for="extractSubjectId">${user.currentAssessmentProgramName == 'PLTW' ? 'Course:' : 'Subject:'}</label>&nbsp;<select id="extractSubjectId" class="select-plugin" title="Subject"><option value="" title="Click to select Subject">Select</option></select>
		</div>
		<div id="rosterExtractFilter" style="margin: 10px 0px 2% 2%; display: none;">
			<label for="extractRosterId">Roster:</label>&nbsp;<select id="extractRosterId" class="select-plugin"><option value="" title="Click to select Roster">Select</option></select>
		</div>
		<div id="downloadFileTypes" style="margin: 10px 0px 2% 2%;font:bold;">
		<label>Download as:</label><span style="color: red;">*</span>&nbsp;&nbsp;
			<input id="CSVdownload" type="checkbox" title="CSVdownload">&nbsp;&nbsp;<label>CSV</label>&nbsp;&nbsp;
			<input id="PDFdownload" type="checkbox" title="PDFdownload">&nbsp;&nbsp;<label>PDF</label>	
		</div>
		</div>
	</div>
	
	<div id="extractFiltersForITIBlueprint">
		<div id="itiblueprintSummaryLevelFilter" class = "hidden">
			<label>Summary Level<font color="red">*</font>:</label>&nbsp;
			<span id="itiBluePrintSummaryFilterStateSpan"><input type="radio" value="20" name="itiBluePrintextractSummaryLevel" title="<fmt:message key='label.extract.summary.level.state'/>"/><fmt:message key='label.extract.summary.level.state'/>&nbsp;</span>
			<span id="itiBluePrintSummaryFilterDistrictSpan"><input type="radio" value="50" name="itiBluePrintextractSummaryLevel" title="<fmt:message key='label.extract.summary.level.district'/>"/><fmt:message key='label.extract.summary.level.district'/>&nbsp;</span>
			<span id="itiBluePrintSummaryFilterSchoolSpan"><input type="radio" value="70" name="itiBluePrintextractSummaryLevel" title="<fmt:message key='label.extract.summary.level.school'/>"/><fmt:message key='label.extract.summary.level.school'/>&nbsp;</span>
			<div>&nbsp;</div>
			<div id="itiBluePrintSummaryErrorMsg" style="color: red;"></div>
		</div>
	   <div id="itiBluePrintExtractFilterDropdowns" class = "hidden">
	   		<div id="itiBluePrintStateExtractFilter" style="margin-bottom:5%;">
				<label for="itiBluePrintExtractStateId">State<font color="red">*</font>:</label>&nbsp;<select id="itiBluePrintExtractStateId" class="select-plugin"><option value="" title="Click to select State">Select</option></select>
			</div>
			<div id="itiBluePrintDistrictExtractFilter" style="margin: 10px 0px 2% 2%;">
				<label for="itiBluePrintExtractDistrictId">District:</label>&nbsp;<select id="itiBluePrintExtractDistrictId" class="select-plugin"><option value="" title="Click to select District">Select</option></select>
			</div>
			<div id="itiBluePrintSchoolExtractFilter" style="margin: 19px 0px 2% 2%;">
				<label for="itiBluePrintExtractSchoolId">School:</label>&nbsp;<select id="itiBluePrintExtractSchoolId" class="select-plugin"><option value="" title="Click to select School">Select</option></select>
			</div>
			<div id="subjectExtractForITIBP" style="margin: 28px 0px 2% 2%;">
	      		<label for="itiSubjectExtractFilter">Subject</label>&nbsp;&nbsp;<select id="itiSubjectExtractFilter" class="select-plugin"><option value="" title="Click to select Subject">Select</option></select>
	      	</div>
	      	<div id="gradeExtractForITIBP" style="margin: 37px 0px 2% 2%;">
	      		<label for="itiGradeExtractFilter">Grade</label>&nbsp;&nbsp;<select id="itiGradeExtractFilter" class="select-plugin"><option value="" title="Click to select Grade">Select</option></select>
	      	</div>
	      	<div id="groupByCheckBoxForITIBP" style="margin: 38px 20% 2% 15%;">
				<input type="checkbox" id="groupByTeacher" title="Check to group by teacher"/> Group by teacher
			</div>
	   </div>
	</div>
	
	<div id="extractFiltersForOrganization">
		<div id="organizationSummaryLevelFilter" class = "hidden">
			<label>Summary Level<font color="red">*</font>:</label>&nbsp;
			<span id="organizationSummaryFilterStateSpan"><input type="radio" value="20" name="organizationextractSummaryLevel" title="<fmt:message key='label.extract.summary.level.state'/>"/><fmt:message key='label.extract.summary.level.state'/>&nbsp;</span>
			<span id="organizationSummaryFilterDistrictSpan"><input type="radio" value="50" name="organizationextractSummaryLevel" title="<fmt:message key='label.extract.summary.level.district'/>"/><fmt:message key='label.extract.summary.level.district'/>&nbsp;</span>
			<span id="organizationSummaryFilterSchoolSpan"><input type="radio" value="70" name="organizationextractSummaryLevel" title="<fmt:message key='label.extract.summary.level.school'/>"/><fmt:message key='label.extract.summary.level.school'/>&nbsp;</span>
			<div>&nbsp;</div>
			<div id="organizationSummaryErrorMsg" style="color: red;"></div>
		</div>
	   <div id="organizationExtractFilterDropdowns" class = "hidden">
	   		<div id="organizationStateExtractFilter" style="margin: 10px 0px 2% 2%;">
				<label for="organizationExtractStateId">State&nbsp;<font color="red">*</font>:</label>&nbsp;<select id="organizationExtractStateId" class="select-plugin"><option value="" title="Click to select State">Select</option></select>
			</div>
			<div id="organizationDistrictExtractFilter" style="margin: 10px 0px 2% 2%;">
				<label for="organizationExtractDistrictId">District:</label>&nbsp;<select id="organizationExtractDistrictId" class="select-plugin"><option value="" title="Click to select District">Select</option></select>
			</div>
			<div id="organizationSchoolExtractFilter" style="margin: 10px 0px 2% 2%;">
				<label for="organizationExtractSchoolId">School:</label>&nbsp;<select id="organizationExtractSchoolId" class="select-plugin"><option value="" title="Click to select State">Select</option></select>
			</div>
			<div id="checkboxidforinactiveOrg" style="margin: 10px 0px 2% 2%;" >
				<input type="checkbox"  id="includeInactiveOrganizations" title="Check to Include Inactive Organizations"/>Include Inactive Organizations
	   		</div> 			
	   </div>	  
	</div>
	
	<div id="extractFilterDropdownsForPDTraining">
		<div id="extractSubFilterForPDTraining" class = "hidden">
		<div id="stateExtractFilterForPDTraining" class="form-fields " style="margin-bottom:5%;">
			<label for="extractStateIdForPDTraining">State:</label>&nbsp;<select id="extractStateIdForPDTraining" name="extractStateIdForPDTraining" class="select-plugin" multiple="multiple" title="Click to select State"></select>
		</div>		
		<div id="districtExtractFilterForPDTraining"  class="form-fields " style="margin-bottom:5%;">
			<label for="extractDistrictIdForPDTraining">District:</label>&nbsp;<select id="extractDistrictIdForPDTraining" name="extractDistrictIdForPDTraining" class="select-plugin" multiple="multiple" title="Click to District"></select>
		</div>
		<div id="schoolExtractFilterForPDTraining"  class="form-fields " style="margin-bottom:5%;">
			<label for="extractSchoolIdForPDTraining">School:</label>&nbsp;<select id="extractSchoolIdForPDTraining" name="extractSchoolIdForPDTraining" class="select-plugin" multiple="multiple" title="Click to select School"></select>
		</div>	
		</div>
	</div>
	
	
	<!-- DLM General Research File -->
	<div id="dlmGeneralReseach" style="display:none;">
		<div id="dlmGeneralReseachExtractFilterDropdowns" class = "hidden" style='margin-left: 70px;'>
	   		<div id="dlmGRFErrorMsg" class="hidden" style="margin:0px;color:red;">There is no data to populate the extract.</div>	
			<div id="dlmGeneralReseachDistrictExtractFilter" style="margin: 10px 0px 2% 2%;">
				<label for="dlmGeneralReseachExtractDistrictId">District:</label>&nbsp;<select id="dlmGeneralReseachExtractDistrictId" class="select-plugin" style="width: 55%; margin-left: 6px;"><option value="" title="Click to select District">Select</option></select>
			</div>
			<div id="dlmGeneralReseachSchoolExtractFilter" style="margin: 19px 0px 2% 2%;">
				<label for="dlmGeneralReseachExtractSchoolId">School:</label>&nbsp;<select id="dlmGeneralReseachExtractSchoolId" class="select-plugin" style="width: 55%; margin-left: 6px;"><option value="" title="Click to select School">Select</option></select>
			</div>
			<div id="subjectExtractFordlmGeneralReseach" style="margin: 28px 0px 2% 2%;">
	      		<label for="dlmGeneralReseachSubjectExtractFilter">Subject:</label>&nbsp;&nbsp;<select id="dlmGeneralReseachSubjectExtractFilter" class="select-plugin" style="width: 55%; margin-left: -3px;"><option value="" title="Click to select Subject">Select</option></select>
	      	</div>
	      	<div id="gradeExtractFordlmGeneralReseach" style="margin: 37px 0px 2% 2%;">
	      		<label for="dlmGeneralReseachGradeExtractFilter">Grade:</label>&nbsp;&nbsp;<select id="dlmGeneralReseachGradeExtractFilter" class="select-plugin" style="width: 55%; margin-left: 6px;"><option value="" title="Click to select Grade">Select</option></select>
	      	</div>
	   </div>
	</div>
	<div id="extractFilterDropdownsForSecurityExtract">
		<div id="extractSubFilterForSecurityExtract" class = "hidden">
			<div id="stateExtractFilterForSecurityExtract" class="form-fields " style="margin: 0 0 2% 20%;">
				<label for="extractStateIdForSecurityExtract">State:</label>&nbsp;<select id="extractStateIdForSecurityExtract" class="select-plugin" name="extractStateIdForSecurityExtract" class="select-plugin" multiple="multiple" title="Click to select State"></select>
			</div>		
			<div id="districtExtractFilterForSecurityExtract"  class="form-fields " style="margin:10px 0px 2% 21%">
				<label for="extractDistrictIdForSecurityExtract">District:</label>&nbsp;<select id="extractDistrictIdForSecurityExtract" class="select-plugin" name="extractDistrictIdForSecurityExtract" class="select-plugin" multiple="multiple" title="Click to select District"></select>
			</div>
			<div id="schoolExtractFilterForSecurityExtract"  class="form-fields " style="margin:0px 0px 2% 21%;">
				<label for="extractSchoolIdForSecurityExtract">School:</label>&nbsp;<select id="extractSchoolIdForSecurityExtract" class="select-plugin" name="extractSchoolIdForSecurityExtract" class="select-plugin" multiple="multiple" title="Click to select School"></select>
			</div>	
		</div>
	</div>
	
	<div id="dateSelectionFilter" style="display:none">
	<br/><br/>
		<table role='presentation'>
			<tr>
				<td width="55%">
				<table>
					<tr><td colspan="2"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Instructional Dates</font><font color="red">*</font></td></tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr><td><label for="itiFromDate" class="field-label isrequired">Begin:</label></td>
			           	<td><input id="itiFromDate" name="itiFromDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/></td>
			        </tr>
		           	<tr><td><label for="itiToDate" class="field-label isrequired">End:</label></td>
			           	<td><input id="itiToDate" name="itiToDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/></td>
			        </tr>
		         </table>
			 </td>
			 <td>    
	             <table>
	             	<tr><td colspan="2"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">End of Year Dates</font><font color="red">*</font></td></tr>
	             	<tr><td colspan="2">&nbsp;</td></tr>
		         	<tr><td><label for="eoyFromDate" class="field-label isrequired">Begin:</label></td>
			           	<td><input id="eoyFromDate" name="eoyFromDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/></td>
			        </tr>
		           	<tr><td><label for="eoyToDate" class="field-label isrequired">End:</label></td>
			           	<td><input id="eoyToDate" name="eoyToDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/></td>
			        </tr>
		         </table>
		     </td>
		     </tr>
        </table>
	</div>
	<br>
	<div id="ksdeExtractFilter" style="display:none">
		<label for="extractStudentStateId">Student State ID<font color="red">*</font>:</label>&nbsp;<input type="text" id="extractStudentStateId" name="extractStudentStateId" value=""/>
		<br>
	</div>
	<div id="kapStudentScoreExtractFilter" style="display:none">	
		
		<div id="districtFilterForStudentScore">				
			<label for="districtIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Distict:</font><span class="lbl-required">*</span></label><br/>
			<select id="districtIdForStudentScore" name="districtIdForStudentScore" class="select-plugin"><option value="" title="Click to select District">Select</option></select>
		</div>		
		<div id="schoolFilterForStudentScore">	
			<label for="schoolIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">School:</font></label><br/>
		<!-- 	<select id="schoolIdForStudentScore" name="schoolIdForStudentScore" class="select-plugin"><option value="">Select</option></select> <br/>  -->
			<select id="schoolIdForStudentScore" name="schoolIdForStudentScore" class="select-plugin" multiple="multiple" title="Click to select School"></select>
			<input type="checkbox" class="selectAll_kap_extract" id="schoolIdForStudentScore_kap_score" checked="true" title="Click to select School"/>Select All
		</div>		
		<div id="subjectFilterForStudentScore">
			<label for="subjectIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Subject:</font><!-- <span class="lbl-required">*</span> --></label><br/>
			<select id="subjectIdForStudentScore" name="subjectIdForStudentScore" class="select-plugin" multiple="multiple" title="Click to select Subject"></select>
			<input type="checkbox" class="selectAll_kap_extract" id="subjectIdForStudentScore_kap_score" checked="true" title="Click to select Subject"/>Select All
		</div>		
		<div id="gradeFilterForStudentScore">		
			<label id ="gradeIdForEnrolledStudentScore" for="gradeIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;"></font> </label><br/> 
			 <input type="hidden" id="kap_St_schoolYear" value="${schoolYear}"></input>
			 <input type="hidden" id="kap_St_ReportYear" value="${reportYear}"></input>		
			<select id="gradeIdForStudentScore" name="gradeIdForStudentScore" class="select-plugin" multiple="multiple" title="Click to select Grade"></select><!-- <br/> -->
			<input type="checkbox" class="selectAll_kap_extract" id="gradeIdForStudentScore_kap_score" checked="true" title="Click to select Grade"/>Select All
		</div><br/>		
		
			
		<div id="schoolYearFilterForStudentScore">
			<label for="schoolYearIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Prior Report Years:</font></label><label>(Current Year ${reportYear} always included)</label><br/>
			<select id="schoolYearIdForStudentScore" name="schoolYearIdForStudentScore" class="select-plugin" multiple="multiple" title="Click to select Year"></select>
			<input type="checkbox" class="selectAll_kap_extract" id="schoolYearIdForStudentScore_kap_score" checked="true" title="Click to select Year"/>Select All
		</div>		
		<br/>		
	</div>
	
	<!-- KELPA 2 Student scores extract -->
	
	<div id="kelpa2studentScoreExtractFilter" style="display:none">	
		
		<div id="kelpa2districtFilterForStudentScore">				
			<label for="kelpa2districtIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Distict:</font><span class="lbl-required">*</span></label><br/>
			<select id="kelpa2districtIdForStudentScore" name="kelpa2districtIdForStudentScore" class="select-plugin"><option value="">Select</option></select>
		</div>		
		<div id="kelpa2schoolFilterForStudentScore">	
			<label for="kelpa2schoolIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">School:</font></label><br/>
		<!-- 	<select id="schoolIdForStudentScore" name="schoolIdForStudentScore" class="select-plugin"><option value="">Select</option></select> <br/>  -->
			<select id="kelpa2schoolIdForStudentScore" name="kelpa2schoolIdForStudentScore" class="select-plugin" multiple="multiple"></select>
			<input  id="schoolIdForStudentScore_kelpa2_score" type="checkbox"  name= "schoolIdForStudentScore" class="selectAll_kelpa2_extract" checked="true" title = "schoolIdForStudentScore"/>Select All
		</div>			
		<div id="kelpa2gradeFilterForStudentScore">		
			<label id ="kelpa2gradeIdForEnrolledStudentScore" for="kelpa2gradeIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;"></font> </label><br/> 
			 <input type="hidden" id="kelpa2_St_schoolYear" value="${schoolYear}"></input>
			 <input type="hidden" id="kelpa2_St_ReportYear" value="${reportYear}"></input>		
			<select id="kelpa2gradeIdForStudentScore" name="kelpa2gradeIdForStudentScore" class="select-plugin" multiple="multiple"></select><!-- <br/> -->
			<input type="checkbox"  id="gradeIdForStudentScore_kelpa2_score" name="gradeIdForStudentScore" class="selectAll_kelpa2_extract" checked="true" title = "gradeIdForStudentScore"/>Select All
		</div><br/>		
		
			
		<div id="kelpa2schoolYearFilterForStudentScore">
			<label for="schoolYearIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Prior Report Years:</font></label><label>(Current Year ${reportYear} always included)</label><br/>
			<select id="kelpa2schoolYearIdForStudentScore" name="kelpa2schoolYearIdForStudentScore" class="select-plugin" multiple="multiple" title = "kelpa2schoolYearIdForStudentScore"></select>
			<input type="checkbox" id="schoolYearIdForStudentScore_kelpa2_score" name="schoolYearIdForStudentScore" class="selectAll_kelpa2_extract" checked="true" title = "schoolYearIdForStudentScore"/>Select All
		</div>		
		<br/>		
	</div>
	
	<div id="kapStudentSpecifiedScoreExtractFilter" style="display:none">	
	<div id="ssidExtractFilterForStudentScore"  class="form-fields-inline ">
			<label for="stateStudentIdForStudentScore" class="field-label"><font style="font-size:14px; color: #0e76bc; font-weight: bold;">Specify a State Student ID</font><span class="lbl-required">*</span></label><br/>
			<input type="text" id="stateStudentIdForStudentScore" name="stateStudentIdForStudentScore" value=""/>
		</div>
	</div>
	
	<div id="dateExtractQueueMessage" style="display:none; color: blue;"></div>
	<div id="dlmTestAdministrationMessage" style="display:none; color: blue; padding: 0 0 0 4.75em; text-indent: -4.75em"><fmt:message key='label.dlm.test.administration.extract.message'/></div>

	<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
		<c:choose>
		    <c:when test="${user.contractingOrgDisplayIdentifier == 'DLMQCST'|| user.contractingOrgDisplayIdentifier == 'DLMQCIMST'|| user.contractingOrgDisplayIdentifier == 'DLMQCEOYST'|| user.contractingOrgDisplayIdentifier == 'DLMQCYEST'|| user.contractingOrgDisplayIdentifier == 'NYTRAIN'}">
				<div id="checkboxidforinternal" style="margin-left: 28px; margin-top: -31px;">
					<input type="hidden"  id="includeInternuluserId" checked="true" />
				</div>
			</c:when>
			<c:otherwise>
				<div id="checkboxidforinternal" style="margin-left: 28px; margin-top: -31px;">
					<input type="checkbox"  id="includeInternuluserId" checked="true" title="Check to include Internal Users in Extract"/>Include Internal Users in Extract
				</div> 
		    </c:otherwise>
		</c:choose>
	</security:authorize>
	
	<security:authorize access="hasRole('DATA_EXTRACTS_PNP_ABRIDGED')">
		<div id="pnpAbridgedFilters">
			<label>File Type:</label>
			&nbsp;<input type="radio" id="fileType_csv" name="fileType" value="csv" checked="checked" />&nbsp;<label for="fileType_csv">CSV</label>
			&nbsp;&nbsp;<input type="radio" id="fileType_xlsx" name="fileType" value="xlsx" />&nbsp;<label for="fileType_xlsx">Excel</label>
			<br/>
			&nbsp;<input id="includeAllStudents" name="includeAllStudents" type="checkbox"/>&nbsp;<label for="includeAllStudents">Include students with no PNP settings</label>
		</div>
	</security:authorize>
</div>

<div id="dataExtractNewFileDialog" class="confirmNewFileDialog"></div>
<input type="hidden" id="dataExtractUserAccessLevelId" value="${user.accessLevel}" />
<input type="hidden" id="userNarrowestOrgId" value="${user.organizationId}" />
<input type="hidden" id="groupCode" value="${user.groupCode}" /> 


<script>
var isTeacher =false;
var instructionalStartDate = "${applicationScope['test.admin.monitoring.extract.instructional.start']}";
var instructionalEndDate ="${applicationScope['test.admin.monitoring.extract.instructional.end']}";
var eoyStartDate ="${applicationScope['test.admin.monitoring.extract.eoy.start']}";
var eoyEndDate ="${applicationScope['test.admin.monitoring.extract.eoy.end']}";
var queuedStartTime = parseInt(${applicationScope['queued.extracts.starttime']});
var queuedEndTime = ${applicationScope['queued.extracts.endtime']};
var queuedEnabled = ${applicationScope['queued.enabled']};
var queuedMsg = "";
var isQcState=false;

if(${user.contractingOrgDisplayIdentifier == 'DLMQCST'|| user.contractingOrgDisplayIdentifier == 'DLMQCIMST'|| user.contractingOrgDisplayIdentifier == 'DLMQCEOYST'|| user.contractingOrgDisplayIdentifier == 'DLMQCYEST'||user.contractingOrgDisplayIdentifier == 'NYTRAIN'}){
	isQcState=true;
}
if(queuedEnabled)
	queuedMsg = "Your request will be in queue and will be available between " + (queuedStartTime - 12) + " p.m. and " + queuedEndTime + " a.m. central time, thank you for your patience.";

var fmtDataExtractMsg = {
	  ReportsDataExtracts : "<fmt:message key='label.reports.dataExtracts'/>",
	  CommonNoRecordsFound : "<fmt:message key='label.common.norecordsfound'/>",
	  QueuedMessage : queuedMsg
};

if(${user.teacher}){
	isTeacher=true;
}
var hasEditCustomFilePermission =false;
<security:authorize access="hasRole('MANAGE_STATE_SPECIFIC_FILES')">
	hasEditCustomFilePermission =true;
</security:authorize>
</script>
<script type="text/javascript" src="<c:url value='/js/external/select2.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/dataExtracts.js'/>"></script>