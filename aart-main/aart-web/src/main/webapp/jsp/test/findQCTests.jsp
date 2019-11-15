<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript"
	src="<c:url value='/js/test/findQCTests.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/findQCTests.js'/>"> </script>
<div>
	<div id="previewQCTestDiv"></div>
	<div>
		<div style="text-align:right">						
			<input type="button" id="markQcCompleteButton" value="<fmt:message key='label.testmanagement.markqccomplete'/>" class="btn_blue markqccomplete" />
			<input type="button" id="removeQCCompleteButton" value="<fmt:message key='label.testmanagement.removeqccomplete'/>" class="btn_blue removeQCComplete" />
		</div>
	<div id="radioError1" style="color:red;"></div>
	<div class="table_wrap">
		<div id="resultsQCSection" class="kite-table">
			<table class="responsive" id="testsQCTable"></table>
			<div id="naMessage" class="hidden">
				<fmt:message key='label.tests.search.notApplicableMessage' />
			</div>
			<div id="testsQCPager"></div>
		</div>
		</div>
	</div>
	<div id="dialog-confirm"></div>
	<div id="RemoveDialog-confirm"></div>
</div>


