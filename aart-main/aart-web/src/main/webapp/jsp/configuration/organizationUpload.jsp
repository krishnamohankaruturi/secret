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
<div  class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Select file and organization level, click on Upload.</span>
    <form id="uploadOrgFilterForm" name="uploadOrgFilterForm">
		<div id="uploadOrgOrgFilter"></div>
		<div class="form-fields">
			<label for="orgFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="orgFileData" name="orgFileData" type="file" class="real-file" style="position: absolute; top: -9999px; left: -9999px;"/>
                <div class="input-append">
				    <input type="text" name="orgFileDataInput" id="orgFileDataInput" title="Organization File" class="input-file" readOnly="readOnly"/>
				    <a tabindex=0 class="add-on" onclick="browseUploadRosterCsv(event)" OnKeyPress = "browseUploadRosterCsv(event)" title="Organization File" ><img alt='CSV File Input' src="${pageContext.request.contextPath}/images/icon-csv.png" title="Organization Upload File Icon" /></a>
				    <span id="Organizations_TemplatedownloadquickHelp"><img tabindex='0' alt='Quick Help' src= "${pageContext.request.contextPath}/images/quickHelp.png" title="Quick Help Icon" /></span>
				</div>
		</div>
		<div class="QuickHelpHint" >Please use the current version of the <a id="Organizations_Templatelink" href="getTemplate.htm?templateName=Organization_Upload_Template.CSV"> Organization Upload Template.</a></div>
	</form>
	<div id="Organizations_TemplatedownloadquickHelpPopup" >
		   	<div id="Organization_TempdownnquickHelpPopupClose" > X </div>
		   		<div id="Organizations_TemplatedownloadquickHelpPopupMessage" >
		    	 <p>Please use the current version of the <a id="Organizations_Templatelink_Popup" href="getTemplate.htm?templateName=Organization_Upload_Template.CSV"> Organization Upload Template.</a></p>
		   	</div>
		</div>
    <security:authorize access="hasAnyRole('PERM_ORG_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uplodOrg">Upload</a>
	 </security:authorize>
</div>
<div class="full_main">
 	<div id="orgGridCell">
		<div id="orgUploadReport" hidden="hidden"></div> <br/>
		<div id="orgUploadReportDetails" ></div>
		<div id="uploadOrg" hidden="hidden"></div>
	 	<div id="orgGridContainer" class="kite-table">
	 		<table class="responsive" id="orgUploadGrid" role="presentation" ><tr><td></td></tr></table>
			<div id="orgUploadGridPager" style="width: auto;"></div>
	 	</div>
	</div>
 	
	<div id="uploadOrgGridCell" >
		<div id="uploadOrganizationDiv" hidden="hidden"></div>
	 	<div id=uploadOrgGridContainer class="kite-table" style="width: 780px;">
	 		<table class="responsive" id="uploadOrgGridTableId"></table>
			<div id="uploadOrgGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div> 
	
<script type="text/javascript">
	var gUploadOrganizationLoadOnce = false;
</script>
<script type="text/javascript" src="<c:url value='/js/configuration/organizationUpload.js'/>"> </script>