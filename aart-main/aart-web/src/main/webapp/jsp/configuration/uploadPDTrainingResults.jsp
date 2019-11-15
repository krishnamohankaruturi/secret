<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/uploadPDTrainingResults.js'/>"> </script>
<div  class="full_side">
	<form id="uploadPDTrainingResultsForm" name="uploadPDTrainingResultsForm">
		<div class="form-fields">
			<label for="pdResultsFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="pdResultsFileData" name="pdResultsFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="pdResultsFileDataInput" id="pdResultsFileDataInput" title="PD Results upload file" class="input-file" readOnly="readOnly"/>
			    <a class="add-on" tabindex="0" onkeypress="uploadPdResultsFileDta(event)" onclick="uploadPdResultsFileDta(event);"><img alt="CSV" src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
			</div>
		</div>			 
	</form>
<security:authorize access="hasAnyRole('PD_TRAINING_RESULTS_UPLOADER')">
	 	<a class="panel_btn" href="#" id="uplodResults">Upload</a>
	 	<div id="pdtprogressbar"></div>
	 </security:authorize>
</div>
<div class="full_main">
	<div id="resultsuploadUserGridCell">
			<div id="resultsuserUploadReport" hidden="hidden" class="hidden"></div> <br/>
			<div id="resultsuserUploadReportDetails" ></div>
			<div id="resultsuploadUser" hidden="hidden" class="hidden"></div>
		 	<div id="resultsuploadUserGridContainer" class="kite-table">
		 		<table class="responsive kite-table" id="resultsuploadUserGridTableId"></table>
				<div id="resultsuploadUserGridPager" style="width: auto;"></div>
		 	</div>
	</div>
</div>
<div>
<table>
<tr id="uploadFileStatus"></tr>
<tr id="uploadFileStatusInprogress"></tr>
<tr id="recordsCreatedCount"></tr>
<tr id=recordsUpdatedCount></tr>
<tr id="recordsRejectedCount"></tr>
<tr id="recordsSkippedCount"></tr>
</table>	
</div>
	