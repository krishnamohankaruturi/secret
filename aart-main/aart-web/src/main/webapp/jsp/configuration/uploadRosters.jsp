<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">select organization level and file, click on Upload.</span>
    <form id="uploadRosterFilterForm" name="uploadRosterFilterForm">
		<div id="uploadRosterOrgFilter"></div>
		<div class="form-fields">
			<label for="rosterFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="rosterFileData" name="rosterFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="rosterFileDataInput" id="rosterFileDataInput" class="input-file" readOnly="readOnly" title="Roster File Data"/>
			    <a tabindex='0' class="add-on"  OnKeyPress="browseUploadRosterCsv(event)"  onclick="browseUploadRosterCsv(event)" tabindex='0'><img alt='CSV File Input' src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
				<span id="Roster_TemplatedownloadquickHelp"><img tabindex='0' alt='Quick Help' src= "${pageContext.request.contextPath}/images/quickHelp.png"/></span>		      
			</div>
			<script type="text/javascript">
			$('input[id=rosterFileData]').on("change",function() {
				$('#rosterFileDataInput').val($('#rosterFileData')[0].files[0].name);
			});
			</script>
		</div>
		
		
		<div class="QuickHelpHint" >Please use the current version of the <a id="Roster_RosterTemplate_link" href="getTemplate.htm?templateName=Roster_Upload_Template.CSV">Roster Upload Template.</a></div>		
				 
	</form>

	<div id="Roster_TemplatedownloadquickHelpPopup">
		<div id="Roster_TemplatedownloadquickHelpPopupClose">X</div>
		<div id="Roster_TemplatedownloadquickHelpPopupMessage">
			<p>
				Please use the current version of the <a
					id="Roster_RosterTemplatelink_Popup"
					href="getTemplate.htm?templateName=Roster_Upload_Template.CSV">Roster
					Upload Template.</a>
			</p>
		</div>
	</div>

	<security:authorize access="hasAnyRole('PERM_ROSTERRECORD_UPLOAD')">
    	<div id="uploadRosterError" class="error" hidden="hidden" class="hidden"></div>
	 	<a class="panel_btn" href="#" id="uploadRosterBtn">Upload</a>
	 </security:authorize>
</div>
<div class="full_main">
	<div id="rosterGridCell">
	 <div id="rosterUploadReport" hidden="hidden" class="hidden"></div>
	 <div id="rostermessages_uploadroster" hidden="hidden"><span class="error_message ui-state-error selectAllLabels hidden duplicate" id="ksPermissionDeniedMessage_uploadroster" >Error Msg: Cannot upload roster file for Kansas state</span></div>
	 <div id="rosterUploadReportDetails"></div>
	 <div id="rosterGridContainer" class="kite-table">
	 	<table class="responsive" id="rosterGrid" role='presentation'></table>
	 	<div id="rosterGridPager" style="width: auto;"></div>
	 </div>
	</div>
	<div id="uploadRosterGridCell" >
		<div id="uploadRosterDiv" hidden="hidden"></div>
	 	<div id=uploadRosterGridContainer class="kite-table">
	 		<table class="responsive" id="uploadRosterGridTableId" role='presentation'></table>
			<div id="uploadRosterGridPager" style="width: auto;"></div>
	 	</div>
	</div>
	
</div>



<div id="uploadRosterPopup" class="config _bcg" hidden="hidden" class="hidden">
	<div class="form">
		<div class="form-fields">
			<label class="field-label"><fmt:message key="label.config.rosters.rostername"/></label><div id="rosterName" class="cell value"></div>
		</div>
		<div class="form-fields">
			<label class="field-label"><fmt:message key="label.config.rosters.contentarea"/></label><div id="uploadcontentArea" class="cell value"></div>
		</div>
	</div>
	<div class="form">
		<div class="form-fields" style="display:block;">
			<label class="field-label"><fmt:message key="label.config.rosters.educator"/></label>
		
			<div id="viewEducatorGridContainer" class="kite-table" >
		 		<table class="responsive" id="viewEducatorGrid"></table>
		 		<div id="viewEducatorGridPager"></div>
			</div>
		</div>
		<div class="form-fields" style="display:block;">
			<label class="field-label"><fmt:message key="label.config.rosters.students"/></label>
	
			<div id="viewAssignedStudentGridContainer"  class="kite-table">
		 		<table class="responsive" id="viewAssignedStudentGrid"></table>
				<div id="viewAssignedStudentGridPager"></div>
			</div>
		</div>
	</div>
</div>

 <script type="text/javascript" src="<c:url value='/js/configuration/uploadRosters.js'/>"> </script>	
<script type="text/javascript">
var gUploadRostersLoadOnce = false;
</script>