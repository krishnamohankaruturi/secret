var createDialog = null;
$(function(){
	findUpdateMessagesInitJS();
});
function findUpdateMessagesInitJS() {
	loadAssessments();
	var $gridAuto = $("#messagetable");
	$("#messagetable").jqGrid('clearGridData');
	$("#messagetable").jqGrid("GridUnload");	

	var gridWidthForVO = 1040;
	var columnWidth=gridWidthForVO/5;
	$("#assessmentProgramId").select2({
		placeholder : 'Select'
	});
	
	if ($gridAuto[0].grid && $gridAuto[0]['clearToolbar']) {
		$gridAuto[0].clearToolbar();
	}
	var colModel = [
			{
				name : 'createdDate',
				index : 'createdDate',
				align : 'center',
				sortable : true,
				hidedlg : false,
				width :columnWidth,
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}, {
				name : 'messageTitle',
				index : 'messageTitle',
				formatter : linkFmatter1,
				align : 'center',
				width :columnWidth,
				sortable : true
			}, {
				name : 'messageId',
				index : 'messageId',
				hidden : true,
				width :columnWidth,
				sortable : true
			}, {
				name : 'assessmentProgramId',
				index : 'assessmentProgramId',
				width :columnWidth,
				hidden : true
			}, {
				name : 'stateProgramId',
				index : 'stateProgramId',
				width :columnWidth,
				hidden : true
			}, {
				name : 'expireMessageDate',
				index : 'expireMessageDate',
				width :columnWidth,
				hidden : true
			}, {
				name : 'expireMessagetime',
				index : 'expireMessagetime',
				width :columnWidth,
				hidden : true
			}, {
				name : 'messageStatus',
				index : 'messageStatus',
				align : 'center',
				stype : 'select',
				width :columnWidth,
				searchoptions : {
					sopt : [ 'eq' ],
					value : ":All;active:Active;expired:Expired;pending:Pending"
				}
			}, {
				name : 'edit',
				index : 'edit',
				formatter : editFmatter1,
				align : 'center',
				width :columnWidth,
				search : false,
				sortable : false
			}, {
				name : 'action',
				index : 'action',
				formatter : cancelformatter,
				align : 'center',
				width :columnWidth,
				search : false,
				sortable : false
			}

	]
	$("#messagetable").scb(
			{
				url : "getMessagesByAssessmentProgram.htm",
				mtype : "POST",
				datatype : "json",
				width : gridWidthForVO,
				rowNum : 5,
				rowList : [ 5, 10, 20, 30, 40, 60, 90 ],
				colNames : [ 'Created Date', 'Message Title', 'Message Id', 'assessmentProgramId', 'stateProgramId',
						"expireMessageDate", "expireMessagetime", "Status", "Edit", "Action" ],
				colModel : colModel,
				sortname : 'messageId',
				sortorder : 'desc',
				loadonce : false,
				viewable : false,
				pager : '#messageCreatePager',
				jsonReader : {
					repeatitems : false,
					page : function(obj) {
						return obj.page !== undefined ? obj.page : "0";
					},
					root : function(obj) {
						return obj.rows;
					}
				},
				beforeRequest : function() {
					var currentPage = $(this).getGridParam('page');
					var lastPage = $(this).getGridParam('lastpage');

					if (lastPage != 0 && currentPage > lastPage) {
						$(this).setGridParam('page', lastPage);
						$(this).setGridParam({
							postData : {
								page : lastPage
							}
						});
					}
				},
				   loadComplete: function(){
			       	   	 var tableid=$(this).attr('id');  
			        	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');         
			          $.each(objs, function(index, value) {         
			           var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
			                 $(value).attr('title',$(nm).text()+' filter');
			                 if ( $(value).is('select')) {
			                	   $(value).removeAttr("role");
			                	   $(value).css({"width": "100%"});
			                	   
			                    	$(value).select2({
			              	   		  placeholder:'Select',
			            	   		  multiple: false,
			            	      		  allowClear : true
			            	   		 });
			                    };
			                 });
			     	 $('.select2-hidden-accessible').removeAttr("aria-hidden"); 
			       	}
			});
	$gridAuto.jqGrid('setGridParam', {
		postData : {
			"filters" : ""
		}
	}).trigger("reloadGrid", [ {
		page : 1
	} ]);
};
function openMessagePreviewOverlay() {
	var valid = $('#addMessagesForm').validate(validateCreateMessageForm()).form();
	if (valid) {
		var messageTitle = $("#messageTitle").val();
		var messageContent=$("#messageContent").val();
		var messageId = 0;
		if ($('#editMessageId').val()) {
			messageId = $('#editMessageId').val();
		}
		var displayDate = $("#displayMessageDate").val();
		var messageDetails = {
			"messageTitle" : messageTitle,
			"messageId" : messageId,
			"createdDate" : new Date(),
			"displayDate" : displayDate,
			"messageContent":messageContent
		}
		$('#managePermissionsDivMessage').load("/AART/userHome.htm?message=" + escape(JSON.stringify(messageDetails)) + " #ep-main-site", null, function () {
			getMessageUserDetails();
		});
		$('#managePermissionsDivMessage').dialog(
				{
					autoOpen : true,
					modal : true,
					width : 1087,
					height : 700,
					title : 'Preview Message',
					create : function(event, ui) {
						var widget = $(this).dialog("widget");
						$(".ui-dialog-titlebar-close span", widget).removeClass("ui-icon-closethick").removeClass('ui-icon').addClass('btn_close');
					},
					open : function(ev, ui) {
						$('#managePermissionsDivMessage').css('pointer-events', 'none');
					},
					close : function(ev, ui) {

					}
				});
	}
}

