<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/tools/uploadPermissions.js'/>"> </script>
<div id="uploadPermissionForm">
<div  class="full_side">	
	
    <span class="panel_subhead">select file  click on Upload.</span>
    <form id="uploadPermissionFilterForm" name="uploadPermissionFilterForm">
		<div class="form-fields">
			<label for="permissionFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="permissionFileData" name="permissionFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="permissionFileDataInput" id="permissionFileDataInput" title='permission File Data' class="input-file" readOnly="readOnly"/>
			    <a tabindex='0' title='permission Upload File Input' class="add-on" OnKeyPress="browseuploadPermissionCsv(event);" onclick="browseuploadPermissionCsv(event);"><img alt='CSV File Input' src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
			</div>
		</div>

	</form>

   	  <c:if test="${user.groupCode == 'GSAD'}">
	 	<a class="panel_btn" href="#" id="uploadPermission">Upload</a>
	 	<div id="permissionprogressbar"></div>
	 </c:if>
</div>
<div class="full_main">
	<div id="uploadPermissionGridCell">
			<div id="permissionUploadReport" hidden="hidden" class="hidden"></div> <br/>
			<div id="permissionUploadReportDetails" ></div>
			<div id="uploadPermissions" hidden="hidden" class="hidden"></div>
		 	<div id="uploadPermissionGridContainer" class="kite-table">
		 		<table class="responsive kite-table" id="uploadPermissionGridTableId"><tr><td></td></tr></table>
				<div id="uploadPermissionGridPager" style="width: auto;"></div>
		 	</div>
	</div>
	
</div>
<div id="reportPermissionUploadErrorMessage"></div>
</div>
<script>
	var upload_permission_Select_Option_Loadonce = false;
	$("#permissionsOrgFilterForm").hide();
</script>