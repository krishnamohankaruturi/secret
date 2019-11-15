<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/testingsummarydashboard.css'/>" type="text/css" />

<script type="text/javascript" src="<c:url value='/js/testingsummarydashboard.js'/>"></script>
<br>
<div>
	<div id="buttons">
		<input type="button" id="selectOrgForTestingSummary" value="View a school" class="panel_btn nextButton">
		<input type="button" id="selectMultiOrgForTestingSummary" value="View all States" class="panel_btn nextButton">
		<input type="button" id="backToDefaultOrgForTestingSummary" value="Back to district" class="panel_btn nextButton">
	</div>
	<div id="headers">
		<div id="orgLabel" ></div>
		<div id="asOf">As of: <span class="time">${asOfString}</span></div>
		<br>
	</div>
	<div  class="testingSummary">
		<div class ="table_wrap" id = "testingSummaryId">
			<div class="kite-table">		
				<table id="testingSummaryTable"  class="responsive" role='presentation'></table>
			</div>
			<div id="testingContextSwitchPopup" class="_bcg config hidden">
				   <div id="organizationFilterDropdowns">
						<div id="organizationDistrictFilter" style="margin: 10px 0px 2% 2%;">
							<label for="organizationDistrictId">District:</label>&nbsp;<select id="organizationDistrictId"><option value="">Select</option></select>
						</div>
						<div id="organizationSchoolFilter" style="margin: 10px 0px 2% 2%;">
							<label for="organizationSchoolId">School:</label>&nbsp;<select id="organizationSchoolId"><option value="">Select</option></select>
						</div> 			
				   </div>
				   <div id="testingSummaryOrgErrorMsg" style="color: red;"></div>	  		
			</div>
		</div>
	</div>
	<div id="footnotes">
		<div style="float:left">Data updated twice daily - approximately noon and midnight Central Time.</div>
		<c:if test="${isKAP != null and isKAP}"><div style="clear:both;float:left">* KAP Interim Predictive counts are for most recent window.</div></c:if>
		<input style="float:right" type="button" id="testingSummaryExtractButton" value="Download Extract" class="panel_btn nextButton">
		<input style="float:right" type="button" id="multipleExtract" value="Download Extract" class="panel_btn nextButton">
	</div>
</div>