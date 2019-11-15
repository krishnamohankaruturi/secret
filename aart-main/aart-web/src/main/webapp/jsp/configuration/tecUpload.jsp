<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.select2-container{
width: 250px !important;
}

.form .form-fields input{
height: 35px !important;
}
</style>
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Select organization level and file, click on Upload.</span>
    <form id="uploadTecOrgFilterForm">
		<div id="uploadTecOrgFilter"></div>
		<div class="form-fields">
			<label for="exitsFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="tecFileData" name="tecFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="tecFileDataInput" title="TEC Upload file" id="tecFileDataInput" class="input-file" style="height: 35px;" readOnly="readOnly"/>
			    <a tabindex=0 class="add-on" title="TEC upload file" onclick="browseUploadRosterCsv(event)" OnKeyPress ="browseUploadRosterCsv(event)"><img alt='CSV File Input' src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
			    <span id="TEC_TemplatedownloadquickHelpInTestTab"><img tabindex='0' alt='Quick Help' src= "${pageContext.request.contextPath}/images/quickHelp.png"/></span>
			</div>
		</div>

		
		<div class="QuickHelpHint">Please use the current version of the <a id="TECUpload_Templatelink" href="getTemplate.htm?templateName=TEC_Upload_Template.CSV">TEC Upload Template.</a></div>		

	
	</form>
	
	<div id="TEC_uploadTemplatedownloadquickHelpPopup" >
		   	<div id="TEC_uploadTemplatedownloadquickHelpPopupClose" > X </div>
		   		<div id="TEC_uploadTemplatedownloadquickHelpPopupMessage" >
		    	 <p>Please use the current version of the <a id="TEC_uploadTemplatelink" href="getTemplate.htm?templateName=TEC_Upload_Template.CSV">TEC Upload Template.</a></p>
		   	</div>
		</div>
	
	
    <security:authorize access="hasAnyRole('PERM_ENRL_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uploadTecExits"><fmt:message key="upload.button"/></a>
	</security:authorize>
</div>
	<div>
			<span id="loadingMessageTECupload" class="hidden" style="padding:25px;"><fmt:message key="label.common.loading"/>...<img src="<c:url value='/images/ajax-loader.gif'/>"/></span>
		</div>
<div class="full_main">
	<div id="tecGridCell">
	
		<div id="tecUploadReport" hidden="hidden"></div> <br/>
		<div id="tecUploadReportDetails" ></div>
	 	<div id="tecGridContainer" class="kite-table">
	 		<table class="responsive" id="tecGrid"></table>
			<div id="tecGridPager" style="width: auto;"></div>
	 	</div>
	</div>
	<div id="tecUploadGridCell" >
		<div id="tecUploadDivtest" hidden="hidden"></div>
	 	<div id="tecUploadGridContainer" class="kite-table">
	 		<table class="responsive" id="tecUploadGridTableId"></table>
			<div id="tecUploadGridPager" style="width: auto;"></div>
	 	</div>
	</div>
	
</div>	
<script type="text/javascript">
	var gUploadTECLoadOnce = false;
</script>