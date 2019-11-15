<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<div>
	<ul id="viewTestCoordinationID" class="nav nav-tabs sub-nav">
		<li class="nav-item">
			<a class="nav-link" href="#tabs_ticketing" onClick="javascript:viewTestSessions(); return false;"  data-toggle="tab" role="tab" aria-controls="tabs_ticketing"><fmt:message key="label.testmanagement.viewTestSession" /></a>
		</li>
		<security:authorize access="hasRole('VIEW_DAILY_ACCESS_CODES')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_daccodes" onClick="javascript:viewDailyAccessCodes(); return false;" data-toggle="tab" role="tab" aria-controls="tabs_daccodes"><fmt:message key="label.testmanagement.viewDACCodes" /></a>
			</li>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content">
		<div id="tabs_ticketing" class="tab-pane" role="tabpanel">

		</div>
		<div id="tabs_daccodes" class="tab-pane" role="tabpanel">

		</div>		
	</div>
</div>

<div class="panel_full noBorder" style="padding-bottom: 32px">
	<div class="form action-bar">
	</div>
	<div class="tabTable">
		<div class="studentMessages studentARTSmessages">
			<span class="error_message ui-state-error permissionDeniedMessage hidden" id="testPsermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
			<span class="error_message ui-state-error selectAllLabels hidden validate requiredMessage" id="testRequiredMessage"><fmt:message key="error.config.required.fields"/></span>
		</div> 
		
	</div>
	
	<div id ="divViewTestSessions" class="hidden">
	</div>
		
	<div id ="divViewDailyAccessCodes" class="hidden">
		<div>
	    	<br/> <span id="select_dailyaccesscode_errors" class="info_message ui-state-error hidden"><fmt:message key='error.select.testsessions.dailyaccesscode' /></span>
		</div>
		<div id="searchFilterContainer" style="margin-top: -50px;">
		<form id="dacSearchFilterForm" name="dacSearchFilterForm" class="form">
			
			<div class="btn-bar">
				<div id="tsSearchFilterErrors" class="error"></div>
				<div id="tsSearchFilterMessage" style="padding:20px" class="hidden"></div> 
			</div><br/><br/>
			<span style="color: #94b54d; padding-left: 25px; padding-top: 20px">Select Assessment Program and the Test Day, the Daily Access Codes are sought for:</span><br/><br/>
			<div class="form-fields" style="padding-left: 80px">
				<label for="dacAssessmentPrograms" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
				<select id="dacAssessmentPrograms" class="bcg_select required" name="dacAssessmentPrograms">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" >
				<label for="dacTestDay" class="field-label">Test Day:<span class="lbl-required">*</span></label>			
				<select id="dacTestDay" class="bcg_select required" name="dacTestDay">
					<option value="">Select</option>
				</select>
				&nbsp;&nbsp;<span id="selectTestDayQuickHelp"  style="padding:25px"><img src="images/quickHelp.png" title="Quick Help" alt="Quick Help"></span>
			</div>
			<div class="testCoordinationQuickHelpHintPopup">At this time, the Daily Access Codes can be viewed only for the Test Days listed.</div>
		</form>
			
			
		</div>
		<div id="divGridDisclaimer1" hidden="hidden" class="hidden" style="padding-left: 25px">
			<c:choose>
				<c:when test="${user.currentAssessmentProgramName == 'PLTW'}">
					<span style="color: #94b54d;">To view codes for individual Grades/Courses, click on PDF or CSV icon within the table below:</span>
				</c:when>
				<c:otherwise>
					<span style="color: #94b54d;">To view codes for individual Grades/Subjects, click on PDF or CSV icon within the table below:</span>
				</c:otherwise>
			</c:choose>
		</div>
		<br/>
		<div id="dataTable">
		<div class="kite-table">		
			<table id="dailyAccessCodesTable"  class="responsive"></table>
			<div id="dailyAccessCodesDiv" class="responsive"></div>
		</div>
		</div>
		<div class="form-fields" id="divBundleDisclaimer" style="padding-left: 25px">
			<table>
				<tr>
				<td>
					<c:choose>
						<c:when test="${user.currentAssessmentProgramName == 'PLTW'}">
							<span style="color: #94b54d;">To bundle multiple Courses/Grades in one file, select the associated checkbox(es) or<br/> select all,
								and click View Access Codes in PDF or CSV format:</span>
						</c:when>
						<c:otherwise>
							<span style="color: #94b54d;">To bundle multiple Subjects/Grades in one file, select the associated checkbox(es) or<br/> select all,
								and click View Access Codes in PDF or CSV format:</span>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<div style ="float: right;">
						<a id="viewAccessCodesPdf"><img alt="Daily Access Codes PDF" style="border:0px solid;" align="left" src="images/btn_viewcodespdf.png" /> </a>
						<a id="viewAccessCodesCsv"><img alt="Daily Access Codes CSV" style="border:0px solid;" align="right" src="images/btn_viewcodescsv.png" /> </a>
					</div>
				</td>
				</tr>
			</table>
			
			
		</div>
		<div class="form-fields" id="divSecurityDisclaimer" style ="float: right;">
		<span style="color: #94b54d;">For security purposes, when finished viewing access codes, click Done to close the window: </span><button class="panel_btn"  id="doneBtn">Done</button>
				
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/configuration/testCoordination.js'/>"> </script>
<script type="text/javascript">
var flag = 1;
var isTeacher = false;
var isNotBack = true;
var hasTestTicketViewPerm = false;
<c:if test="${user.teacher}">
	isTeacher = true;
</c:if>
<security:authorize access="hasRole('PERM_TESTTICKET_VIEW')" >
	hasTestTicketViewPerm = true;
</security:authorize>
<c:if test="${not param.keepData}">
	clearTCSearchFilterValuesFromSession();
</c:if>
</script>