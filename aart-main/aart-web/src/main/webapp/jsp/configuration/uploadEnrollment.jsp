<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Select organization level and file, click on Upload.</span>
    <form id="uploadEnrollmentOrgFilterForm">
		<div id="uploadEnrollmentOrgFilter"></div>
		<div class="form-fields">
			<label for="enrollmentFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="enrollmentFileData" name="enrollmentFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="enrollmentFileDataInput" id="enrollmentFileDataInput" class="input-file" readOnly="readOnly" title="Upload Enrollment File" />
			    <a tabindex=0 class="add-on" onclick="browseUploadEnrollmentCsv(event)" OnKeyPress = "browseUploadEnrollmentCsv(event)"  title="Upload Enrollment File" ><img alt='CSV File Input' id="enrollmentUploadFileIcon"/></a>
			    <span id="ENR_Templatedownloadquick"><img tabindex='0' alt='Quick Help' src= "${pageContext.request.contextPath}/images/quickHelp.png"/></span>
			</div>
		</div>
			


<div class="QuickHelpHint" >Please use the current version of the <a id="ENR_Templatelink" href="getTemplate.htm?templateName=Enrollment_Upload_Template.CSV">Enrollment Upload Template.</a></div>		
	</form>
	
	<div id="ENR_TemplatedownloadquickHelpPopup" >
		   	<div id="ENR_TemplatedownloadquickHelpPopupClose" > X </div>
		   		<div id="ENR_TemplatedownloadquickHelpPopupMessage" >
		    	 <p>Please use the current version of the <a id="ENR_Templatelink_popup" href="getTemplate.htm?templateName=Enrollment_Upload_Template.CSV">Enrollment Upload Template.</a></p>
		   	</div>
		</div>	
    <security:authorize access="hasAnyRole('PERM_ENRL_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uploadEnrollment"><fmt:message key="upload.button"/></a>
	</security:authorize>
</div>



<span id="loadingmessageUE" class="hidden" style="padding:25px;"><fmt:message key="label.common.loading"/>...<img src="<c:url value='/images/ajax-loader.gif'/>"/></span>
	
<div class="full_main">
	<div id="enrollmentGridCell">
		<div id="enrollmentUploadReport" hidden="hidden"></div> <br/>
		<div id="enrollmentUploadReportDetails" >
		</div>
	 	<div id="enrollmentGridContainer" class="kite-table">
	 		<table class="responsive" id="enrollmentGrid" role="presentation" ></table>
			<div id="enrollmentGridPager" style="width: auto;"></div>
	 	</div>
	 </div>
	 
	 <div id="uploadEnrollmentGridCell" >
		 <div id="uploadEnrollmentDivGrid" hidden="hidden"></div> 
	 	<div id=uploadEnrollmentGridContainer class="kite-table">
	 		<table class="responsive" id="uploadEnrollmentGridTableId"></table>
			<div id="uploadEnrollmentGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>

<script type="text/javascript">
	var gUploadEnrollmentLoadOnce = false;
</script>
