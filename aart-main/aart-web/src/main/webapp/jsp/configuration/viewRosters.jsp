<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.ui-dialog .ui-dialog-buttonpane{
border-width:0 0 0 0 !important;
}

.ui-widget-overlay{
opacity :0 !important;
}
/* div .config .blueLabel {
	font-size: .95em;
	color: #4090e1;
	text-transform: uppercase;
}
div .config .cell {
	display: table-cell;
}


div .config .error {
	color: red;
} */
div .config .lesswidth{
width:100% !important;
}
</style>
<link rel="stylesheet"
	href="<c:url value='/css/theme/configuration.css'/>" type="text/css" />
 
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="viewRosterOrgFilterForm">
		<div id='viewRosterOrgFilter'></div>
	</form>
    <security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')">
	 	<a class="panel_btn" href="#" id="searchviewRosters">Search</a>
	</security:authorize>
</div>
<div class="full_main">
	<div id="viewRosterGridCell">
	<div id="viewRosterGridContainer"  class="kite-table"> 
		<table class="responsive" id="viewRostersTableId" role='presentation'></table>
		<div class="responsive" id="viewRosterGridPager"></div>	
	</div>
	</div>
</div>
<div id="viewRosterPopup" class="_bcg config hidden"  style="padding-left: 20px;">
	<div id="rosterId" hidden="hidden"></div>
	<div id="schoolId" hidden="hidden"></div>
	<div id="schoolYear" hidden="hidden"></div>
	
	<div id="viewRosterSaveError" class="config error" style="height: 25px;" ></div>
	<div class="tabTable lesswidth" style="margin-top: 10px;">
		<div class="row">
			<div class="blueLabel" >
				<div id="rosterNameLbl" class="isrequired field-label"><fmt:message key="label.config.rosters.rostername" /><span class="lbl-required">*</span></div>
			</div>
			<div class="cell">
				<input id="viewRosterName" type="text" maxlength="75" title="Roster Name" />
			</div>
			<div class="blueLabel" >
				<div id="contentAreaLbl" class="isrequired field-label">
					<c:choose>
						<c:when test="${user.currentAssessmentProgramName == 'PLTW'}">
						  <fmt:message key="label.config.rosters.subjectPLTW"/>
						</c:when>
						<c:otherwise>
						  <fmt:message key="label.config.rosters.subject"/>
						</c:otherwise>
					</c:choose>
				<span class="lbl-required">*</span></div>
			</div>
			<div id="contentArea" class="cell">
				<select id="viewContentAreaSelect"  class="bcg_select" name="viewContentAreaSelect" title="Subject" ><option value="0">Select</option></select>
			</div>
			<c:choose>
				<c:when test="${user.currentAssessmentProgramName != 'PLTW'}">
					<div class="blueLabel" >
						<div id="courseLbl"><fmt:message key="label.config.rosters.course"/></div>
					</div>
					<div id="course" class="cell">
						<select id="viewCourseSelect"  class="bcg_select" name="viewCourseSelect" title="Course" ><option value="0">Select</option></select>
					</div>
				</c:when>
			</c:choose>
			
		</div>
	</div>
	<%-- <div class="tabTable">
		<div class="row">
			<div class="blueLabel" style="width:200px;"><div id="rosterNameLbl"><fmt:message key="label.config.rosters.rostername"/></div></div><div class="cell"><input id="vieRosterName" type=text/></div>
		</div>
		<div class="row">
			<div class="blueLabel" style="width:200px;"><div id="contentAreaLbl"><fmt:message key="label.config.rosters.contentarea"/></div></div>
			<div id="contentArea" class="cell">
			<select id="viewContentAreaSelectId"  class="bcg_select" name="viewContentAreaSelect"><option value="0">Select</option></select></div>
		</div>
	</div> --%>
	<br>
	<br>
	<div class="blueLabel"><fmt:message key="label.config.rosters.selecteducator"/></div>
	<br>
	<br>	
	<div id="inactiveEdMsg" hidden="true" style="color: red;"><fmt:message key="notice.config.rosters.inactiveedmsg"/><br><br></div>
	<div id="editEducatorGridContainer" class="kite-table">
 		<table class="responsive" id="editEducatorGrid" role='presentation'></table>
		<div id="editEducatorGridPager"></div>
	</div>
	<br>
	<br>
	<div class="blueLabel"><fmt:message key="label.config.rosters.selectstudents"/></div>
	<br/>
	<div id="editStudentGridContainer"  class="kite-table">
 		<table class="responsive" id="editStudentGrid" role='presentation'></table>
		<div id="editStudentGridPager"></div>
	</div>
	<label id="editRosterStudentSelectError" class="error createRosterError" style ="text-align: left;"></label>
</div>

<script type="text/javascript">
var editRosterPermission = false;
var gViewRosterLoadOnce = false;
$(function() {
	<security:authorize access="hasRole('PERM_ROSTERRECORD_MODIFY')">
		editRosterPermission = true;
	</security:authorize>
});
</script>