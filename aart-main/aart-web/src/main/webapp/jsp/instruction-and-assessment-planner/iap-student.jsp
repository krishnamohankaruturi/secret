<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<c:set var="studentFirstContactComplete" value="${student.firstContact == 'Completed'}"/>
<c:set var="canModifyIAP" value="${user.IAPModificationStatus == true and studentFirstContactComplete}"/>
<c:set var="isScience" value="${subject.abbreviatedName == 'Sci'}"/>

<%-- set some label text for later --%>
<c:choose>
	<c:when test="${isScience}">
		<c:set var="claimLabel" value="Core Idea"/>
		<c:set var="conceptualAreaLabel" value="Topic"/>
	</c:when>
	<c:otherwise>
		<c:set var="claimLabel" value="Claim"/>
		<c:set var="conceptualAreaLabel" value="Conceptual Area"/>
	</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${student != null}">
	<link rel="stylesheet" href="<c:url value='/css/instruction-and-assessment-planner.css'/>" type="text/css" />
	
	<c:set var="clickToSeeText" value="Click to view ${student.legalFirstName} ${student.legalLastName}'s"/>
	<div>
		<a href="#" onclick="backToIAPHome()"> ‹back </a>
	</div>
	<div id="iap-content" class="tab-content">
		<input id="studentId" type="hidden" value="${student.id}"/>
		<input id="enrollmentsRostersId" type="hidden" value="${enrollmentsRostersId}"/>
		<input id="rosterId" type="hidden" value="${rosterId}"/>
		<input id="otwId" type="hidden" value="${otw.otwId}"/>
		<input id="contentAreaId" type="hidden" value="${subject.id}"/>
		<input id="gradeLevel" type="hidden" value="${studentGrade}"/>
		<div class="iap-header">
			<span style="float: left;">
				 ${districtName} / ${schoolName} / ${subject.name}
			</span>
			<span class="window-text">
				${otw.instructionPlannerDisplayName}
			</span>
			<br/><br/><br/>
			<div>
				<div class="student-info">
					<span class="student-name">
						${student.legalLastName},&nbsp;${student.legalFirstName}
						<c:if test="${not empty student.legalMiddleName}">
							&nbsp;${fn:substring(student.legalMiddleName, 0, 1)}.
						</c:if>
					</span>
					<br/><br/>
					<span class="regular-text">
						State&nbsp;ID:&nbsp;${student.stateStudentIdentifier}
					</span>
				</div>
				
				<div class="iap-student-icons">
					<security:authorize access="hasAnyRole('VIEW_FIRST_CONTACT_SURVEY', 'EDIT_FIRST_CONTACT_SURVEY')">
						<figure class="iap-iconandtext">
							<c:choose>
								<c:when test="${not studentFirstContactComplete}">
									<img class="header-icon"
										id="fcsImage"
										alt="${clickToSeeText} First Contact Survey"
										title="${clickToSeeText} First Contact Survey"
										src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/AlertGray.svg'/>"/>
								</c:when>
								<c:otherwise>
									<img class="header-icon"
										id="fcsImage"
										alt="${clickToSeeText} First Contact Survey"
										title="${clickToSeeText} First Contact Survey"
										src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/CheckmarkBox-Gray.svg'/>"/>
								</c:otherwise>
							</c:choose>
							<figcaption class="caption">First Contact Survey</figcaption>
						</figure>
					</security:authorize>
					<figure class="iap-iconandtext">
						<img class="header-icon"
							id="pnpImage"
							alt="${clickToSeeText} Personal Needs Profile"
							title="${clickToSeeText} Personal Needs Profile"
							src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/person-Gray.svg'/>"/>
						<figcaption class="caption">PNP Profile</figcaption>
					</figure>
					<figure class="iap-iconandtext">
						<img class="header-icon"
							id="credentialsImage"
							alt="${clickToSeeText} credentials"
							data-toggle="popover"
							data-username="${student.username}"
							data-password="${student.password}"
							src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/lock-Gray.svg'/>"/>
						<figcaption class="caption">Credentials</figcaption>
					</figure>
					<c:if test="${subject.abbreviatedName == 'ELA'}">
						<figure class="iap-iconandtext">
							<img class="header-icon"
								id="themesImage"
								alt="${clickToSeeText} sensitive themes"
								data-toggle="popover"
								src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/book.svg'/>"/>
							<figcaption class="caption">Themes</figcaption>
						</figure>
					</c:if>
					<figure class="iap-iconandtext">
						<a id="printLink" target="_blank" href="<c:url value='printIAP.htm'/>?studentId=${student.id}&rosterId=${rosterId}&enrollmentRosterId=${enrollmentsRostersId}">
							<img class="header-icon" src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/print.svg'/>"/>
						</a>
						<figcaption class="caption">Print</figcaption>
					</figure>
					<c:if test="${not empty previousWindow}">
						<figure class="iap-iconandtext">
							<a id="printLink" target="_blank" href="<c:url value='printIAP.htm'/>?studentId=${student.id}&rosterId=${rosterId}&enrollmentRosterId=${enrollmentsRostersId}&operationalTestWindowId=${previousWindow.otwId}">
								<img class="header-icon" src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/leaf.svg'/>"/>
							</a>
							<figcaption class="caption">Fall Essential Element Status Report</figcaption>
						</figure>
					</c:if>
				</div>
			</div>
			<br/>
			<div id="directions">
				<c:choose>
					<c:when test="${not empty otw.instructionPlannerDirections}">
						${otw.instructionPlannerDirections}
					</c:when>
					<c:otherwise>
						Select an essential element and linkage level.
					</c:otherwise>
				</c:choose>
			</div>
			<br/>
			<div class="icon-legend">
				<div class="icon-legend-item">
					<div class="icon-legend-image-container instruction-in-progress">
						<img class="legend-icon-img" src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/transparentBlackArrowBlueBox.svg'/>"/>
					</div> 
					<div class="icon-legend-text">Instruction In Progress</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container testlet-assigned">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/flagBox-transparent.svg'/>"/>
					</div> 
					<div class="icon-legend-text">Testlet Assigned</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container testing-in-progress">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/timerBox-transparent.svg'/>"/>
					</div>
					<div class="icon-legend-text">Testing In Progress</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container complete">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg'/>"/>
					</div>
					<div class="icon-legend-text">Complete</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container recommended-linkage-level">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/WhiteRibbonTransparentBox.svg'/>"/>
					</div>
					<div class="icon-legend-text">Recommended Linkage Level</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container mastery-demonstrated">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/WhiteStarTransparentBox.svg'/>"/>
					</div>
					<div class="icon-legend-text">Mastery Demonstrated</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container mastery-not-demonstrated">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/WhiteXTransparentBox.svg'/>"/>
					</div>
					<div class="icon-legend-text">Mastery Not Demonstrated</div>
				</div>
				<div class="icon-legend-item">
					<div class="icon-legend-image-container results-not-available">
						<img class="legend-icon-img" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/minusBoxTransparent.svg'/>"/>
					</div>
					<div class="icon-legend-text">Results Not Available</div>
				</div>
			</div>
		</div>
		
		<hr/><br/>
		
		<div style="clear: both;"></div>
		
		<c:if test="${not studentFirstContactComplete}">
			<div>The First Contact survey for this student is not submitted. The survey must be submitted before creating a plan.</div>
			<br/>
		</c:if>
		
		<c:choose>
		<c:when test="${canModifyIAP}">
			<div id="iap-main-content">
		</c:when>
		<c:otherwise>
			<div id="iap-main-content" style="background-color:lightgray; opacity:0.7;">
		</c:otherwise>
		</c:choose>
			
			<c:set var="previousCriteria" value="${null}" />
			<c:set var="previousClaimId" value="${null}" />
			<c:set var="previousConceptualAreaId" value="${null}" />
			<c:set var="emptyCriteriaOutputted" value="${false}" />
			<c:forEach items="${EEList}" var="ee">
				<c:if test="${isInstructionallyEmbeddedModel and not empty ee.criteria and ee.criteria != previousCriteria}">
					<c:set var="previousClaimId" value="${null}" />
					<c:set var="previousConceptualAreaId" value="${null}" />
					<c:if test="${previousCriteria != null}">
						<%-- close off the element that we opened --%>
						</fieldset><br/>
					</c:if>
					<fieldset class="criteria" data-criteria="${ee.criteria}">
						<legend>${ee.criteriaText}</legend>
					<c:set var="previousCriteria" value="${ee.criteria}" />
					<div class="completion-container">
						<figure class="iap-iconandtext">
							<c:choose>
								<c:when test="${blueprintCriteriaCompletionMap[ee.criteria]}">
									<div class="completion-image-container">
										<img class="completion-image" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg'/>" />
									</div>
									<figcaption class="caption">Complete</figcaption>
								</c:when>
								<c:otherwise>
									<div class="completion-image-container"></div>
									<figcaption class="caption">Not Complete</figcaption>
								</c:otherwise>
							</c:choose>
						</figure>
					</div>
				</c:if>
				
				<%-- these start where there is no criteria anymore, so we're safe to assume all the rest have no criteria, either --%>
				<c:if test="${empty ee.criteria}">
					<c:if test="${isInstructionallyEmbeddedModel and previousCriteria != null}">
						<%-- close off the element that we opened --%>
						</fieldset><br/>
					</c:if>
					
					<c:if test="${isInstructionallyEmbeddedModel and not emptyCriteriaOutputted and previousCriteria != null}">
						<fieldset class="criteria" data-criteria="">
							<legend>
								The Essential Elements below are available for instruction for your student.
								Although they do not count towards blueprint requirements, they may be beneficial for your student’s educational goals.
							</legend>
							<c:set var="emptyCriteriaOutputted" value="${true}" />
					</c:if>
					
					<c:set var="previousCriteria" value="${null}" />
				</c:if>
				
				<c:if test="${previousClaimId != ee.claimId or previousConceptualAreaId != ee.conceptualAreaId}">
					<div>
						<div class="claim-text-container">
						<c:if test="${previousClaimId != ee.claimId}">
							<c:if test="${previousClaimId != null}">
								<br/>
							</c:if>
							<div class="claim">
								${claimLabel}: ${ee.claimContentCode} ${ee.claimDescription}
							</div>
						</c:if>
						<c:if test="${previousConceptualAreaId != ee.conceptualAreaId}">
							<c:if test="${previousConceptualAreaId != null and previousClaimId == ee.claimId}">
								<br/>
							</c:if>
							<div class="conceptual-area">
								${conceptualAreaLabel}: ${ee.conceptualAreaContentCode} ${ee.conceptualAreaDescription}
							</div>
						</c:if>
						</div>
						<div style="clear:both;"></div>
					</div>
					<br/>
   				</c:if>
   				
   				<c:set var="previousClaimId" value="${ee.claimId}" />
				<c:set var="previousConceptualAreaId" value="${ee.conceptualAreaId}" />
				
				<div class="container">
					<div class="header-row row">
						<div class="col-lg-2">Essential Element</div>
						<c:forEach items="${linkageLevels}" var="ll">
							<div class="col-lg">
								${fn:escapeXml(ll.displayName)}
								<c:if test="${ee.EE.recommendedLinkageLevelName == ll.levelName}">
									<img class="recommended-level" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/WhiteRibbonTransparentBox.svg'/>"/>
								</c:if>
							</div>
						</c:forEach>
					</div>
					
					<div class="row ee-row">
						<input type="hidden" name="eeId" value="${fn:escapeXml(ee.EE.id)}"/>
						<input type="hidden" name="eeContentCode" value="${fn:escapeXml(ee.EE.contentCode)}"/>
						<input type="hidden" name="isWriting" value="${ee.isWritingTestlet}"/>
						<div class="col-lg-2">
							<div class="ee-content-code">
								${ee.EE.contentCode}
							</div>
							<div class="ee-desc">
								${ee.EE.description}
							</div>
						</div>
						<c:if test="${not empty ee.linkageLevels}">
							<c:forEach items="${linkageLevels}" var="ll">
								<c:set var="displayCard" value="false"/> <%-- display the card --%>
								<c:set var="eeLLName" value=""/>
								<c:set var="eeLLShortDesc" value=""/>
								<c:forEach items="${ee.linkageLevels}" var="eeLL">
									<c:if test="${ll.levelName == fn:escapeXml(eeLL.linkagelabel)}">
										<c:set var="displayCard" value="true"/>
										<c:set var="eeLLName" value="${ll.levelName}"/> <%-- this is for the request parameter to the popover, it must match the system, not the display --%>
										<c:set var="eeLLShortDesc" value="${fn:escapeXml(eeLL.linkagelevelshortdesc)}"/>
									</c:if>
								</c:forEach>
								<c:choose>
									<c:when test="${displayCard}">
										<div class="col-lg ee-col">
											<input type="hidden" name="eeLinkageLevelName" value="${eeLLName}"/>
											<div class="card border-dark">
												<div class="card-body" data-toggle="popover" role="button" tabindex="0">
													<div class="ll-short-desc">${eeLLShortDesc}</div>
													<img class="three-dot-menu" src="<c:url value='/images/3-dot-menu.png'/>" />
												</div>
												<c:choose>
													<c:when test="${not empty ee.itiEntries}">
														<c:set var="persistingItiEntry" value="${null}"/>
														<c:forEach items="${ee.itiEntries}" var="itiEntry">
															<%--
															we don't want records from other linkage levels or ones that don't mean much to the user anymore,
															so we filter out the canceled and no-testlet-assigned entries because they would look the same as if the user took no action
															--%>
															<c:if test="${itiEntry.linkageLevel == eeLLName
																	and itiEntry.statusCode != 'CANCELED'
																	and itiEntry.statusCode != 'COMPLETED_WITH_NO_TESTLET'}">
																<c:set var="persistingItiEntry" value="${itiEntry}"/>
															</c:if>
														</c:forEach>
														
														<c:set var="classToAddToCard" value=""/>
														<c:set var="footerStatusImgSrc" value="${null}"/>
														<c:set var="footerText" value=""/>
														<c:set var="footerDate" value="${null}"/>
														<c:set var="footerMasteryImgSrc" value="${null}"/>
														<c:choose>
															<c:when test="${persistingItiEntry == null}">
																<c:set var="classToAddToCard" value="blank"/>
																<c:set var="footerStatusImgSrc" value="${null}"/>
																<c:set var="footerText" value=""/>
																<c:set var="footerDate" value="${null}"/>
																<c:set var="footerMasteryImgSrc" value="${null}"/>
															</c:when>
															<c:when test="${persistingItiEntry.statusCode == 'STARTED'}">
																<c:set var="classToAddToCard" value="instruction-in-progress"/>
																<c:set var="footerStatusImgSrc" value="/images/icons/instruction-and-assessment-planner/svg/transparentBlackArrowBlueBox.svg"/>
																<c:set var="footerText" value="Instruction<br/>In Progress"/>
																<c:set var="footerDate" value="${persistingItiEntry.createdDate}"/>
																<c:set var="footerMasteryImgSrc" value="${null}"/>
															</c:when>
															<c:when test="${persistingItiEntry.statusCode == 'COMPLETED_WITH_TESTLET'}">
																<c:choose>
																	<c:when test="${persistingItiEntry.studentsTestsStatus == 'unused'}">
																		<c:set var="classToAddToCard" value="testlet-assigned"/>
																		<c:set var="footerStatusImgSrc" value="/images/icons/instruction-and-assessment-planner/svg/flagBox-transparent.svg"/>
																		<c:set var="footerText" value="Testlet<br/>Assigned"/>
																		<c:set var="footerDate" value="${persistingItiEntry.confirmDate}"/>
																		<c:set var="footerMasteryImgSrc" value="${null}"/>
																	</c:when>
																	<c:when test="${persistingItiEntry.studentsTestsStatus == 'inprogress'}">
																		<c:set var="classToAddToCard" value="testlet-in-progress"/>
																		<c:set var="footerStatusImgSrc" value="/images/icons/instruction-and-assessment-planner/svg/timerBox-transparent.svg"/>
																		<c:set var="footerText" value="Testing<br/>In Progress"/>
																		<c:set var="footerDate" value="${persistingItiEntry.studentsTestsStartTime}"/>
																		<c:set var="footerMasteryImgSrc" value="${null}"/>
																	</c:when>
																	<c:when test="${persistingItiEntry.studentsTestsStatus == 'complete'}">
																		<c:set var="classToAddToCard" value="testlet-complete"/>
																		<c:set var="footerStatusImgSrc" value="/images/icons/instruction-and-assessment-planner/svg/whiteCheckmark.svg"/>
																		<c:set var="footerText" value="Testlet<br/>Complete"/>
																		<c:set var="footerDate" value="${persistingItiEntry.studentsTestsEndTime}"/>
																		<c:choose>
																			<c:when test="${ee.isWritingTestlet}">
																				<c:set var="footerMasteryImgSrc" value="images/icons/instruction-and-assessment-planner/svg/minusBoxTransparent.svg"/>
																			</c:when>
																			<c:otherwise>
																				<%-- not writing testlet --%>
																				<c:choose>
																					<c:when test="${empty persistingItiEntry.mastered}">
																						<c:set var="footerMasteryImgSrc" value="images/icons/instruction-and-assessment-planner/svg/minusBoxTransparent.svg"/>
																					</c:when>
																					<c:when test="${persistingItiEntry.mastered}">
																						<c:set var="footerMasteryImgSrc" value="images/icons/instruction-and-assessment-planner/svg/WhiteStarTransparentBox.svg"/>
																					</c:when>
																					<c:otherwise>
																						<c:set var="footerMasteryImgSrc" value="images/icons/instruction-and-assessment-planner/svg/WhiteXTransparentBox.svg"/>
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																	</c:otherwise>
																</c:choose>
															</c:when>
														</c:choose>
														<div class="card-footer border-dark tile-bottom-content ${classToAddToCard}">
															<div class="card-footer-image-container">
																<c:if test="${footerStatusImgSrc != null}">
																	<img src="<c:url value='${footerStatusImgSrc}'/>"/>
																</c:if>
															</div>
															<div class="card-footer-text">
																${footerText}
															</div>
															<div class="card-footer-date-text">
																<fmt:formatDate value="${footerDate}" pattern="MM/dd"/>
																<c:if test="${footerMasteryImgSrc != null}">
																	<img class="mastery-image" src="<c:url value='${footerMasteryImgSrc}'/>"/>
																</c:if>
															</div>
														</div>
														<%-- remove the variables that we used, just to be safe --%>
														<c:remove var="classToAddToCard"/>
														<c:remove var="footerStatusImgSrc"/>
														<c:remove var="footerText"/>
														<c:remove var="footerDate"/>
														<c:remove var="footerMasteryImgSrc"/>
														
													</c:when>
													<c:otherwise>
														<div class="card-footer border-dark tile-bottom-content blank"></div>
													</c:otherwise>
												</c:choose>
											</div>
						   				</div>
					   				</c:when>
					   				<c:otherwise>
					   					<div class="col-lg ee-col">
					   						<div class="empty"></div>
				   						</div>
					   				</c:otherwise>
			   					</c:choose>
							</c:forEach>
						</c:if>
			   		</div>
			   		
		   		</div>
			</c:forEach>
		</div>
		<%-- just to add some height so the bottom row of popovers doesn't trail off the viewable page --%>
		<br/><br/><br/>
	</div>
	
	<div id="viewStudentDetailsDiv" style="display:none;"></div>
	<div id="accessProfileDiv" style="display:none;"></div>
	<div id="firstContactViewDiv" style="display:none;"></div>
	
	<script type="text/javascript" src="<c:url value='/js/instruction-and-assessment-planner/iap-common.js'/>"></script>
	
	<script type="text/javascript">
		// for the PNP dialog, mainly
		var studentInfo = {
			id: '<c:out value="${student.id}" escapeXml="false"/>',
			studentFirstName: '<c:out value="${student.legalFirstName}" escapeXml="false"/>',
			studentMiddleName: '<c:out value="${student.legalMiddleName}" escapeXml="false"/>',
			studentLastName: '<c:out value="${student.legalLastName}" escapeXml="false"/>',
			stateStudentIdentifier: '<c:out value="${student.stateStudentIdentifier}" escapeXml="false"/>',
			gender: '${student.gender == 1 ? "Male" : (student.gender == 0 ? "Female" : "-")}',
			dateOfBirth: '${studentDateOfBirth}',
			gradeCourseName: '${studentGrade}'
		};
		window.localStorage.setItem(studentInfo.id, JSON.stringify(studentInfo));
		
		var popoverCloseButton = ' <button type="button" class="close" aria-hidden="true">&times;</button>';
		
		function isPopoverOpen(jqPopoverObj){
			var popoverData = jqPopoverObj.data('bs.popover');
			if (typeof (popoverData) !== 'undefined'){
				return $(popoverData.tip).is(':visible');
			}
			return false;
		}
		
		/*
		Updates a popover's title and content.
		Any call to this function must be followed by a .popover('show') to actually display the updated content.
		*/
		function updatePopoverContent(jqPopoverObj, content, title){
			if (content != null && typeof(content) !== 'undefined'){
				jqPopoverObj.data('bs.popover').config.content = content;
			}
			if (title != null && typeof(title) !== 'undefined'){
				jqPopoverObj.data('bs.popover').config.title = title;
			}
		}
		
		function scrollToLastUserPosition(){
			var scrollLeft = window.sessionStorage.getItem('iap-scrollLeft');
			var scrollTop = window.sessionStorage.getItem('iap-scrollTop');
			
			if (scrollLeft != null){
				$(window).scrollLeft(scrollLeft);
			}
			if (scrollTop != null){
				$(window).scrollTop(scrollTop);
			}
			
			window.sessionStorage.removeItem('iap-scrollLeft');
			window.sessionStorage.removeItem('iap-scrollTop');
		}
		
		$(function(){
			scrollToLastUserPosition();
			
			<%--
			little hack to include this in the page...I don't like having this in the JSP...but meh
			ALSO: Note that these tags are preceeded by //, which is just a trick to get the IDE to stop warning about strange JS syntax.
			Since JSP tags are evaluated well before a DOM is created and JS executed, all that ends up getting inserted into the page is a blank comment
			--%>
			//<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">
				if ($('#directions .testMgmtLink').length > 0){
					$('#directions .testMgmtLink').wrap($('<a>').attr('href', '<c:url value="/viewTestSessions.htm"/>'));
				}
			//</security:authorize>
			
			$('.student-name').on('click', function(){
				openViewStudentPopup(studentInfo.id, studentInfo.stateStudentIdentifier, false,
						studentInfo.studentFirstName + ' ' + (studentInfo.studentMiddleName != null && studentInfo.studentMiddleName.length > 0 ? studentInfo.studentMiddleName + ' ' : '') + studentInfo.studentLastName);
			});
			
			$('#fcsImage').on('click', function(){
				viewFirstContactDetails(studentInfo.id, studentInfo.studentFirstName + ' ' + studentInfo.studentLastName);
			});
			
			$('#pnpImage').on('click', function(){
				accessProfileDetails(studentInfo.id, "${student.stateStudentIdentifier}", "${user.currentAssessmentProgramName}", studentInfo.studentFirstName + ' ' + studentInfo.studentLastName);
			});
			
			// universal events for the close buttons
			$('[data-toggle="popover"]').on('shown.bs.popover', function(){
				var $this = $(this);
				var $popoverElement = $($this.data('bs.popover').tip);
				$('button.close', $popoverElement).off('click').on('click', function(){
					$this.popover('hide');
					$this.focus();					
				});
				
			});
			
			$('#credentialsImage[data-toggle="popover"]').popover({
				trigger: 'manual',
				placement: 'bottom',
				html: true,
				title: 'Credentials' + popoverCloseButton,
				content: function(){
					var html = '<div>Username:&nbsp;&nbsp;' + $(this).data('username') + '</div>';
					html += '<div>Password:&nbsp;&nbsp;' + $(this).data('password') + '</div>';
					return html;
				}
			}).on('click', function(){
				$(this).popover('show');
			});
			
			$('#themesImage[data-toggle="popover"]').on('click', function(){
				var $popoverTriggerElement = $(this);
				var popoverData = $popoverTriggerElement.data('bs.popover');
				var loadingContent = 'Loading...';
				var title = 'Sensitive Themes';
				if (typeof (popoverData) === 'undefined'){
					$popoverTriggerElement.popover({
						trigger: 'manual',
						placement: 'bottom',
						html: true,
						title: (title == null || title == undefined || title.length === 0) ? '' : title,
						content: loadingContent
					});
				}
				
				if (!isPopoverOpen($popoverTriggerElement)){
					updatePopoverContent($popoverTriggerElement, loadingContent, title);
					$popoverTriggerElement.popover('show');
					
					// get the data again, just so we know it contains what we expect
					popoverData = $popoverTriggerElement.data('bs.popover');
					var popoverElementId = popoverData.tip.attributes.id.value;
					
					// get our popover content
					$.ajax({
						url: 'getSensitiveThemes.htm',
						method: 'GET',
						data: {
							studentId: $('#studentId').val(),
							contentAreaId: $('#contentAreaId').val()
						}
					}).done(function(data){
						// populate our popover element with the content once the it's back
						var visiblePopover = $('#' + popoverElementId + '.popover:visible');
						if (visiblePopover.length > 0){
							updatePopoverContent(visiblePopover, data, title + popoverCloseButton);
							visiblePopover.popover('show'); // show the updated content
						}
					}).fail(function(xhr){
						var visiblePopover = $('#' + popoverElementId + '.popover:visible');
						if (visiblePopover.length > 0){
							updatePopoverContent(visiblePopover, 'Error retrieving content', title + popoverCloseButton);
							visiblePopover.popover('show'); // show the updated content
						}
					});
				}
			}).on('keydown', function(e){
				var keyCode = e.which;
				// 13 is Enter, 32 is Space, these should trigger for keyboard navigation
				if (keyCode === 13 || keyCode === 32){
					$(this).click();
				} else if (keyCode === 27) {
					// they hit escape
					$(this).popover('hide').focus();
				}
			});
			
			<%--
			more of the // syntax trickery as noted above
			--%>
			//<c:if test="${canModifyIAP}">
				$('.card-body[data-toggle="popover"]').on('click', function(){
					var $popoverTriggerElement = $(this);
					var popoverData = $popoverTriggerElement.data('bs.popover');
					var loadingContent = 'Loading...';
					var cfdId = $popoverTriggerElement.closest('.ee-row').find('input[name="eeId"]').val();
					var contentCode = $popoverTriggerElement.closest('.ee-row').find('input[name="eeContentCode"]').val();
					var isWriting = $popoverTriggerElement.closest('.ee-row').find('input[name="isWriting"]').val() == 'true';
					var title = contentCode;
					if (typeof (popoverData) === 'undefined'){
						$popoverTriggerElement.popover({
							trigger: 'manual',
							html: true,
							title: (title == null || title == undefined || title.length === 0) ? '' : title,
							content: loadingContent
						});
					}
					
					if (!isPopoverOpen($popoverTriggerElement)){
						updatePopoverContent($popoverTriggerElement, loadingContent, title);
						$popoverTriggerElement.popover('show');
						
						// get the data again, just so we know it contains what we expect
						popoverData = $popoverTriggerElement.data('bs.popover');
						var popoverElementId = popoverData.tip.attributes.id.value;
						
						var linkageLevelName = $popoverTriggerElement.closest('.ee-col').find('input[type="hidden"][name="eeLinkageLevelName"]').val();
						
						var contentOfPopover = 'Error retrieving content.'; // content if the request isn't successful
						
						// get our popover content
						$.ajax({
							url: 'instruction-planner-popover-content.htm',
							method: 'GET',
							data: {
								studentId: $('#studentId').val(),
								enrollmentsRostersId: $('#enrollmentsRostersId').val(),
								rosterId: $('#rosterId').val(),
								otwId: $('#otwId').val(),
								contentAreaId: $('#contentAreaId').val(),
								gradeLevel: $('#gradeLevel').val(),
								contentFrameworkDetailId: cfdId,
								contentCode: contentCode,
								linkageLevelName: linkageLevelName,
								isWriting: isWriting
							}
						}).done(function(data){
							contentOfPopover = data;
						}).fail(function(xhr, textStatus, errorThrown){
							console.error('Error getting popover content - ' + textStatus + ' - ' + errorThrown);
						}).always(function(){
							// populate our popover element with the content
							var visiblePopover = $('#' + popoverElementId + '.popover:visible');
							if (visiblePopover.length > 0){
								updatePopoverContent(visiblePopover, contentOfPopover, title + popoverCloseButton);
								visiblePopover.popover('show'); // show the updated content
							}
						});
					}
				}).on('keydown', function(e){
					var keyCode = e.which;
					// 13 is Enter, 32 is Space, these should trigger for keyboard navigation
					if (keyCode === 13 || keyCode === 32){
						$(this).click();
					} else if (keyCode === 27) {
						// they hit escape
						$(this).popover('hide').focus();
					}
				});
			//</c:if>
		});
		
		function setInSessionStorage(storageItemName, storageItemValue) {
			window.sessionStorage.setItem(storageItemName, storageItemValue);			
		}
		function backToIAPHome(){
			setInSessionStorage("reloadIAPHomePage", true);
			window.location.href = "instruction-planner.htm";
		}
	</script>
	
	<script type="text/javascript" src="<c:url value='/js/instruction-and-assessment-planner/iap-common.js'/>"></script>

</c:when>
<c:otherwise>
	<div>Data was not correct.</div>
</c:otherwise>
</c:choose>