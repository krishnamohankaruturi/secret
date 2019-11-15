<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<script type="text/javascript" src="<c:url value='/js/reports-ui/stateSpecificFiles/stateSpecificFiles.js'/>"></script>

<security:authorize access="hasAnyRole('MANAGE_STATE_SPECIFIC_FILES','VIEW_STATE_SPECIFIC_FILES')">
		<!-- <div id="stateSpecificFilesDialog" style="display: none;">-->			
			<div style="margin: 3% 2% 2% 2%;">
				<label style="font-weight: bold; margin-left: 1%">Assessment Program:</label> 
				<span id="currentUserAssessmentProgram"></span> 
				<label style="font-weight: bold; margin-left: 1%">State:</label> 
				<span id="currentOrganization"></span>
			</div>
			<div id="uploadCustomFileSucess" style="text-align: center; color: green"></div>
			<div id="uploadCustomFileError" style="text-align: center; color: red"></div>
			<div id="removeCustomFileSucess" style="text-align: center; color: green"></div>
			<div id="removeCustomFileError" style="text-align: center; color: red"></div>
			<div class="table_wrap" >
				<div class="kite-table">
					<table id="stateSpecificFilesTableId" class="responsive"></table>
					<div id="pstateSpecificFilesTableId" class="responsive"></div>
				</div>
			</div>
			<security:authorize access="hasRole('MANAGE_STATE_SPECIFIC_FILES')">
				<div style="margin-left: 81%">
					<button class="ui-button ui-corner-all" id="postFileButton" onclick= "postFile()">Post file</button>
				</div>
			</security:authorize>
			<div id="removeStateSpecificFileDialog" style="display:none;">
                Are you sure you want to remove the file?
            </div>
		<!-- </div> -->
		<div id="uploadPostFileDialog" style="display: none;">
			<input type="hidden" id="stateSpecificFileMaxSize"  value="${stateSpecificFileMaxSize}"/>
			<div>
				<div id="uploadCustomNoFileError" style="color: red"></div>
				<input id="postFileData" name="postFileData" type="file" class="ui-widget-content"
					accept="${stateSpecificFileAllowedExtensions}" />
			</div>
			<div style="margin-top:20px;">
				<div id="uploadCustomFileDescriptionError" style="color: red"></div>
				<label>Description <span class="lbl-required">*</span></label>
				<span>
					<input type="text" name="descriptionName" id="descriptionName" title="Description Name"/>
				</span>
			</div>
		</div>

	</security:authorize>

<script>	
var hasEditCustomFilePermission =false;
<security:authorize access="hasRole('MANAGE_STATE_SPECIFIC_FILES')">
	hasEditCustomFilePermission =true;
</security:authorize>
</script> 