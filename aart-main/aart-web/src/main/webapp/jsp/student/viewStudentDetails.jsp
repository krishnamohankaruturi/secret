<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
span.collapser {
	cursor: pointer;
}
img {
	max-width: 100%;
	height: auto;
	width: auto\9; /* ie8 */
}
.futureUnrollDate{
	color:red !important;
	display:none;
}
.invalidUnrollDate{
	color:red !important;
	display:none;
}
#invalidDateMessage_exit{
	color:red !important;
	display:none;
}
#unRollStudentNext{
	float: right;
	margin-top: 100px;
}
#unRollDate{
	height: 35px;    
    width: 226px !important;
    margin: 0px 0px 15px 0px;
}
 .text_hyperlink{
    text-decoration: underline;
    color: #0254eb !important;
}

</style>

<script type="text/javascript">
	var exitCodes = ${exitCodes};	
</script>

<div style="width: 100%" class = "pagecontent">
	<!-- Left Margin -->
	<div id="testCollectionInformationLeftMarginDiv" style="width: 5%; float: left;">
		&nbsp;
	</div>
	<c:if test="${action == 'exit'}">
	<div id="unRollStudentInputDetails" style="width: 100%; float: left; ">
			    		<div id="unRollStudentInput">
				    		<div><p>Student&#39;s Exit date, the date on which the change is effective: </p><input id="unRollDate" type=text readonly="true" title="Student's Exit Date"/>
				    		<p class="invalidUnrollDate">Invalid Date</p>
				    		<p class="futureUnrollDate">Future Date Not Allowed!</p>
				    		<p id ="invalidDateMessage_exit" style="color: red;!important">Exit date shouldn't less than school EntryDate!</p>
				    		</div>
				    		<div style="margin: 19px 0px 0px 0px;" ><p>Reason for Removing Student: </p><select id="reason_exit" class="bcg_select" title="Reason for Removing Student" >
 								<option value="0">Select</option>
							</select></div>
							<input type="hidden" id="currentSchoolYears_exit" value='${students[0].currentSchoolYears}'/>
							<input type="hidden" id="attendanceSchoolDisplayIdentifiers_exit" value='${students[0].attendanceSchoolDisplayIdentifiers}'/>
							<input type="hidden" id="stateStudentIdentifier_exit" value='${students[0].stateStudentIdentifier}'/>
							<input type="hidden" id="schoolEntryDate_exit" value='${students[0].schoolEntryDate}'/>
							<input type="hidden" id="selectedOrgs_exit" value='${selectedOrg}'/>
							
						</div>
						<div id="unRollStudentNext">
							<input type="button" id="unRollStudentNextConfirm" value="Exit Student"/>
						</div>
						<div id="exit_student_heading"  class="ui-dialog-title" style="margin-bottom:15px">							
							         Student Record - ${students[0].legalFirstName} ${students[0].legalLastName}
						</div>
    </div>
    </c:if>
	
	<div id="testCollection InformationMainDiv" style="width: 90%; float: left;" class="divBlockFont viewst">
		Student
		<c:set var="dialogName" value="Edit Student Record - ${students[0].legalFirstName}" />
		<c:if test="${students[0].legalMiddleName != null && students[0].legalMiddleName != ''}">
			<c:set var="dialogName" value="${dialogName} ${students[0].legalMiddleName}" />
		</c:if>
		<c:set var="dialogName" value="${dialogName} ${students[0].legalLastName}" />
		<c:set var="dialogName" value="${fn:escapeXml(dialogName)}"/>
		
		<div style="float: right;">
			<security:authorize access="hasRole('PERM_STUDENTRECORD_MODIFY')">
				<c:if test="${param.editLink}">
					<a href="javascript:closeAndCallEditStudent('${fn:escapeXml(dialogName)}',${param.studentId});" class="text_hyperlink">Edit</a>
				</c:if>
			</security:authorize>
		</div>
		<BR/>
		
		<hr style="width: 100%; height: 2px;"/>
		<!-- Left Margin -->
		<div style="width: 5%; float: left;">
			&nbsp;
		</div>
		<div style="width: 90%; float: left;">
			<table style="width: 100%;color:black;" role="presentation" >
				<tr>
					<td style="width: 50%;">
						<label>Student State ID: </label> ${students[0].stateStudentIdentifier}
					</td>
					<td style="width: 50%;">
						<label>Date of Birth: </label> ${students[0].dateOfBirthStr}
					</td>
				</tr>			 
				<c:if test="${students[0].dlmStudentStr == 'Yes' && isTeacherSelected}">
				<tr>
					<td style="width: 50%;">
						<label>Kite Login Username: </label>
						<c:choose>
							<c:when test="${isDLMAuthenticated}">
						       ${students[0].username}
						    </c:when>
						    <c:otherwise>
								<a class="restricted" href="#" style="color: red;">RESTRICTED</a>    	
						    </c:otherwise>
						</c:choose>
					</td>
					<td style="width: 50%;">
						<label>Password: </label>
						<c:choose>
							<c:when test="${isDLMAuthenticated}">
						       ${students[0].password}
						    </c:when>
						    <c:otherwise>
								<a class="restricted" href="#" style="color: red;">RESTRICTED</a>    	
						    </c:otherwise>
						</c:choose>
						
					</td>
				</tr>
				</c:if>
			</table>		 	
		</div>
		<!-- Right Margin -->
		<div style="width: 5%; float: right;">&nbsp;
		</div>
		<BR/>	
		Demographic<BR/>
		<hr style="width: 100%; height: 2px;"/>

		<div style="width: 5%; float: left;">
			&nbsp;
		</div>
		<div style="width: 90%; float: left;">
			<table style="width: 100%;color:black;" role="presentation" >
				<tr>
					<td style="width: 50%;">
						<label>Gender: </label> ${students[0].genderString}
					</td>
					<td style="width: 50%;">
						<label>Comprehensive Race: </label> ${students[0].comprehensiveRaceStr}
					</td>
				</tr>
				<tr>
					<td>
					<c:choose>
						<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
							<label>First Language: </label> ${students[0].firstLanguageStr}
						</c:when>
					</c:choose>
					</td>
					<td>
						<label>Hispanic Ethnicity: </label>${students[0].hispanicEthnicity}
					</td>
				</tr>
			</table>		 	
		</div>
		<!-- Right Margin -->
		<div style="width: 5%; float: right;">&nbsp;
		</div>
		<BR/>
		<BR/>
	
		Profile<BR/>
		<hr style="width: 100%; height: 2px;"/>

		<div style="width: 5%; float: left;">
			&nbsp;
		</div>
		<div style="width: 90%; float: left;">
			<table style="width: 100%;color:black;" role="presentation" >
				<tr>
					<c:if test="${students[0].primaryDisabilityCode != null && students[0].primaryDisabilityCode != '0'}">
					<td style="width: 50%;">
						<label>Primary Disability: </label> ${students[0].primaryDisabilityCodeStr}
					</td>
					</c:if>
					<td style="width: 50%;">
					    <label>PNP Profile: </label>
						<security:authorize access="hasRole('VIEW_STUDENT_PNP')">
							<c:if test="${isExit}">
						        <a class="text_hyperlink" href="javascript:localRef_accessProfileDetailsForExit(${param.studentId},'${param.stateStudentIdentifier}','${students[0].abbreviatedName}');">${students[0].accessProfileStatus}</a>
						    </c:if>
						    <c:if test="${!isExit}">
						        <a class="text_hyperlink" href="javascript:localRef_accessProfileDetails(${param.studentId},'${param.stateStudentIdentifier}','${students[0].abbreviatedName}');">${students[0].accessProfileStatus}</a>
						    </c:if>
					    </security:authorize>
						<security:authorize access="!hasRole('VIEW_STUDENT_PNP')">
						   ${students[0].accessProfileStatus}	
						</security:authorize>						
					</td>
				</tr>
				<tr>
					<td>
					<!-- Changes During US16289  -->
						<label style="margin: 0px;float: left;">Assessment Program: </label>
					<c:if test="${students[0].abbreviatedName != null && students[0].abbreviatedName != '0'}">	
				    <c:forTokens items="${students[0].abbreviatedName}" delims="," var="name">   
				    	<div style="padding-left: 175px;">	<c:out value="${name}"/></div>
					</c:forTokens> 
				    </c:if>	 
					</td>
					<security:authorize access="hasAnyRole('EDIT_FIRST_CONTACT_SURVEY','VIEW_FIRST_CONTACT_SURVEY')">
						<c:choose>
							<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
								<td>
									<label>First Contact Survey: </label> 
									<c:if test="${students[0].status != 'Not Applicable'}">
										<c:if test="${(hasFcsReadOnlyPermission && (students[0].status == 'NOT STARTED'))}">
											${students[0].status}
										</c:if>
										<c:if test="${!(hasFcsReadOnlyPermission && (students[0].status == 'NOT STARTED'))}">
											<c:if test="${isExit}">
										        <a class="text_hyperlink" href="javascript:localRef_viewFirstContactDetailsForExit(${param.studentId});">${students[0].status}</a>
										    </c:if>
										    <c:if test="${!isExit}">
												<a class="text_hyperlink" href="javascript:localRef_viewFirstContactDetails(${param.studentId});">${students[0].status}</a> 
											</c:if>
										</c:if>
									</c:if>	
									<c:if test="${students[0].status == 'Not Applicable'}">
										${students[0].status}
									</c:if>				
								</td>
							</c:when>
						</c:choose>
					</security:authorize>
				</tr>
				
				
				<c:if test="${students[0].esolParticipationCode != null && students[0].esolParticipationCode != '0'}">
					<tr>
						<td>
							<label>ESOL Participation: </label> ${students[0].esolParticipationCodeStr}
						</td>
						<td>
							<label>ESOL Entry: </label> ${students[0].esolProgramEntryDateStr}
						</td>
					</tr>
					<tr>
						<td>
							<label>USA Entry: </label> ${students[0].usaEntryDateStr}
						</td>					
					</tr>
				</c:if>
				
					
			</table>		 	
		</div>
		<!-- Right Margin -->
		<div style="width: 5%; float: right;">&nbsp;
		</div>
		<BR/>
		<BR/>
	
		School Enrollment<BR/>
		<hr style="width: 100%; height: 2px;"/>

		<div style="width: 5%; float: left;">
			&nbsp;
		</div>
		<div style="width: 90%; float: left;">
			<c:if test="${! empty students}">
				<c:set var="sname" value="" />
				<c:forEach var="s" items="${students}">
					<c:if test="${sname != s.schoolName}">
						<c:set var="sname" value="${s.schoolName}" />
						<div>
							<span class="collapser">-</span> 
							${s.districtName} (${s.residenceDistrictIdentifiers}) / 
							${s.schoolName} (${s.attendanceSchoolDisplayIdentifiers})
							<label style="font-weight:normal;">${s.gradeCourseName}, School Year ${s.currentSchoolYears}</label>
							<table style="width: 100%;color:black;" class="collapsiblerow" role="presentation" >
								<tr>
									<td><label>Accountability: 
									<c:if test="${(! empty s.accountabilityDistrictIdentifier) && (empty s.aypSchoolId) }">
									   </label> ${s.accountabilityDistrictName} (${s.accountabilityDistrictIdentifier})</td>
									</c:if>
									<c:if test="${(! empty s.accountabilityDistrictIdentifier) && (! empty s.aypSchoolId) }">
									   </label> ${s.accountabilityDistrictName} (${s.accountabilityDistrictIdentifier}) / ${s.aypSchoolNames} (${s.aypSchoolId})</td>
									</c:if>
								</tr>
							</table>
							<table style="width: 100%;color:black;" class="collapsiblerow" role="presentation" >
								
								<tr>
									<td><label>Student Local ID: </label> ${s.localStudentIdentifiers}</td>
									<td>
										<c:choose>
											<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
												<label>Gifted Student: </label> ${s.giftedStudentStr}
											</c:when>
										</c:choose>
									</td>
									<td></td>
									<td></td>								
								</tr>
								<tr>
									<td><label>State Entry: </label> ${s.stateEntryDate}</td>
									<td><label>District Entry: </label> ${s.districtEntryDate}</td>
									<td><label>School Entry: </label> ${s.schoolEntryDate}</td>
									<td></td>								
								</tr>
								<tr>
									<td colspan="4" class="itable">
										<table style="width: 100%;" role="presentation" >
											<tr>
												<th style="font-size: 17px;">${user.currentAssessmentProgramName == 'PLTW' ? 'Course:' : 'Subject:'}</th>
												<c:choose>
													<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
														<th style="font-size: 17px;">Course</th>
													</c:when>
												</c:choose>
												<th style="font-size: 17px;">Educator</th>
												<th style="font-size: 17px;">Roster</th>
											</tr>
												<c:set var="ctr" value="0" />
												<c:forEach var="hash" items="${studentSubjects[s.schoolName]}">
													<tr>
			        									<td><c:out value="${studentSubjects[s.schoolName][ctr]}"/></td>
			        									<c:choose>
															<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
																<td><c:out value="${studentCourses[s.schoolName][ctr]}"/></td>
															</c:when>
														</c:choose>
			        									<td><c:out value="${studentEducators[s.schoolName][ctr]}"/></td>
			        									<td><c:out value="${studentRosters[s.schoolName][ctr]}"/></td>
				  									</tr>
				  									<c:set var="ctr" value="${ctr+1}" />
		  										</c:forEach>
										</table>
									</td>
								</tr>
							</table>						    
						</div>
					</c:if>					
				</c:forEach>
			</c:if>
		</div>
		<!-- Right Margin -->
		<div style="width: 5%; float: right;">&nbsp;
		</div>
		<BR/>
	</div>
</div>
	
	<!--Added during US16243 to add a new dialog -->
<div id="dialog_view_pass" title=" " class="hidden">
    	<p> To view Student’s Kite Login User Name and Password, teachers must:</p>
             <p>1) Complete the required training for current school year and</p>
             <p>2) Agree to the Security Terms and Conditions.</p>
    </div>
<script type="text/javascript"	src="<c:url value='/js/student/viewStudentDetails.js'/>"> </script>   
<script type="text/javascript">
	var viewStudentPNPpermission = false; 
	<security:authorize access="hasRole('VIEW_STUDENT_PNP')">
		var viewStudentPNPpermission = true;
	</security:authorize>
</script>

