<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
	<div id="saveAcademicDatesSuccess" class="hidden" style="color: green; padding-left: 25px; font-size: 15px;"><fmt:message key="label.academicdates.save.success" /></div>
	<div id="saveAcademicDatesError"  class="error hidden" style="padding-left: 25px; font-size: 15px;"></div>
	
	<input id="saveTopBtn" type="button" class="btn_blue" value="Save"/>        
	<p>
	<span style="padding-left: 25px; font-size: 15px;">For each state listed, select the months and dates that represent the first and last days of the academic year in that state.</span>
	</p>

		<div id=orgAcademicYearContainer class="kite-table">
			<table class="responsive" id="orgAcademicYearTable"></table>
		</div>

	<p> &nbsp;</p>
	<p> &nbsp;</p>
	
	<input id="saveBottomBtn" type="button" class="btn_blue" value="Save"/>
	<p>&nbsp;</p>
</div>