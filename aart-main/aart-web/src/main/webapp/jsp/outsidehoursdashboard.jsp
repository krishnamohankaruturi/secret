<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/outsidehoursdashboard.css'/>" type="text/css" />


<script type="text/javascript" src="<c:url value='/js/outsidehoursdashboard.js'/>"></script>
<br>
<div>
	<div class="leftText">
		<div id="orgLabelOutside" ></div> <div id="orgNameOutside"></div>
	</div>
	<div class="buttons">
		<input type="button" id="todayOutside" value="Today" class="panel_btn nextButton btn-right">&nbsp;
		<input type="button" id="yesterdayOutside" value="Prior Day" class="panel_btn nextButton btn-right">&nbsp;
		<input type="button" id="yearOutside" value="School Year" class="panel_btn nextButton btn-right">
	</div>
</div>
<br class="clear"/>
<div>
<div class="leftText">
	<p id="outsideText"><span id="outsideCount"></span> test sessions started or completed outside expected hours <span id="outsideDate"></span></p>
</div>
<div id="outsideUpdated">
	<p>As of: <span id="outsideUpdatedDate">${asOfString}</span></p>
</div>
</div>
<br class="clear"/>

<div id = "errormsg">
	<div class ="table_wrap">
		<div class="kite-table">		
			<table id="outsideHoursTable"  class="responsive" role='presentation'></table>
			<div id="pOutsideHoursTable" class="responsive"></div>
		</div>
	</div>
</div>
<div>
	<p>
		Data updated twice daily – approximately noon and midnight Central Time
		<br/>
		Does not include schools specified by the state
		<br/>
		<!--  Expected hours are Mon-Fri 6AM - 5PM local time zone-->
		Expected hours are <b><span id="outsideTime">${outsideTime}</span></b> local time zone
	</p>
	<input style="float:right" type="button" id="testingOutsideHoursExtractButton" value="Download Extract" class="panel_btn nextButton">
</div>