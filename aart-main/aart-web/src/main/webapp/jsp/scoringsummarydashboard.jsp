<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value='css/scoringsummarydashboard.css'/>" type="text/css"/>
<script type="text/javascript" src="<c:url value='js/scoringsummarydashboard.js'/>"></script>

<br/>
<div>
	<div id="scoring_buttons">
		<input type="button" id="selectOrgForScoringSummary" value="View a school" class="panel_btn nextButton hidden">
		<input type="button" id="backToDefaultOrgForScoringSummary" value="Back" class="panel_btn nextButton hidden">
	</div>
	<div id="scoring_headers">
		<div id="scoring_orgLabel"></div>
		<div id="scoring_asOf">As of: <span class="time">${asOfString}</span></div>
	</div>
	<div class="scoringSummary">
		<div class="table_wrap" id ='scoringSummaryId'>
			<div class="kite-table">
				<table id="scoringSummaryTable" class="responsive"></table>
			</div>
			<div id="scoringContextSwitchPopup" class="_bcg config hidden">
				<div id="scoringOrganizationFilterDropdowns">
					<div id="scoringOrganizationOrgFilter" style="margin: 10px 0px 2% 2%;">
						<label id="scoringOrganizationOrgIdLabel" for="scoringOrganizationOrgId">District:</label>&nbsp;<select id="scoringOrganizationOrgId"><option value="">Select</option></select>
					</div>
				</div>
				<div id="scoringSummaryOrgErrorMsg" style="color: red;"></div>
			</div>
		</div>
		
		<div>
			<div>Data updated twice daily - approximately noon and midnight Central Time.</div>
			<input style="float:right" type="button" id="scoringSummaryExtractButton" value="Download Extract" class="panel_btn nextButton">
		</div>
	</div>
</div>