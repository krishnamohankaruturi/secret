<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="css/message.css" rel="stylesheet">
<%@ include file="/jsp/include.jsp"%>
<style>
.ui-multiselect-menu {
    border: 1px solid #a7a9ac !important;
}
#announcementsView .text_hyperlink{
 	cursor: pointer;
    text-decoration: none;
    color: #0254eb;
}

#resultsQCSection{
 	margin-left: 1px;
}
.h4, h4 {
    font-size: 1em;
}
.input-large {
  width: 250px !important;
}

#addMessagesForm .form-fields{
 margin : 0px 20px 5px 25px;
}
 .bcg_select {
	width: 250px !important;
	position: relative;
	vertical-align: middle;
	white-space: nowrap;
	height: 35px;
}
.message-boxmain {
    margin: 3px;
    padding: 0px;
}



</style>
<div id="dialog-confirm" title="Create Message?" style="display:none;">
  <p id="notificationConfirmMessage"><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span></p>
</div>
<div id="announcementsView">
	<div id="managePermissionsDivMessage" class="hidden">
		<div class="_bcg">
			<div class="config wrap_bcg">
				<div id="groupIdentifier"><input id="groupId" type="hidden" /></div>
				<div id="permissionTabs" class="panel_full">
					<ul class="tabs" id="permissionTabsContent"></ul>
				</div>
				<div id="save">
					<security:authorize access="hasRole('PERM_ROLE_MODIFY')">
						<a class="btn_blue buttonBottomLeft" id="saveRolesBtn" href="javascript:void(0)">Save</a>
					</security:authorize>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="message" style="margin-top: 5%;" >
			<img alt="New Message" src="images/icons/edit_icon.png"> <a style="cursor: pointer" tabindex="0" class="text_hyperlink" title="New Message" onkeypress="newMessages(event)" onclick="newMessages(event)"><span
				style="top: 1%;" >New Message</span></a>
		</div>
		<div class="table_wrap">
			<div id="resultsQCSection" class="kite-table">
				<table class="responsive" id="messagetable"></table>

				<div id="messageCreatePager"></div>
			</div>
		</div>

		<div  id="confirm_msg" class="confirm_msg" style="display: none;">
			<input type="hidden" id="cancelNotificationId" />
			<p>Are you sure you want to cancel this message?</p>
			<p>After you click "yes", the message won't able to show on home page.</p>
		</div>

		<!-- reactive message conformation	 -->

		<div id="reactivate_confirm_msg" class="reactive_msg" style="display: none;">
			<input type="hidden" id="reactivemessageId" />
			<p>Expired message will be republished and visible to the selected user groups for the new duration.</p>
		</div>

		<div id="createMsgwindow" style="display: none;">

			<form id="addMessagesForm" name="addMessagesForm" class="form">
				<input type="hidden" id="editMessageId" />
				<div>
				<div class="message-box">
					<div>
						<h4 style="color: rgb(20, 114, 181); margin-left: 25px; margin-bottom: 0px;">Deliver To:</h4>
						<div class="message-boxmain">
							<span class="message-boxfilter-headers">
								<h4>
									<span id="" style="color: #1472b5;">ASSESSMENT PROGRAM:</span> <span style="color: red">*</span>
								</h4>
							</span>
							<span class="message-boxfilter-headers">
								<h4 style="margin-left: 0px;">
									<span style="color: #1472b5;">STATE:</span>
								</h4>
							</span>
							<span class="message-boxfilter-headers">
								<h4 style="margin-left: 0px;">
									<span style="color: #1472b5;">ROLES:</span>
								</h4>
							</span>
							
							<span class="message-boxfilters">
								<select class="wrapper-dropdown-5 bcg_select" style="margin-left: 15px; width: 90%;" disabled="disabled"
									id="assessmentProgramId" title="Assessment program" name="assessmentProgramId"></select>
							</span>
							<span class="message-boxfilters">
								<select class="wrapper-dropdown-5 bcg_select" title="State" style="margin-left: 15px;" id="stateProgramId" name="stateProgramId"
								multiple="multiple"></select>
							</span>
							<span class="message-boxfilters">
								<select class="wrapper-dropdown-5 bcg_select" title="Role" style="margin-left: 15px;" id="rolesId" name="rolesId"
									multiple="multiple"></select>
							</span>
						</div>
					</div>
					<div class="calendarCreateMsg">

						<span style="font-size: 15px;color: #434343;">Display message at</span> <span style="color: red">*</span> <input type="text"
							name="displayMessageDate" title="Display message at" id="displayMessageDate" class="calendarCreateMsg-dropdown"
							style="margin-left: 3%; height: 30px; width: 150px; background-image: url(images/date-icons.png); background-repeat: no-repeat; background-position: right center; padding-left: 25px;" />
						<input type="text" name="displayMessagetime" title="Display message Time" id="displayMessagetime" class="calendarCreateMsg-dropdown"
							style="margin-left: 3%; height: 30px; width: 125px; background-image: url(images/time-icons.png); background-repeat: no-repeat; background-position: right center; padding-left: 13px;" />
						<br> <br> <span style="font-size: 15px;color: #434343;">Expire message at</span> <span style="color: red">*</span> <input
							type="text" name="expireMessageDate" title="Message Expire Date" id="expireMessageDate" class="calendarCreateMsg-dropdown"
							style="margin-left: 23px; height: 30px; width: 150px; background-image: url(images/date-icons.png); background-repeat: no-repeat; background-position: right center; padding-left: 25px;" />

						<input type="text" name="expireMessagetime" title="Message Expire Date time" id="expireMessagetime" class="calendarCreateMsg-dropdown"
							style="margin-left: 3%; height: 30px; width: 125px; background-image: url(images/time-icons.png); background-repeat: no-repeat; background-position: right center; padding-left: 13px;" />
					</div>
					<div class="message-title">
						<span style="font-size: 15px;color: #434343;">Message Title:</span><span style="color: red">*</span><br>
						<textarea name="messageTitle" id="messageTitle" title="Message Title" class="message_title" disabled="disabled"
							style="height: 80px; width: 95%;"></textarea>
					</div>

					<div class="message-content">
						<div style="margin-bottom: 1%;color: #434343;">Message Content:</div>
						<textarea name="messageContent" title="Message Content" id="messageContent" class="message_content"></textarea>
					</div>
					<div class="message-btn">
						<button class="btnCreateMsg" type="button" id=editbtn onclick="enableNotificationForEdit();">Edit</button>
						<button class="btnCreateMsg" type="button" onclick="openMessagePreviewOverlay();" name="preview">Preview</button>
						<button class="btnCreateMsg" type="button" id="enableMessageBtn" onclick="saveMessage(false);">Enable</button>
						<button class="btnCreateMsg" type="button" onclick="closeNotificationPopup();">Cancel</button>
					</div>
				</div>
			</form>

		</div>
	</div>
</div>
<script type="text/javascript" src="<c:url value='/js/external/ckeditor/ckeditor.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/configuration/createMessage.js'/>"></script>
<script>
$(function() {
	// clear data so that time picker renders properly from second time.
	$("#displayMessagetime").removeData('timepicker-list');
	$("#expireMessagetime").removeData('timepicker-list');
	var displayMessagetime = $("#displayMessagetime").timepicker({
		'timeFormat' : 'h:i A',
		'step' : 15
	});
	var expireMessagetime = $("#expireMessagetime").timepicker({
		'timeFormat' : 'h:i A',
		'step' : 15
	});
	
	<%-- make it clear what page we're on in the nav --%>
	$('.main-nav a[href*="${page}"]').tab('show');
});
</script>
