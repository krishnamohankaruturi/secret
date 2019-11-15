<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/jsp/include.jsp"%>

<style>
h2 {
	color: #0E76BC;
	font-size: 1em;
	font-weight: 300;
	margin-top: 15px;
}

h1 {
	color: #646567;
	font-size: 14pt;
	font-weight: 300;
}

.ui-tabs .ui-tabs-panel {
	padding: 0;
}

#firstContactResponseTabs .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active, a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover{
	border:none;
	background:none;
}
#firstContactResponseTabs .ui-widget-header{	
	background:none;
}

</style>

<div id="section0" data-tabid="0" class="fcsSectionContainer" data-pagenumber="0" data-pageindex="0">
	<br />
	<div class="messages">
		<span class="error_message ui-state-error selectAllLabels hidden" id="selectAllLabelsFirstComplete"><fmt:message
				key="error.firstContact.selectAllLabels" /></span>
	</div>
	<div id="mystyle" class="headerStyle">
		<div class="topInfoLeft" style="float: left; padding: 1%">
			<label class="subsectionTitleComplete">Ready to Submit</label>
		</div>
		<div class="topInfoRight">
			<security:authorize access="!(hasRole('VIEW_FIRST_CONTACT_SURVEY') && !hasRole('EDIT_FIRST_CONTACT_SURVEY'))">
				<button type="button" id="submitSurveyButton" class="btn_blue submitSurvey"
					style="width: auto; margin-top: 10px !important;">Submit Survey</button>
			</security:authorize>
		</div>
	</div>
	<hr style="width: 100%; height: 1px;" />
	<br />
	<p class="preSubmitSurveyMessage" style="line-height: 1.5; font-size: 14pt;">
		Your responses have been saved but not submitted.<br /> Please press the Submit Survey button to submit your responses.
	</p>
	<h1 class="postSubmitSurveyMessage" style="line-height: 1.5; font-size: 14pt; display: none;">Responses to the survey have been successfully submitted.</h1>
</div>