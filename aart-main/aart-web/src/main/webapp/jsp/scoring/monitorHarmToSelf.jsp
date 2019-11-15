<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<style>

#monitorSelfToHarm {
    text-align: center;
    color: #769846;
    font-size: 22px;
    font-weight: 400;
	}

</style>

<div id="monitorStudentView">
<input id="monitorHalfToSelfUserAccesslevel" type="hidden" value="${user.accessLevel}" />
	<div class="container">
	<div>
	<h1 id="monitorSelfToHarm">Monitor Suspected Harm To Self Student Responses</h1>
	</div>
		
	<div class="table_wrap">
			<div id="resultsMonitorSection" class="kite-table">
				<table class="responsive" id="monitorselftoharmtable"></table>

				<div id="monitorSelfToHarmPager"></div>
			</div>
		</div>
	
	</div>
</div>

<script>
$( document ).ready(function() {
	
});
</script>
