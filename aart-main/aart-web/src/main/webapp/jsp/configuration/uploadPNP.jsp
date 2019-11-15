<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
	<span class="panel_subhead">Select organization level and file, click on Upload</span>
	<form id="uploadPNPOrgFilterForm">
		<div id="uploadPNPOrgFilter"></div>
		<div class="form-fields">
			<label for="pnpFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="pnpFileData" name="pnpFileData" type="file" class="hideFileUploader">
			<div class="input-append">
				<input type="text" name="pnpFileDataInput" id="pnpFileDataInput" class="input-file" readOnly="readOnly" title="Upload PNP File" />
				<a tabindex=0 class="add-on" onclick="browseUploadPNPCsv(event)" onkeypress="browseUploadPNPCsv(event)"  title="Upload PNP File"> 
					<img alt='CSV File Input' src="<c:url value="/images/icon-csv.png"/>" id="pnpUploadFileIcon"/>
				</a>
				<span id="pnpQuickHelp">
					<img tabindex="0" alt="Quick Help" src="${pageContext.request.contextPath}/images/quickHelp.png"/>
				</span>
			</div>
		</div>
		
		<div id="pnpQuickHelpContent" class="QuickHelpHint">
			<div>There is no Student PNP template available.</div>
			<security:authorize access="hasRole('DATA_EXTRACTS_PNP_ABRIDGED')">
				<br/>
				<div>
					Instead, go to <a href="<c:url value='dataExtracts.htm'/>">Reports &rarr; Data Extracts</a>, and download a PNP Settings (Abridged) extract. Make any required changes to that extract and upload it here.
				</div>
			</security:authorize>
		</div>
	</form>
	
    <security:authorize access="hasAnyRole('PERSONAL_NEEDS_PROFILE_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uploadPNPButton"><fmt:message key="upload.button"/></a>
	</security:authorize>
</div>

<div class="full_main">
	<div id="pnpGridCell">
		<div id="pnpUploadReport" hidden="hidden"></div> <br/>
		<div id="pnpUploadReportDetails" >
		</div>
	 	<div id="pnpGridContainer" class="kite-table">
	 		<table class="responsive" id="pnpGrid" role="presentation" ></table>
			<div id="pnpGridPager" style="width: auto;"></div>
	 	</div>
	 </div>
	 
	 <div id="uploadPNPGridCell" >
		 <div id="uploadPNPDivGrid" hidden="hidden"></div> 
	 	<div id=uploadPNPGridContainer class="kite-table">
	 		<table class="responsive" id="uploadPNPGridTableId"></table>
			<div id="uploadPNPGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>

<script type="text/javascript">
	var gUploadPNPLoadOnce = false;
</script>
