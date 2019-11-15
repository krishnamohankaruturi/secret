	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
 
<style>
.studentList{
 padding-bottom: 3px;
}
</style>
 
<div class="_bcg">

	<div id="arts_monitor_noReport"></div>
	
	 <div class="arts_monitor_messages">
	 	<span id="arts_monitor_no_reactivate_params" class="error_message ui-state-error hidden"><fmt:message key='error.monitortestsession.sectionstatus.no.params'/></span>
		<span id="arts_monitor_reactivation_success" class="success_message ui-state-highlight hidden"><fmt:message key='label.monitortestsession.reactivation.success'/></span>            
        <span id="arts_monitor_reactivation_error" class="error_message ui-state-error hidden"><fmt:message key='label.monitortestsession.reactivation.error'/></span>
        <span id="arts_monitor_reactivation_permission_denied" class="error_message ui-state-error hidden"><fmt:message key='label.monitortestsession.reactivation.no.permission'/></span>
        
	 	<span id="arts_monitor_no_endtestsession_params" class="error_message ui-state-error hidden"><fmt:message key='error.endtestsession.no.params'/></span>
	 	<span id="arts_monitor_end_test_session_success" class="info_message ui-state-highlight hidden"><fmt:message key='label.endtestsession.success'/></span>
        <span id="arts_monitor_end_test_session_error" class="error_message ui-state-error hidden"><fmt:message key='label.endtestsession.error'/></span>	
        <span id="arts_monitor_end_test_session_permission_denied" class="error_message ui-state-error hidden"><fmt:message key='label.endtestsession.no.permission'/></span>
     </div>
	  
	<div class ="panel_full noBorder">	
		 <div class="top_info">

		</div> <!-- /top_info -->
		
		<span class="error_message ui-state-error hidden monitorTestSessionError" id="monitorTestSessionError"></span> <br />
		 
	    <div class="table_wrap">
			<div class="kite-table">
				<table id="arts_monitorTestSession"  class="responsive"></table>
				<div id="arts_monitorTestSessionPager" class="responsive"></div>			
			</div>
		</div>
		<div id="arts_monitorLegend" class="floatLeftDiv">
			<p>
				<img src="images/icons/monitor-answered.png" style="vertical-align: middle;" title="- Answered" alt="- Answered">- Answered,
				<img src="images/icons/monitor-unanswered.png" title="- Unanswered" alt="- Unanswered" style="vertical-align: middle;">- Unanswered,
				** - Not Available
			</p>
		</div>
		<div id="reactivateValidationDialog"  style="display:none;" title="Test Reactivation Validation Error">
			<p>Current system policy does not permit reactivation of the selected test stage for the following students:</p>
			<p id="nonEligibleStudentList"></p>
			<p>The selected test stage will be reactivated for all students not listed in this message.</p>
			<p>Click the OK button to continue, or Cancel to return to the Test Monitor page.</p>
		</div>
		<div id="reactivateConfirmationDialog"  style="display:none;" title="Confirm Test Reactivation">
			<p id="reactivateConfirmaionMessage"></p>
			<p>Click the OK button to continue, or Cancel to return to the Test Monitor page.</p>
		</div>
		<div id="arts_performanceMonitorButtonDiv" class="floatRightDiv">
			<input type="button" id="arts_monitor_progressRefresh" value="Refresh" class="btn_blue" title="Refresh"/>
			<input type="button" id="arts_monitor_reactivate" value="Reactivate" class="btn_blue" title="Refresh" disabled="disabled"/>
			<input type="button" id="arts_monitor_endTestSession" value="End Test Session" title="End Test Session" class="btn_blue"/>		
		</div>
	</div>
	<br />
</div>


<%@ include file="/jsp/test/monitorInclude.jsp" %>