function validateCreateMessageForm() {
	return {
		rules : {
			displayMessageDate : {
				required : true
			},
			displayMessagetime : {
				required : true
			},
			stateProgramId : {
				required : true
			},
			expireMessageDate : {
				required : true
			},
			expireMessagetime : {
				required : true
			},
			messageTitle : {
				required : true,
				maxlength : 256
			},
			messageContent : {
				required : false
			},
			onkeyup : false
		},
		messages : {
			displayMessageDate : "",
			displayMessagetime : "",
			stateProgramId : "",
			expireMessageDate : "",
			expireMessagetime : "",
			messageTitle : {
				required : ""
			}
		},
		errorPlacement : function(error, element) {
			// error.appendTo( element.parent("td").next("td") );
		}
	};
}
function showConfirmationMessage(stateProgram, rolesIds, cnfMessage) {
	$("#dialog-confirm #notificationConfirmMessage").text(cnfMessage);
	$("#dialog-confirm").dialog({
		resizable : false,
		height : "auto",
		width : 400,
		modal : true,
		buttons : {
			"OK" : function() {
				saveNotificationMessage(stateProgram, rolesIds);
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		create : function(event, ui) {
			var widget = $(this).dialog("widget");		
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
		}
	});
}

function saveNotificationMessage(stateProgram, rolesIds) {
	var messageTitle = $("#messageTitle").val();
	var messageContent = CKEDITOR.instances.messageContent.getData();
	var messageId = "0";
	$.ajax({
		url : 'saveMessage.htm',
		data : {
			messageTitle : messageTitle,
			messageContent : messageContent,
			displayMessageDate : $("#displayMessageDate").val(),
			expireMessageDate : $("#expireMessageDate").val(),
			assessmentProgramId : $("#assessmentProgramId").val(),
			stateProgramId : stateProgram,
			rolesId : rolesIds,
			displayMessagetime : $("#displayMessagetime").val(),
			expireMessagetime : $("#expireMessagetime").val(),
			messageId : $("#editMessageId").val()
		},
		dataType : 'JSON',
		type : 'POST',
		
	}).done( function(data) {
		findUpdateMessagesInitJS();
		clearDataFeilds();
		createDialog.dialog('close');
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.error('Message Saved Error Occured');
	})
}
function saveMessage(reactivateMode) {
	if ($('#enableMessageBtn').data('reactivatemode') == true) {
		$('#reactivate_confirm_msg').dialog(
		{
			autoOpen : true,
			modal : true,
			width : 400,
			title : 'Reactivate Confirmation',
			buttons : {
				"Yes" : function(event, ui) {
					reactiveMsg($(this));
				},
				"No" : function(ev, ui) {
					$(this).dialog("close");
				}
			},
			create : function(event, ui) {
				var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
			}
		});
	} else {

		var stateProgram = $('#addMessagesForm #stateProgramId').val();
		var rolesIds = ($("#rolesId").val() == null) ? [] : $("#rolesId").val();

		if (stateProgram == null) {
			stateProgram = (stateProgram == null) ? [] : stateProgram;
		}

		var valid = $('#addMessagesForm').validate(validateCreateMessageForm()).form();
		if (valid) {
			if (stateProgram.length === 0 && rolesIds.length === 0 && !reactivateMode) {
				var cnfMessage = 'This message will be visible to all user roles in all states affiliated to the assessment program.';
				showConfirmationMessage(stateProgram, rolesIds, cnfMessage);
			} else if (stateProgram.length === 0 && rolesIds.length > 0 && !reactivateMode) {
				var cnfMessage = 'This message will be visible to the selected user roles in all states affiliated to the assessment program.';
				showConfirmationMessage(stateProgram, rolesIds, cnfMessage);
			} else if (rolesIds.length === 0 && stateProgram.length > 0 && !reactivateMode) {
				var cnfMessage = 'This message will be visible to all user roles in the selected state(s).';
				showConfirmationMessage(stateProgram, rolesIds, cnfMessage);
			} else {
				saveNotificationMessage(stateProgram, rolesIds);
			}
		}
	}
}

function clearDataFeilds() {
	$("#editMessageId").val(0);
	$("#messageTitle").val("");
	$("#messageContent").val("");
	$("#displayMessageDate").val("");
	$("#expireMessageDate").val("");
	$("#stateProgramId").val("").trigger('change.select2');
	$("#rolesId").val("").trigger('change.select2');
	$("#displayMessagetime").val("");
	$("#expireMessagetime").val("");
}

function getStatesListCallback(callback, operation, editData) {
	var formData = "";
	formData = "assessmentProgramId=" + $("#assessmentProgramId").val();
	if ($("#assessmentProgramId").val() == null || $("#assessmentProgramId").val() == '' || $("#assessmentProgramId").val() == 0) {

	} else {
		$('#stateProgramId').children('option').remove();
		$.ajax({
			url : 'getStatesList.htm',
			dataType : 'json',
			data : formData,
			processData : false,
			contentType : false,
			cache : false,
			type : 'GET'
			
		}).done(function(data) {
			var htmldata = "";
			for (x in data) {
				htmldata += "<option value=" + data[x].stateId + ">" + data[x].stateName + "</option>";
			}
			$("#stateProgramId").select2('destroy');
			$("#stateProgramId").html(htmldata);
			$("#stateProgramId").select2({
				placeholder : 'Select'
			});
			
			if (editData != null && editData.stateProgramIdList) {
				$("#stateProgramId").val(editData.stateProgramIdList);
			}
			$("#stateProgramId").trigger('change.select2');
			$("#stateProgramId").prop('disabled', false);
			
			if (callback) {
				callback(operation, editData);
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
			})
	}
}

function getRolesList(callback, operation, editData) {
	var formData = "";

	formData = "rolesId=" + $("#rolesId").val();
	$('#rolesId').children('option').remove();
	$.ajax({
		url : 'getRolesForNotifications.htm',
		dataType : 'json',
		processData : false,
		contentType : false,
		cache : false,
		type : 'POST'
			
	}).done( function(data) {
		var htmldata = "";
		for (x in data.allGroups) {
			htmldata += "<option value=" + data.allGroups[x].groupId + ">" + data.allGroups[x].groupName + "</option>";
		}
		$("#rolesId").select2('destroy');
		$("#rolesId").html(htmldata);
		$("#rolesId").select2({
			placeholder : 'Select'
		});
		
		if (editData != null && editData.rolesIdList) {
			$("#rolesId").val(editData.rolesIdList);
		}
		
		$("#rolesId").prop('disabled', false);
		$("#rolesId").trigger('change.select2');
		
		if (callback) {
			callback(operation,editData);
		}
		$("#messageTitle").focus();
	})
	.fail( function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
		})
}

function newMessages(event) {
	 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }
	$("#messageTitle").removeClass('error');
	$("#editbtn").hide();
	showMessageDialog('createMessage', null);
}

function showMessageDialog(operation, data) {
	$('#addMessagesForm').find('input,textarea').removeClass('error');
	var titleMsg = 'Create New Message';
	if (operation === 'createMessage') {
		clearDataFeilds();
		$("#editMessageId").val(0);
		titleMsg = 'Create New Message';
		$('#edit').hide();
		$('#view').hide();
		$('#create').show();
		$("#editbtn").hide();
	} else if (operation === 'editMessage') {
		titleMsg = 'Edit Message';
		$('#edit').show();
		$('#view').hide();
		$('#create').hide();
	} else if (operation === 'viewMessage') {
		titleMsg = 'View Message';
		$('#edit').hide();
		$('#view').show();
	} else if (operation === 'ReactivateMessage') {
		titleMsg = 'Edit Message';
		$('#edit').show();
		$('#view').hide();
	}
	createDialog = $('#createMsgwindow').dialog(
			{
				autoOpen : false,
				modal : true,
				width : 950,
				height : 550,
				title : titleMsg,
				open : function(ev, ui) {
					$('#stateProgramId').parent().find('button').remove();
					$("#rolesId").parent().find('button').remove();
					windowPopup(operation, data);
					var widget = $(this).dialog("widget");		
					$("#createMsgwindow", widget).css('display','inline');
					$("#stateProgramId").select2({
						placeholder : 'Select'
					});
					$("#rolesId").select2({
						placeholder : 'Select'
					});
				},
				close : function(ev, ui) {
					$("#stateProgramId").select2('destroy');
					$("#rolesId").select2('destroy');
					if(CKEDITOR.instances.messageContent){
						CKEDITOR.instances.messageContent.destroy();
					}
				}
			}).dialog('open');
}

function windowPopup(operation, data) {
	var ckeditor = $("#messageContent");
	ckeditor.ckeditor();
	ckeditor.ckeditorGet().config.resize_enabled = false;
	ckeditor.ckeditorGet().config.height = 200;
	
	getStatesListCallback(function(operation, data) {
		$('#displayMessageDate').datepicker({
			minDate : new Date(),
			maxDate : '+2y',
			onSelect : function(date) {
				var selectedDate = new Date(date);
				var endDate = new Date(selectedDate.getTime());

				$('#expireMessageDate').datepicker("option", "minDate", endDate);
				$('#expireMessageDate').datepicker("option", "maxDate", '+2y');
			}
		});

		$('#expireMessageDate').datepicker();

		$('#messageTitle').removeAttr('disabled');
		if ($('button.ui-multiselect').length > 1) {
			$('button.ui-multiselect').last().remove()
		}

		$("#stateProgramId").select2('destroy');
		$("#stateProgramId").select2({
			placeholder : 'Select'
		});
		$("#stateProgramId").prop('disabled', false);
		$("#stateProgramId").trigger('change.select2');

		if (operation === 'ReactivateMessage') {
			$('#messageTitle').attr('disabled', true);
			$('#assessmentProgramId').attr('disabled', true);
			$('#editbtn').show();
			$("#stateProgramId").prop('disabled', true).trigger('change.select2');
			ckeditor.ckeditorGet().config.readOnly = true;
			$('#enableMessageBtn').data('reactivatemode', true);
		} else {
			$('#messageTitle').removeAttr('disabled');
			$('#assessmentProgramId').removeAttr('disabled');
			$("#stateProgramId").prop('disabled', false).trigger('change.select2');
			$('#editbtn').hide();
			ckeditor.ckeditorGet().config.readOnly = false;
			$('#enableMessageBtn').removeData('reactivatemode');
		}
		getRolesList(function(operation) {
			if (operation === 'ReactivateMessage') {
				$("#rolesId").prop('disabled', true).trigger('change.select2');
			} else {
				$("#rolesId").prop('disabled', false).trigger('change.select2');
			}
		}, operation, data);
	}, operation, data);
	
}

function cancelMsg(dialogElement) {
	var formData = "";
	formData = "messageId=" + $("#cancelNotificationId").val();
	$.ajax({
		url : 'cancelMessage.htm',
		dataType : 'json',
		data : formData,
		processData : false,
		contentType : false,
		cache : false,
		type : 'GET'
		
	}).done( function(data) {
		findUpdateMessagesInitJS();
		dialogElement.dialog('close');
	}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
		})
}

