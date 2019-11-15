<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>


<script type="text/javascript" async
	src="${pageContext.request.contextPath}/js/external/MathJax.js">
</script>
<link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/interim.css'/>" type="text/css" />
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/external/ui.jqgrid.min-4.15.5.css" />

<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/external/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/external/bootstrap-modal-bs3patch.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/external/bootstrap-modal.css" />

<script type="text/javascript">
  var viewInterimSchoolReportPermission = false;
  var viewInterimDistrictReportPermission = false;
  var hasViewPredictiveStudentScore= false;
  var interimQuestionCSVPermission = false;
  var currentAssessmentProgramName = '${user.currentAssessmentProgramName}';
  <security:authorize access="hasRole('VIEW_INT_PRED_SCHOOL_REPORT')">
	  viewInterimSchoolReportPermission = true;
  </security:authorize>
  <security:authorize access="hasRole('VIEW_INT_PRED_DISTRICT_REPORT')">
      viewInterimDistrictReportPermission = true;
  </security:authorize>
  <security:authorize access="hasRole('VIEW_PREDICTIVE_STUDENT_SCORE')">
      hasViewPredictiveStudentScore = true;
  </security:authorize>
  <security:authorize access="hasRole('VIEW_PREDICTIVE_QUESTION_CSV')">
     interimQuestionCSVPermission = true;
  </security:authorize>
</script>
    
<body id= "bodyId">
<div id="interimTabs" class="panel_full">
	<div>
		<div class="container-fluid interim_bg">
			<div class="row" style="margin-top:50px;">
				<div class="col-sm-4 interim_box">
					<a href="#"><div id ="assembleTab"><img src="images/interim_landicon1.png"
								alt="Select and Assemble Tests" />
								<p>Build or Select a Test</p>
								<span class="interimHeader">Build, Select, Preview</span>
						</div></a>
				</div>
				<div class="col-sm-4 interim_box">
					<a href="#"><div  id ="manageTests">
							<img src="images/interim_landicon2.png"
								alt="Select and Assemble Tests" />
								<p>My Tests</p>
								<span class="interimHeader">Assign, Manage</span>
						</div></a>
				</div>
				<div class="col-sm-4 interim_box">
					<a href="#"><div id="viewResults">
							<img src="images/interim_landicon3.png"
								alt="Select and Assemble Tests" />
								<p>View Results</p>
								<span class="interimHeader">View Reports</span>
						</div></a>
				</div>
				 <!-- <div class="col-sm-offset-4 col-sm-4 interim_box">
					<a href="#"><div id="manageGroups">
							<img src="images/group_icon.png"
								alt="Manage My Groups" />
								<p>Manage My Groups</p>
						</div></a>
				</div> --> 
			</div>
		</div>
	</div>

</div>
<div id="previewQCTestDiv"></div>
<div id="predStudentReports-view"></div>
<div id="monitorTestDiv">
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
</div>
</body>

<script src="${pageContext.request.contextPath}/js/external/ejs.js"></script>
<script src="${pageContext.request.contextPath}/js/external/ui.multiselect.js"></script>
<script src="${pageContext.request.contextPath}/js/external/jquery.jqgrid.min-4.15.5.js"></script>
<script src="${pageContext.request.contextPath}/js/external/ui-multiselect-en.js"></script>

<script src="${pageContext.request.contextPath}/js/external/grid.locale-en.js"></script>

<script src="${pageContext.request.contextPath}/js/external/papaparse.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/jquery.timepicker.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/jquery.dataTables.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/interim.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tde/jquery.dnd.scroll.js"></script>