<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<form id="permissionsOrgFilterForm" name="permissionsOrgFilterForm" class="form">
	<table width="750">
		<tr>
			<td>
				<div class="form-fields">
					<label for="PermissionStateDropDown" class="field-label">State: <span class="lbl-required">*</span></label> 
					<select id="PermissionStateDropDown" class="bcg_select required" name="PermissionStateDropDown" title="Select State">
					</select>
					<div>
					<input type="checkbox" class="Role_Permission_State_AP_selectAll" id="PermissionStateDropDown_selectAll" title="Select All States"/>Select All
					</div>
				</div>
			</td>
			<td>
				<div class="form-fields">
					<label for="PermissionAPDropDown" class="field-label">Assessment Program: <span class="lbl-required">*</span></label> 
					<select id="PermissionAPDropDown" class="bcg_select required" name="PermissionAPDropDown" title="Select Assessment Program">
					</select>
					<div>
					<input type="checkbox" class="Role_Permission_State_AP_selectAll" id="PermissionAPDropDown_selectAll" title="Select All Assessment Programs"/>Select All
					</div>
				</div>
			</td>
			<td>
				<a href="#" id="permissionsDownloadLInk">Download Report</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript" src="<c:url value='/js/tools/permissionsAndRolesExtract.js'/>"> </script>