<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userUpload.js'/>"> </script>

<div  class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">select file and organization level, click on Upload.</span>
    <form id="uploadUserFilterForm" name="uploadUserFilterForm">
		<div id="uploadUserOrgFilter"></div>
		<div class="form-fields">
			<label for="userFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="userFileData" name="userFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="userFileDataInput" id="userFileDataInput" title='user File Data' class="input-file" readOnly="readOnly"/>
			    <a tabindex='0' title='User Upload File Input' class="add-on" OnKeyPress="browseUploadUserCsv(event);" onclick="browseUploadUserCsv(event);"><img alt='CSV File Input' src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
				<span id="User_TemplatedownloadquickHelp"><img tabindex='0' alt='Quick Help' src= "${pageContext.request.contextPath}/images/quickHelp.png"/></span>
			</div>
		</div>

		
		<div class="QuickHelpHint" >Please use the current version of the <a id="User_Templatelink" href="getTemplate.htm?templateName=User_Upload_Template.CSV">User Upload Template.</a></div>

	</form>
	<div id="User_TemplatedownloadquickHelpPopup">
		   	<div id="User_TemplatedownloadquickHelpPopupClose" > X </div>
		   		<div id="User_TemplatedownloadquickHelpPopupMessage" >
		    	 <p>Please use the current version of the <a id="User_TemplatelinkPopup" href="getTemplate.htm?templateName=User_Upload_Template.CSV">User Upload Template.</a></p>
		   	</div>
		</div>
    <security:authorize access="hasAnyRole('PERM_USER_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uplodUser">Upload</a>
	 	<div id="userprogressbar"></div>
	 </security:authorize>
</div>
<div class="full_main">
	<div id="uploadUserGridCell">
			<div id="userUploadReport" hidden="hidden" class="hidden"></div> <br/>
			<div id="userUploadReportDetails" ></div>
			<div id="uploadUser" hidden="hidden" class="hidden"></div>
		 	<div id="uploadUserGridContainer" class="kite-table">
		 		<table class="responsive kite-table" id="uploadUserGridTableId"><tr><td></td></tr></table>
				<div id="uploadUserGridPager" style="width: auto;"></div>
		 	</div>
	</div>
<!-- Commented for F670 Accessibility AMP -->

	<!-- <div id="uploadUserGridCell" >
		<div id="uploadUserDiv" hidden="hidden"></div>
	 	<div id=uploadUserGridContainer class="kite-table">
	 		<table class="responsive" id="uploadUserGridTableId"></table>
			<div id="uploadUserGridPager" style="width: auto;"></div>
	 	</div>
	</div> -->
	
</div>
<script>
	var upload_User_Select_Option_Loadonce = false;
</script>