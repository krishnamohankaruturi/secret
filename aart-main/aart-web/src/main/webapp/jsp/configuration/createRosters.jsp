<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.form .form-fields input{
height: 34px !important;
}
.input-large{
width:248px !important;
}
.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset{
float :none !important
}
.ui-widget-overlay{
z-index: 0 !important;
}
._bcg {
color: red !important;
}

</style>
<div class="form" id="cretaeroster" style="margin-left:-24px">
	<div id="createRosterSuccess" style="color: green;"></div>
	<div id="duplicateRosterError" class="error"></div>
	<div id="forceBiologyCourseError" class="error"></div>
	<div id="rostermessages_createroster" hidden="hidden"><span class="error_message ui-state-error selectAllLabels hidden duplicate" id="ksPermissionDeniedMessage_createRoster">Roster cannot be created for Kansas state users</span></div>
	<form id="createRosterSearchForm" name="createRosterSearchForm">
		<div>
			<div id='createRosterOrgFilter'></div>
			<div class="btn-bar"  style="margin-left:25px">
				<button id="searchRosters" class="btn_blue"><fmt:message key='label.common.search'/></button>
			</div>
		</div>
	</form>
	
	<form id="createRosterForm" name="createRosterForm">
		<div class="form-fields">
			<label for="rosterNameLbl" class="isrequired field-label"><fmt:message key="label.config.rosters.rostername"/><span class="lbl-required">*</span></label>
			<input id="createRosterName" name="rosterName" type="text" class="input-large" maxlength="75" title="Roster Name"/>
		</div>
		<div class="form-fields">
			<label for="createContentAreaSelect" class="isrequired field-label" style="margin-top:37px;"><fmt:message key="label.config.rosters.subject"/><span class="lbl-required">*</span></label>
			<select id="createContentAreaSelect"  class="bcg_select required" name="createContentAreaSelect" style = "color: #4e4f4f; "><option value="">Select</option></select>
		</div>
		<div class="form-fields">
			<label for="createCourseSelect" class="field-label"><fmt:message key="label.config.rosters.course"/></label>
			<select id="createCourseSelect"  class="bcg_select" name="createCourseSelect"><option value="">Select</option></select>
		</div>
	</form>
	<div>
		<div class="form-fields grid-row">
			<label for="selectEducator" class="isrequired field-label"><fmt:message key="label.config.rosters.selecteducator"/><span class="lbl-required">*</span>
			<span id="createRosterEducatorIdError" style="font-size: 0.9em;text-transform: none;" class="error createRosterError"></span></label>
			<div id="createEducatorGridContainer" class="kite-table">
		 		<table class="responsive" id="createEducatorGrid"></table>
				<div id="createEducatorGridPager"></div>
			</div>
			<label id="createRosterEducatorSelectError" class="error createRosterError" ></label>
		</div>
	</div>
	<div>
		<div class="form-fields grid-row">
			<label id="createRosterStudentGradeError" class="" style="color:red;margin-left:250px;display:none;margin-top:10px;" >Warning! only it will add more than 10th grade students</label>
			<label for="selectStudents" class="isrequired field-label"><fmt:message key="label.config.rosters.selectstudents"/><span class="lbl-required">*</span></label>
			<div id="createStudentGridContainer"  class="kite-table">
		 		<table class="responsive" id="createStudentGrid"></table>
				<div id="createStudentGridPager"></div>
			</div>
			<label id="createRosterStudentSelectError" class="error createRosterError"></label>
		</div>
	</div>
	<div class="btn-bar" style="margin-left:25px">
			<button id="createRosterBtn" class="btn_blue" style="margin-top: 10px;"><fmt:message key='label.common.save'/></button>
	</div>
	<div id="createRosterError" class="error"></div>		
	
</div>

<script type="text/javascript">
var gCreateRosterLoadOnce = false;
var contractingOrgDisplayIdentifier = '${user.contractingOrgDisplayIdentifier}';

</script>