<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/jsp/include.jsp"%>
<div id="firstContactPageDiv" class="_bcg">

	<input id="pageNumber" value="${pageNumber}" type="text" class="hidden" /> 
	<input id="editSurveyCheck" value="${check}" type="text" class="hidden" />
	<input id="fcrvStudentId" value="${studentId}" type="text" class="hidden" />
	<input id="surveyId" value="${surveyId}" type="text" class="hidden" /> 
	<input id="surveyRootSections" value="${surveyRootSections}" type="text" class="hidden" /> 
	<input id="surveySubSections" value="${surveySubSections}" type="text" class="hidden" /> 
	<input id="noOfRootSections" value="${noOfRootSections}" type="text" class="hidden" /> 
	<input id="noOfSubSections" value="${noOfSubSections}" type="text" class="hidden" />
	<input id="pageIndex" value="${pageIndex}" type="text" class="hidden" />
	<input id="globalPageNumList" value="${globalPageNumList}" type="text" class="hidden" /> 
	<input id="allQuestionsRequired" value="${allQuestionsRequired}" type = "text" class="hidden" />
	<input id="fcsLastPageId" value="${fcsLastPageId}" type = "text" class="hidden" />
	
	<div id="welcomePageContentHeadder">
		<span style="float: left;"> <img alt="Kite" src="images/dlm_logo_final_registered_312x128.png" width="312" height="128">
		</span> <span class="fcsHeaderTitle"> First Contact Survey</span>
	</div>

	<div id="firstContactResponseTabs" class="panel_full">
		<ul class="tabs nav nav-tabs sub-nav">
			<li class="nav-item" ><a class="nav-link" href="#tabs_0">
					<div>
						<br />
						<fmt:message key="label.firstcontact.welcome" />
						<br />
					</div>
					<div class="pages page0"></div>
			</a></li>
			<c:forEach var="surveyRootSection" items="${surveyRootSections}">
				<li class="nav-item" ><a  class="nav-link"  href="#tabs_${surveyRootSection.sectionOrder}">
						<div>
							<c:out value="${surveyRootSection.surveySectionDescription}" escapeXml="false"></c:out>
						</div> <c:forEach var="surveySubSection" items="${surveySubSections}">
							<c:if test="${surveySubSection.parentSurveySectionId == surveyRootSection.id}">
								<div class="pages page${surveySubSection.globalPageNumber} pageIndex${surveySubSection.pageIndex}">
									<c:out value="${surveySubSection.surveySectionName}"></c:out>
								</div>
							</c:if>
						</c:forEach>
				</a></li>
			</c:forEach>

			<li class="nav-item" ><a href="#tabs_${fn:length(surveyRootSections) + 1}">
					<div>
						<br />
						<fmt:message key="label.firstcontact.complete" />
					</div>
					<div class="pages lastPage${fcsLastPageId} pageIndex${fcsLastPageId}"></div>
			</a></li>
		</ul>

		<div id="tabs_0">
			<jsp:include page="firstContactWelcomePage.jsp" />
		</div>

		<c:forEach var="surveyRootSection" items="${surveyRootSections}">
			<div id="tabs_${surveyRootSection.sectionOrder}" class="ui-tabs-hide">
				<c:forEach var="surveySubSection" items="${surveySubSections}">
					<c:if test="${surveySubSection.parentSurveySectionId == surveyRootSection.id}">
						<div id="section${surveySubSection.globalPageNumber}" data-tabid="${surveyRootSection.sectionOrder}"
							data-pageindex="${surveySubSection.pageIndex}" data-pagenumber="${surveySubSection.globalPageNumber}" class="fcsSectionContainer" style="display: none;">
							<br />
							<div class="messages">
								<span class="error_message ui-state-error selectAllLabels hidden selectAllLabelsFCS" id="selectAllLabelsFCS${surveySubSection.pageIndex}" > <fmt:message
										key="error.firstContact.selectAllLabels" /></span>
							</div>
							<div class="headerStyle">
								<div class="topInfoLeft">
									<label class="subsectionTitle">${surveySubSection.surveySectionName}</label>
								</div>
								<div id="${fn:replace(surveyRootSection.surveySectionName, ' ' ,'')}Buttons${surveySubSection.globalPageNumber}">
									<button type="button" id="fcsPreviousButton${surveySubSection.globalPageNumber}" data-pagenumber="${surveySubSection.globalPageNumber}"
										data-pageindex="${surveySubSection.pageIndex}" data-tabid="${surveyRootSection.sectionOrder}" class="btn_blue fcsPreviousButton">Previous</button>
									<button type="button" id="fcsNextButton${surveySubSection.globalPageNumber}" data-pagenumber="${surveySubSection.globalPageNumber}"
										data-pageindex="${surveySubSection.pageIndex}" data-tabid="${surveyRootSection.sectionOrder}" class="btn_blue fcsNextButton">Next</button>
								</div>
							</div>
							<div>${surveySubSection.instructionNote}</div>
							<div id="SectionContent_${surveySubSection.globalPageNumber}"
								data-surveysectionname="${surveySubSection.surveySectionName}" class="fcsSectionContent"></div>
							<br />
						</div>
					</c:if>
				</c:forEach>
			</div>
		</c:forEach>

		<div id="tabs_${fn:length(surveyRootSections) + 1}">
			<jsp:include page="firstContactCompletePage.jsp" />
		</div>
	</div>
</div>

<div id="dialog-confirm" style="display: none;"></div>

<script>
	$(function() {
		initializeFirstContactSurvey();
	}); // end of document ready
</script>
