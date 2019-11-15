<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<style>
.error {
	color: red;
}

.helpLink {
	width: 32%;
	display: inline-block;
	color: rgb(2, 84, 235);
}


#CreateHelpTopicWindow .form-group, #EditHelpTopicWindow .form-group,
	#CreateHelpContentWindow .form-group {
	margin-bottom: 3%;
}

.hcjqactions {
	width: 33%;
	display: inline-block;
}

.field-label {
	color: #0e76bc;
	margin-bottom: 1%;
	display:block;
}
#createHelpContentDropdown option,#editHelpTopicDropdown option,#createTopicDesc,#editTopicDesc{
	color:#5b5c5e;
}
#helpContentFileDataInput {
	color: #a7a9ac !important;
	float: left;
}

#createHelpContentExpireDate{
float: left; height: 30px; 
margin-top:0px; 
margin-left: 50px; 
background-image: url(images/date-icons.png); 
background-repeat: no-repeat; 
background-position: right center;

}

.btnCreateMsg{
   background: #0e76bc url("../images/btn-bg.png") no-repeat center center;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	-ms-border-radius: 4px;
	-o-border-radius: 4px;
	border-radius: 4px;
	border: 0;
	display: inline-block;
	padding: 10px 10px;
	color: white;
	text-decoration: none;
	font-size: 1em;
	font-weight: 300;
	line-height: 20px;
}
.btnCreateMsg:hover{
	cursor: pointer;
}

#manageHelpContentHeader {
	margin-top: 2%;
	width: 80%; 
	text-transform:none !important;
}

