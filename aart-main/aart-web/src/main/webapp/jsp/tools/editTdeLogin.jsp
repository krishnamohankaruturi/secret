<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
#generatePassword
{
font: normal normal normal 14px/1 FontAwesome !important;
}
</style>
 <script type="text/javascript" src="<c:url value='/js/tools/editTdeLogin.js'/>"> </script>
 <link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />

<div class="full_side">
	<h1 class="panel_head sub">Select Organization</h1>
	<span class="panel_subhead">Specify organization level and click on search</span>
		<form id="studentUserNameOrgFilterForm">
			<div id="studentUserNameOrgFilter"></div>
		</form>
		<a class="panel_btn" href="#" id="studentUserNameButton">Search</a>
</div>

<div class="full_main">
	<label class="hidden error" id="messageEditTdeLogin"></label>
	<div id="studentUserNameGridCell">
		<div id="viewStudentDetails" hidden="hidden" class="hidden"></div>
			<div id="studentUserNameGridContainer" class="kite-table">
				<table class="responsive" id="studentUserNameGridTableId"></table>
				<div id="pstudentUserNameGridTableId" style="width: auto;"></div>
			</div>
	</div>
</div>
<div id="viewUsernamePwdWindow" style="display: none; font-size: 1em;">
    <div id="editStudentDetailsSucess" style="text-align: center; color: green"></div>
	<div id="editStudentDetailsError"
		style="text-align: center; color: red"></div>
	<form id="validateStudentDetails" style="text-align:center; margin-top:10%;">

		<div class="form-fields" style="margin-right:30px">
			<label class="field-label" for="userName">Username:<span
				class="lbl-required">*</span> <input type="text"
				class="js-student_fields" name="studentUsername" id="studentUsername" />
			</label>
		</div>

		<div class="form-fields">
			<label class="field-label" for="password">Password:<span
				class="lbl-required">*</span> <input type="text"
				class="js-student_fields" name="studentPassword" id="studentPassword" readonly />
			<span>
			  <button id="generatePassword" class="fa fa-refresh" style="color: #0e76bc;background-color: transparent;border: none;"
			  title="Generate Password"></a>
			</span>
			</label>
			
		</div>
		<div class="form-fields">
			<input type="button" id="updateStudentDetails" value="Update"
				class="btn_blue" />
		</div>
	</form>
</div>
<script type="text/javascript">
view_EditTDEUserName_tab();
</script>
