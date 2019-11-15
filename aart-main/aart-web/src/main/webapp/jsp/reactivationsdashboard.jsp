<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/reactivationsdashboard.css'/>" type="text/css" />


<script type="text/javascript" src="<c:url value='/js/reactivationsdashboard.js'/>"></script>
<br>
<div>
	<div class="leftText">
		<div id="orgLabelReactivations" ></div> <div id="orgNameReactivations"></div>
	</div>
	<div class="buttons">
		<input type="button" id="todayReactivations" value="Today" class="panel_btn nextButton btn-right">&nbsp;
		<input type="button" id="yesterdayReactivations" value="Prior Day" class="panel_btn nextButton btn-right">&nbsp;
		<input type="button" id="yearReactivations" value="School Year" class="panel_btn nextButton btn-right">
	</div>
</div>
<br class="clear"/>
<div>
<div class="leftText">
	<p id="reactivationsText"><span id="reactivationsCount"></span> Reactivations <span id="reactivationsDate"></span></p>
</div>
<div class="buttons">
	<input type="button" id="displayDetail" value="Student Detail" class="panel_btn nextButton btn-right">&nbsp;
	<input type="button" id="displaySummary" value="Summary" class="panel_btn nextButton btn-right">
</div>
</div>
<br class="clear"/>
<div id="reactivationsUpdated">
	<p>As of: <span id="reactivationsUpdatedDate">${asOfString}</span></p>
</div>
<div id = "reactivationsSummary">
	<div class ="table_wrap">
		<div class="kite-table">		
			<table id="reactivationsSummaryTable"  class="responsive" role='presentation'></table>
			<div id="pReactivationsSummaryTable" class="responsive" ></div>
		</div>
	</div>
</div>
<div id = "reactivationsDetail">
	<div class ="table_wrap">
		<div class="kite-table">		
			<table id="reactivationsDetailTable"  class="responsive" role='presentation'></table>
			<div id="pReactivationsDetailTable" class="responsive"></div>
		</div>
	</div>
</div>
<div>
	<p>
		Data updated twice daily – approximately noon and midnight Central Time
	</p>
	<input style="float:right" type="button" id="reactivationsSummaryExtractButton" value="Download Extract" class="panel_btn nextButton">
	<input style="float:right" type="button" id="reactivationsDetailExtractButton" value="Download Extract" class="panel_btn nextButton">
</div>