</style>
<h1 id="manageHelpContentHeader">Manage Help Content</h1>
<div id="createHelp" style="margin-top: 3%;">
	<div id="topicSucessMsg" class="ui-state-highlight hidden"></div>
	<div class="container">
		<ul>
			<div id="helpWindows" style="width: 80%">
				<span class="helpLink" id="createHelpTopic"> <img src="images/icons/edit_icon.png" /> <a
					style="cursor: pointer;"> <span style="top: 2%;">Create New Topic</span></a>
				</span> <span class="helpLink" id="editTopic" style="dispay: none;"> <img src="images/icons/edit_icon.png"> <a
					style="cursor: pointer;"> <span style="top: 2%;">Edit Existing Topic</span></a>
				</span> <span class="helpLink" id="createHelpContent"> <img src="images/icons/edit_icon.png"> <a
					style="cursor: pointer;"> <span style="top: 2%;">Create New Help Content</span></a>
				</span>
			</div>
		</ul>
		<div class="table_wrap">
			<div id="helpContentSection" class="kite-table">
				<h5 style="margin-bottom: 1%; font-size: 0.9em;font-weight: bold;" class="_bcg">Assessment Program and State Specific Help Content</h5>
				<table class="responsive" id="helpContentTable"></table>
				<div id="helpContentPager"></div>
			</div>
		</div>
	</div>

	<div class="container" id="CreateHelpTopicWindow" style="display: none;">
		<form id="addHelpTopicForm" name="addHelpTopicForm" class="form">
			<div style="margin-left: 10%; margin-top: 10%;">
			<div id="helpTopicErrorMessage" style="color:red;"></div>
				<div class="form-group">
					<label for="createTopicName" class="field-label">Topic Name <span class="lbl-required">*</span></label> <input type="text"
						class="form-control" id="createTopicName" placeholder="Enter TopicName" name="createTopicName" style="width: 80%;"
						class="text required" path="templateName"> </input>
				</div>
				
				<div class="form-group" style="height: 45px;">
					<label for="createTopicSlug" class="field-label">Topic Page URL :</label>
					<span class="TopicPageHostUrl" style="float:left;"></span>
					<input type="text" class="form-control" id="createTopicSlug" placeholder="Enter Slug" name="createTopicSlug" path="templateName"
					style="width: 31% !important; float:left;"> </input>
				</div>
				
				
				
				<div class="form-group">
					<label for="createTopicDesc" class="field-label">Description <span class="lbl-required">*</span></label>
					<textarea type="text" class="form-control" id="createTopicDesc" placeholder="Enter Description"
						name="createTopicDesc" style="width: 80%; height: 80px;" class="text required" path="templateName"></textarea>
				</div>
			</div>
		</form>
	</div>

	<div class="container" id="EditHelpTopicWindow" style="display: none;">
		<form id="editHelpTopicForm" name="editHelpTopicForm" class="form">
			<div style="margin-left: 10%; margin-top: 10%;">
			<div id="editHelpTopicErrorMessage" style="color:red;"></div>
				<div class="form-group">
					<label for="editHelpTopicDropdown" class="field-label">Select Topic <span class="lbl-required">*</span></label> <select
						id="editHelpTopicDropdown" class="bcg_select required" name="editHelpTopicDropdown">
						<option value="">Select</option>
					</select>
				</div>
				<div class="form-group">
					<label for="editTopicId" class="field-label">Topic Id</label><input type="text"
						class="form-control" id="editTopicId" name="editTopicId" style="width: 80%;" readonly="readonly"
						class="text"> </input>
				</div>
				<div class="form-group">
					<label for="editTopicName" class="field-label">Topic Name <span class="lbl-required">*</span></label><input type="text"
						class="form-control" id="editTopicName" placeholder="Enter TopicName" name="editTopicName" style="width: 80%;"
						class="text required" path="templateName"> </input>
				</div>
				<div class="form-group" style="height: 45px;">
					<label for="editTopicSlug" class="field-label">Topic Page URL :</label><span class="TopicPageHostUrl" style="float:left;"></span><input type="text"
						class="form-control" id="editTopicSlug" placeholder="Enter Slug" name="editTopicSlug" style="width: 31% !important; float:left;"
						class="text" path="templateName"> </input>
				</div>
				<div class="form-group">
					<label for="editTopicDesc" class="field-label">Description <span class="lbl-required">*</span></label>
					<textarea type="text" class="form-control" id="editTopicDesc" placeholder="Enter Description" name="editTopicDesc"
						style="width: 80%; height: 80px;" class="text required" path="templateName"></textarea>
				</div>
			</div>
		</form>
	</div>

	<div class="container" id="CreateHelpContentWindow" style="display: none;">
		<form id="createHelpContentForm" name="createHelpContentForm" class="form">
			<div style="margin: 2%;">
			<div id="helpContentErrorMessage" style="color:red;"></div>
				<div class="form-group" style="width:100%;padding-bottom: 5%;">
					<div style="width:33%;float:left">
						<label for="helpContentAps" class="field-label">AssessmentProgram <span
							class="lbl-required">*</span></label> 
							 <select id="helpContentAps" class="bcg_select required" multiple="multiple"  name="helpContentAps">
					         </select>			
					</div>
					<div style="width:33%;float:left">
					<label for="helpContentStates" class="field-label">State(s) </label>
					 <select id="helpContentStates" class="bcg_select" multiple="multiple" name="helpContentStates"></select>
					</div>
					<div style="width:33%;float:left">
					<label
							for="helpContentRoles" class="field-label">Role(s) </label>
							 <select id="helpContentRoles" class="bcg_select" multiple="multiple"
						 name="helpContentRoles">
					</select>
					
							
					</div>
					  
				</div>
				<div class="form-group" style="width:100%;padding-top: 5%;">
					<div style="width:60%;float:left">

						<label for="createHelpContentDropdown" class="field-label">Select Topic <span class="lbl-required">*</span></label>
						<select id="createHelpContentDropdown" class="bcg_select required" name="createHelpContentDropdown">
							<option value="">Select</option>
						</select>
						
					</div>
					<div style="width:18%;float:left; margin-left: 6%;">
					<label for="createHelpContentTags" class="field-label">Add A Tag</label>
						 <input type="text" class="form-control" id="createHelpContentTags" placeholder="Enter Tags"
							name="createHelpContentTags" class="text" path="templateName"> </input>
					</div>
					</div>
					 
				
				<div class="form-group" style="width: 100%;padding-top: 10%;">
					<div style="width:60%;float:left">
						<label for="createHelpContentTitle" class="field-label">Help Title <span class="lbl-required">*</span></label> 
						<input type="text" class="form-control" id="createHelpContentTitle" placeholder="Enter Help Title"
						name="createHelpContentTitle" class="text required" style="width: 49%;" path="templateName"/> 
						
					</div>
					<div style="width:40%;float:left">
					<label
							for="createHelpContentExpireDate" class="field-label" style="margin-left: 50px;">Expire Date </span></label>
					<input
						type="text" name="createHelpContentExpireDate" id="createHelpContentExpireDate" class="calendarCreateMsg-dropdown"/>
						</div>
				</div>

				<div class="form-group"  style="width: 100%;padding-top: 10%;">
					<label for="createContentSlug" style="float:left;" class="field-label">Topic Page URL : </label><span style="float:left;" class="TopicPageHostUrl"></span>
					<input type="text" style="width: 18% !important; float:left;" class="form-control" id="createContentSlug" placeholder="Enter Slug" name="createContentSlug"
						 class="text" path="templateName"> </input>
				</div>
				
				
				<div class="form-group" style="width: 100%;float:left">
					
						<label for="createHelpContentText" class="field-label">Content <span class="lbl-required">*</span></label>
				
					<textarea type="text" class="form-control" id="createHelpContentText" placeholder="Enter Content"
						name="createHelpContentText" style="width: 80%; height: 80px;" class="text required" path="templateName"></textarea>
				</div>
				<div class="form-group" style="width: 100%;float:left">
					<label for="helpContentFileData" class="form-label field-label">Upload :</label> 
					<input id="helpContentFileData" name="helpContentFileData" type="file" class="hideFileUploader">
					<div class="input-append">
						<input type="text" name="helpContentFileDataInput" id="helpContentFileDataInput" class="input-file"
							readOnly="readOnly" /><a class="add-on" onclick="$('input[id=helpContentFileData]').click();"></a>
					</div>
				</div>
				<div id="uploadFileName" style="float: left; display:none;"></div>
				<input type="hidden" class="form-control" id="createContentId" name="createContentId" > </input>
			</div>
		</form>
	</div>
	<div class="container" id="saveConfDialog" style="display: none;">
		<p style="text-align: center;">Do you want to save this content?</p>
	</div>
	<div class="container" id="publishConfDialog" style="display: none;">
		<p style="text-align: center;">Do you want to continue to publish making this available to users?</p>
	</div>
	<div class="container" id="helptopicDeleteConfDialog" style="display: none;">
		<p style="text-align: center;"></p>
	</div>
	<div class="container" id="helpContentDeleteConfDialog" style="display: none;">
		<p style="text-align: center;"></p>
	</div>
</div>
<script>
	$(function() {
		initializeHelpContentListeners();
		manageHelpContentGrid();
		determineEditTopicVisibility();
		populateTopicPageHostUrl();
	});
</script>
<script type="text/javascript" src="<c:url value='/js/tools/helpContent.js'/>">
	
</script>