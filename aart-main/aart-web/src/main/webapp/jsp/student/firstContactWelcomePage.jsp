<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/jsp/include.jsp"%>

<input id="studentId" value="${studentId}" type="text" class="hidden" />
<div id="WelcomePageSection0" data-tabid="0" class="fcsSectionContainer" data-pagenumber="0" data-pageindex="0">
	<div id="WelcomePageMyStyleDiv" class="headerStyle" style="display: inline-block;">
		<h2 style="color: #073758; font-size: 16pt; font-weight: bold;">
			<fmt:message key="label.firstcontact.survey.welcome" />
		</h2>
		<div id="nextButtonDiv">
			<input type="button" id="editSurveyButton" value="Edit Survey" data-tabid="0" data-pagenumber="0" data-pageindex="0"
				class="panel_btn fcsNextButton hidden" style="float: right; margin-top: -40px !important;" /> <input type="button"
				id="startSurveyButton" value="Start Survey" data-tabid="0" data-pagenumber="0" data-pageindex="0"
				class="panel_btn fcsNextButton hidden" style="float: right; margin-top: -40px !important;" />
		</div>
	</div>
	<br>
	<div id="welcomePageContent" style="width: 100%;">
		<img alt="Kite" src="images/dlm_logo_final_registered_312x128.png" width="312" height="128"> <br> <br>
		<div class="fcsHeader2">This survey asks questions about the characteristics of this student who is
			participating in the DLM alternate assessment. Topics include sensory and motor capabilities, computer access,
			communication, academics, and attention.</div>
		<div class="fcsHeader2">Your answers help determine how the DLM system should be customized to this student.
			Answers also help us determine plans for future development of the DLM system to better support all students.</div>
	</div>
	<br /> <b>Note: Each bubble indicates a page within a tab. Before submitting the survey, check each bubble to see
		if it is blue or yellow.</b> <br /> <br />
	<div style="padding: 5px 40px">
		<span style="background-color: #0e69fd;" class="pages"> </span> <i> - You have answered all questions on this page
		</i>
	</div>
	<div style="padding: 5px 40px">
		<span class="pages"> </span> <i> - <b>You have not answered all questions on this page</b></i>
	</div>
	
</div>




