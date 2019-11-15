<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<c:if test="${empty errorMessage}">


<c:forEach items="${resultStudent}" var="curRecord">
	<c:set var="MathRecord" value="${curRecord.subjectRecord.M}" />
	<c:set var="ELArecord" value="${curRecord.subjectRecord.ELA}" />
	<c:set var="ScienceRecord" value="${curRecord.subjectRecord.Sci}" />
	<c:set var="viewStudentDialogTitle">
		<c:out value="${curRecord.student.legalFirstName}"/>&nbsp;<c:if test="${not empty curRecord.student.legalMiddleName}"><c:out value="${curRecord.student.legalMiddleName}"/>&nbsp;</c:if><c:out value="${curRecord.student.legalLastName}"/> 
	</c:set>
	
    <div class='container-fluid'> 
	<div class= 'row teacherStudentRow'>
		<div class='col-md-3'>
			  <div class='row-md-3 teacherCell'><c:if test="${!user.isTeacher}">Teacher: ${curRecord.teacherName} </c:if></div>
			  <div class='row-md-7 gradeCell'><div class='circle'>${curRecord.grade}</div></div>
		  </div>
		  <div class='col-md-8'>
			<div id ='inner_table_${curRecord.recordKey}' class='container-fluid box-shadow--16dp'>
				<div class= 'row studentHeader headerText'>
					<div class='col-md-6'>
						<a
							id="iap_home_viewStudent_${curRecord.recordKey}"
							class="viewStudentLink"
							onclick="callMethodPassed('openViewStudentPopup', '${curRecord.student.id}','${curRecord.student.legalFirstName}','${curRecord.student.legalMiddleName}',
								'${curRecord.student.legalLastName}','${curRecord.student.stateStudentIdentifier}','${curRecord.student.gender}','${curRecord.student.dateOfBirth}',
								'${user.currentAssessmentProgramName}','${viewStudentDialogTitle}')">
									<u class='headerText'>${curRecord.studentName}</u>
						</a>
						<br>
						State ID: ${curRecord.studentStateIdentifier}
					</div>
					<div class='col'>
					<security:authorize access="hasAnyRole('VIEW_FIRST_CONTACT_SURVEY', 'EDIT_FIRST_CONTACT_SURVEY')">
						<figure class='iap-icon'>
						<c:choose>
							<c:when test="${curRecord.student.firstContact != 'Completed'}">
							  	<img class='header-icon centerIcon center'
													id='iap_home_fcsImage_${curRecord.recordKey}'
													title='${clickToSeeText} First Contact Survey'
													onclick="viewFirstContactDetails('${curRecord.student.id}','${curRecord.studentName}')"
													src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/whiteAlert.svg'/>'/>
								</c:when>
								<c:otherwise>
								<img class='header-icon centerIcon center'
													id='iap_home_fcsImage_${curRecord.recordKey}'
													title='${clickToSeeText} First Contact Survey'
													onclick="viewFirstContactDetails('${curRecord.student.id}','${curRecord.studentName}')"
													src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/CheckmarkBox-White.svg'/>'/>
									</c:otherwise>
									</c:choose>
									<figcaption class='iap_caption iconText'>First Contact</figcaption>				
								</figure>
					</security:authorize>
					</div>
					<div class='col'>
					<figure class='iap-icon'>
							<img class='header-icon centerIcon center'
								id='iap_home_pnpImage_${curRecord.recordKey}'
								title='${clickToSeeText} Personal Needs Profile'
								onclick="callMethodPassed('accessProfileDetails','${curRecord.student.id}','${curRecord.student.legalFirstName}','${curRecord.student.legalMiddleName}',
								'${curRecord.student.legalLastName}','${curRecord.student.stateStudentIdentifier}','${curRecord.student.gender}','${curRecord.student.dateOfBirth}',
								 '${user.currentAssessmentProgramName}','${curRecord.studentName}')"
								src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/person-White.svg'/>'/>
							<figcaption class='iap_caption iconText'>PNP Profile</figcaption>
							</figure>
					</div>
					<div class='col'>
					<figure class='iap-icon'>
							<img class='header-icon centerIcon center iap_home_credentialsImage'
								id='iap_home_credentialsImage_${curRecord.recordKey}'
								alt='${clickToSeeText} credentials'
								data-toggle='popover'
								data-username='${curRecord.studentUserName}'
								data-password='${curRecord.studentPassword}'
								src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/lock-White.svg'/>'/>
								<figcaption class='iap_caption iconText'>Credentials</figcaption>
							</figure>
					</div>
					
				</div>
				<div class = "row studentHeader headerText">
						<div class='col-md-6'> </div>
						<c:if test="${not empty ELArecord}">
						<div class='col iap-columnText'>ELA</div>
						</c:if>
						<c:if test="${not empty MathRecord}">
						<div class='col iap-columnText'>MATH</div>
						</c:if>
						<c:if test="${not empty ScienceRecord}">
						<div class='col iap-columnText'>SCI</div>
						</c:if>
					</div>
				<div class= 'row greyBackground'>
					<div class='col-md-6'>View/Create plans</div>
					<c:if test="${not empty ELArecord}">
					<div class='col'>
					<figure class='iap-icon'>
						  	<img class='header-icon centerIcon center'
												id='iap_home_viewInstELA_${ELArecord.enrollmentrosterid}'
												title='viewCreateInstruction ELA'
												onclick="viewCreateInstruction('${curRecord.student.id}','${ELArecord.rosterid}','${ELArecord.enrollmentrosterid}')"
												src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/circleArrow-blue.svg'/>'/>		
							</figure>
					</div></c:if>
					<c:if test="${not empty MathRecord}">
					<div class='col'>
					<figure class='iap-icon'>
						  	<img class='header-icon centerIcon center'
												id='iap_home_viewInstMath_${ELArecord.enrollmentrosterid}'
												title='viewCreateInstruction Math'
												onclick="viewCreateInstruction('${curRecord.student.id}','${MathRecord.rosterid}','${MathRecord.enrollmentrosterid}')"
												src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/circleArrow-blue.svg'/>'/>	
							</figure>
					</div></c:if>
					<c:if test="${not empty ScienceRecord}">
					<div class='col'>
					<figure class='iap-icon'>
						  	<img class='header-icon centerIcon center'
												id='iap_home_viewInstSCI_${ELArecord.enrollmentrosterid}'
												title='viewCreateInstruction SCI'
												onclick="viewCreateInstruction('${curRecord.student.id}','${ScienceRecord.rosterid}','${ScienceRecord.enrollmentrosterid}')"
												src='<c:url value='/images/icons/instruction-and-assessment-planner/svg/circleArrow-blue.svg'/>'/>			
							</figure>
					</div></c:if>
				</div>
				<c:if test="${isIEModelState}">
				<div class= 'row lightBlueBackground'>
					<div class='col-md-6'>Essential Elements complete that count towards meeting blueprint requirements</div>
					<c:if test="${not empty ELArecord.numofEEs}">
					<div class='col iap-columnText'> 
						${ELArecord.numofEEsCompleted} of ${ELArecord.numofEEs} 
					</div></c:if>
					<c:if test="${not empty MathRecord.numofEEs}">
					<div class='col iap-columnText'>
					 	${MathRecord.numofEEsCompleted} of ${MathRecord.numofEEs} 
					</div></c:if>
					<c:if test="${not empty ScienceRecord.numofEEs}">
					<div class='col iap-columnText'>
					 NA 
					</div></c:if>
				</div>
				</c:if>
				<div class= 'row'>
					<div class='col-md-6'>Number of plans with instruction in progress</div>
					<c:if test="${not empty ELArecord}">
					<div class='col iap-columnText'>${ELArecord.instTestsInProgress} 
					</div></c:if>
					<c:if test="${not empty MathRecord}">
					<div class='col iap-columnText'>${MathRecord.instTestsInProgress} 
					</div></c:if>
					<c:if test="${not empty ScienceRecord}">
					<div class='col iap-columnText'>${ScienceRecord.instTestsInProgress} 
					</div></c:if>
				</div>
				<div class= 'row greyBackground'>
					<div class='col-md-6'>Testlets assigned and ready to test</div>
					<c:if test="${not empty ELArecord}">
					<div class='col iap-columnText'>${ELArecord.instTestsNotStarted} 
					</div></c:if>
					<c:if test="${not empty MathRecord}">
					<div class='col iap-columnText'>${MathRecord.instTestsNotStarted} 
					</div></c:if>
					<c:if test="${not empty ScienceRecord}">
					<div class='col iap-columnText'>${ScienceRecord.instTestsNotStarted} 
					</div></c:if>
				</div>
				<div class= 'row'>
					<div class='col-md-6'>Total number of testlets completed</div>
					<c:if test="${not empty ELArecord}">
					<div class='col iap-columnText'>${ELArecord.instTestscompleted} 
					</div></c:if>
					<c:if test="${not empty MathRecord}">
					<div class='col iap-columnText'>${MathRecord.instTestscompleted} 
					</div></c:if>
					<c:if test="${not empty ScienceRecord}">
					<div class='col iap-columnText'>${ScienceRecord.instTestscompleted} 
					</div></c:if>
				</div>
			</div> 		

		  </div>
	</div>
	</div>
	<br>
	<br>
</c:forEach>
<input type='hidden' id= "totalCount" value=${pageCount} />
<input type='hidden' id= "offSet" value=${offSet} />

<div class="bottom" id = "iapPagination">
	<div class="iap_pagination" id = "myiapPagination">
	    <a href="#" class="first" data-action="first">&laquo;</a>
	    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
	    <input type="text" readonly="readonly" />
	    <a href="#" class="next" data-action="next">&rsaquo;</a>
	    <a href="#" class="last" data-action="last">&raquo;</a>
	</div>
</div>
</c:if>
<c:if test="${not empty errorMessage}">
<p>${errorMessage}</p>
</c:if>
