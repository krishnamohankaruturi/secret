<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/shortdurationtesting.css'/>" type="text/css" />


<script type="text/javascript" src="<c:url value='/js/shortdurationtesting.js'/>"></script>
<br>
<div>
	<div class="leftText">
		<div id="orgLabelShortDuration" ></div> <div id="orgNameShortDuration"></div>
	</div>
	<div class="buttons">
		<input type="button" id="todayShortDuration" value="Today" class="panel_btn nextButton btn-right">&nbsp;
		<input type="button" id="yesterdayShortDuration" value="Prior Day" class="panel_btn nextButton btn-right">&nbsp;
		<input type="button" id="yearShortDuration" value="School Year" class="panel_btn nextButton btn-right">
	</div>
</div>
<br class="clear"/>
<div>

<div class="leftText">
	<p id="shortDurationText"><span id="shortDurationCount"></span> tests completed under short duration <span id="shortDurationDate"></span></p>
</div> 

<div id="shortDurationUpdated">
	<p>As of: <span id="shortDurationUpdatedDate">${asOfString}</span></p>
</div>
</div>
<br class="clear"/>

<div id = "errormsgs">
	<div class ="table_wrap">
		<div class="kite-table">		
			<table id="shortDurationTable"  class="responsive" role='presentation'></table>
			<div id="pShortDurationTable" class="responsive"></div>
		</div>
	</div>
</div>
<div>
	<p>
		Data updated twice daily – approximately noon and midnight Central Time
	</p>
	<input style="float:right" type="button" id="testingshortDurationExtractButton" value="Download Extract" class="panel_btn nextButton">
</div>