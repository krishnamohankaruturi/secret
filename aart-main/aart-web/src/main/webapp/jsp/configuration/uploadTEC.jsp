<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Select organization level and file, click on Upload.</span>
    <form id="uploadExitsOrgFilterForm">
		<div id="uploadExitsOrgFilter"></div>
		<div class="form-fields">
			<label for="exitsFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="exitsFileData" name="exitsFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="exitsFileDataInput" id="exitsFileDataInput" style="height: 35px;" class="input-file" readOnly="readOnly" title="Upload TEC File" />
			    <a tabindex=0 class="add-on" onclick="browseUploadRosterCsv(event)" OnKeyPress ="browseUploadRosterCsv(event)"  title="CSV File Input" ><img alt='CSV File Input' src="${pageContext.request.contextPath}/images/icon-csv.png" title="TEC Upload File"  /></a>
			    <span id="TEC_TemplatedownloadquickHelp"><img tabindex='0' alt='Quick Help' src= "${pageContext.request.contextPath}/images/quickHelp.png" title="Quick Help" /></span>
			</div>
		</div>

		
		<div class="QuickHelpHint">Please use the current version of the <a id="TEC_Templatelink" href="getTemplate.htm?templateName=TEC_Upload_Template.CSV">TEC Upload Template.</a></div>		

	
	</form>
	
	<div id="TEC_TemplatedownloadquickHelpPopup" >
		   	<div id="TEC_TemplatedownloadquickHelpPopupClose" > X </div>
		   		<div id="TEC_TemplatedownloadquickHelpPopupMessage" >
		    	 <p>Please use the current version of the <a id="TEC_Templatelink_popup" href="getTemplate.htm?templateName=TEC_Upload_Template.CSV">TEC Upload Template.</a></p>
		   	</div>
		</div>
	
	
    <security:authorize access="hasAnyRole('PERM_ENRL_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uploadExits"><fmt:message key="upload.button"/></a>
	</security:authorize>
</div>
	<div>
			<span id="loadingMessageTEC" class="hidden" style="padding:25px;"><fmt:message key="label.common.loading"/>...<img src="<c:url value='/images/ajax-loader.gif'/>"/></span>
		</div>
<div class="full_main">
	<div id="exitsGridCell">
	
		<div id="exitsUploadReport" hidden="hidden"></div> <br/>
		<div id="exitsUploadReportDetails" ></div>
	 	<div id="exitsGridContainer" class="kite-table">
	 		<table class="responsive" id="exitsGrid" role="presentation" ></table>
			<div id="exitsGridPager" style="width: auto;"></div>
	 	</div>
	</div>
	<div id="uploadTECGridCell" >
		<div id="uploadTECDiv" hidden="hidden"></div>
	 	<div id=uploadTECGridContainer class="kite-table">
	 		<table class="responsive" id="uploadTECGridTableId"></table>
			<div id="uploadTECGridPager" style="width: auto;"></div>
	 	</div>
	</div>
	
</div>	
<script type="text/javascript">
	var gUploadExitsLoadOnce = false;
</script>