function closeNotificationPopup() {
	clearDataFeilds();
	createDialog.dialog('close');
	return true;
}
function enableNotificationForEdit() {

	$('#messageTitle').removeAttr('disabled');
	$('#assessmentProgramId').removeAttr('disabled');

	// CKEidtor and Multi Select Enable Them.
	$("#stateProgramId").prop('disabled', false).trigger('change.select2');
	$("#rolesId").prop('disabled', false).trigger('change.select2');
	CKEDITOR.instances.messageContent.setReadOnly(false);
}

// ORIGINAL FILE BEFORE THIS HAS BEEN ADDED FROM JSP
function loadAssessments() {

	$.ajax({
		url : 'getNotificationAssessments.htm',
		dataType : 'json',
		processData : false,
		contentType : false,
		cache : false,
		type : 'GET'
		
	}).done(function(data) {
		var selected = true;
		var htmldata = '';
		for (x in data) {
			htmldata += "<option value=" + data[x].assessmentId + " selected='true'>" + data[x].assessmentName + "</option>";
		}
		$("#assessmentProgramId").html(htmldata);
	}).fail( function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
		})

}

function expireStatusformatter(cellvalue, options, rowObject) {
	var htmlString = "Active";
	var time = new Date().getTime();
	if (rowObject.displayDate > time) {
		htmlString = "Pending";
	} else if (time > rowObject.expiryDate) {
		htmlString = "Expired";
	} else {
		htmlString = "Active";
	}
	rowObject.messageStatus = htmlString;
	return htmlString;
}
function cancelformatter(cellvalue, options, rowObject) {

	var htmlString = "";
	if (rowObject.messageStatus == 'Active') {
		htmlString = "<a href='javascript:cancelMessage(" + rowObject.messageId + ")'>Cancel</a>";
	} else if (rowObject.messageStatus == 'Expired') {
		htmlString = "<a href=\"javascript:editMessage(" + rowObject.messageId + "," + rowObject.assessmentProgramId + ","
				+ rowObject.stateProgramId + ",'ReactivateMessage'" + ")\">Reactivate</a>";
	}
	return htmlString;
}
function editFmatter1(cellvalue, options, rowObject) {
	var from3 = 'editfrom';
	var htmlString = "";
	htmlString = "<a href=\"javascript:editMessage(" + rowObject.messageId + "," + rowObject.assessmentProgramId + ","
			+ rowObject.stateProgramId + ",'editMessage'" + ")\">Edit</a>";
	return htmlString;
}
function linkFmatter1(cellvalue, options, rowObject) {
	var htmlString = "<a href=\"javascript:editMessage(" + rowObject.messageId + "," + rowObject.assessmentProgramId + ","
			+ rowObject.stateProgramId + ",'viewMessage'" + ")\">" + rowObject.messageTitle + "</a>";
	return htmlString;
}
function editMessage(mid, asid, spid, operation) {
	var formData = "";
	formData = "messageId=" + mid + "&assessmentProgramId=" + asid;
	$("#editMessageId").val(mid);
	$.ajax({
		url : 'editMessage.htm',
		dataType : 'json',
		data : formData,
		processData : false,
		contentType : false,
		cache : false,
		type : 'GET'
	
		
	})	.done( function(data) {
		// populate fields from data returned.
		$("#messageContent").val(data.messageContent);
		$("#messageTitle").val(data.messageTitle);
		$("#displayMessageDate").val(data.displayMessageDate);
		$("#expireMessageDate").val(data.expireMessageDate);
		$("#displayMessagetime").val(data.displayMessagetime);
		$("#expireMessagetime").val(data.expireMessagetime);
		$("#assessmentProgramId").val(asid);
		
		
		showMessageDialog(operation, data);

		if ($('button.ui-multiselect').length > 1) {
			$('button.ui-multiselect').last().remove();
		}

		$('#displayMessageDate').datepicker({
			minDate : new Date(),
			maxDate : '+2y',
			onSelect : function(date) {
				var selectedDate = new Date(date);
				var endDate = new Date(selectedDate.getTime());

				$('#expireMessageDate').datepicker("option", "minDate", endDate);
				$('#expireMessageDate').datepicker("option", "maxDate", '+2y');

			}
		});
		$('#expireMessageDate').datepicker();

	}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	})

}

function cancelMessage(mid) {
	$("#cancelNotificationId").val(mid);

	$('#confirm_msg').dialog(
			{
				autoOpen : true,
				modal : true,
				width : 400,
				title : 'Cancel Confirmation',
				buttons : {
					"Yes" : function(event, ui) {
						cancelMsg($(this));
					},
					"No" : function(ev, ui) {
						$(this).dialog("close");
					}
				},
				create : function(event, ui) {
					var widget = $(this).dialog("widget");
					$(".ui-dialog-buttonset", widget).css('text-align', 'center');
				}
			});
}
function reactiveMsg(dialogElement) {
	dialogElement.dialog("close");
	$('#enableMessageBtn').removeData('reactivatemode');
	saveMessage(true);
}
