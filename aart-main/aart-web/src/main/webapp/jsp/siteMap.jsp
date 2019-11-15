<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript"
	src="<c:url value='/js/configuration/batchStudentTracker.js'/>">
	
</script>
<div class="_bcg">
	<div class="wrap_bcg">
		<div class="welcome">
			<form>
				<security:csrfInput />
				<br>
				<br>
				<ul>
					<li><fmt:message key="label.nav.aartmanagement" />
						<ul>
							<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<fmt:message
									key="label.nav.assessmentprogrammanagement" />
					<security:authorize access="hasAnyRole('PERM_MANAGEORGASSESSPROG_VIEW','PERM_ASSESSPROGPARTIC_VIEW')">
								<ul>
									<security:authorize
										access="hasRole('PERM_MANAGEORGASSESSPROG_VIEW')">
										<li><a
											href="<c:url value='/manageOrgAssessmentPrograms.htm'/>"><fmt:message
													key="label.nav.manageAssessmentContract" /></a></li>
									</security:authorize>
									<security:authorize
										access="hasRole('PERM_ASSESSPROGPARTIC_VIEW')">
										<li><a
											href="<c:url value='/getAssessmentProgramParticipation.htm'/>"><fmt:message
													key="label.nav.assessmentProgramParticipation" /></a></li>
									</security:authorize>
								</ul>
					</security:authorize>					
							</li>

						</ul></li>

					<br />
					<li><fmt:message key="label.nav.dataupload" />
						
							<security:authorize access="hasRole('UPLOAD_WEBSERVICE')">
							<ul>
								<li>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<a
									href="<c:url value='/immediateUpload.htm'/>"><fmt:message
											key="label.nav.webservice" /></a>
								</li>
								</ul>
							</security:authorize>
						
						</li>

					<br />
					<br />
					<li><fmt:message key="label.nav.test" />
						<ul>
							<li>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<fmt:message
									key="label.nav.testresults" />
								<ul>
									<li>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;<a href="rosterReporting.htm"><fmt:message
												key="label.nav.rosterreporting" /></a>
									</li>
								</ul>
							</li>
						</ul></li>
				</ul>
			</form>
		</div>
	</div>
</div>




