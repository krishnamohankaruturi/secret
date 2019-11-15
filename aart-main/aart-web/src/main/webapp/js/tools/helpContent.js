String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

function initializeHelpContentListeners() {
	$('#createTopicName').on("change",function() {
		var slugValue = $(this).val().replaceAll(' ', '-');
		$('#createTopicSlug').val(slugValue);
	});
	$('#editTopicName').on("change",function() {
		var slugValue = $(this).val().replaceAll(' ', '-');
		$('#editTopicSlug').val(slugValue);
	});
	$('#createHelpContentTitle').on("change",function() {
		var slugValue = $(this).val().replaceAll(' ', '-');
		$('#createContentSlug').val(slugValue);
	});
}
function validateCreateTopic() {
	return {
		rules : {
			createTopicName : {
				required : true,
				maxlength : 75
			},
			createTopicDesc : {
				required : true,
				maxlength : 500
			},
			errorClass : "error",
		},
		messages : {
			createTopicName : {
				required : "This field is required",
			},
			createTopicDesc : {
				required : "This field is required",
			}
		}
	};
}

function validateEditTopic() {
	return {
		rules : {
			editTopicName : {
				required : true,
				maxlength : 75
			},
			editTopicDesc : {
				required : true,
				maxlength : 500
			},
			errorClass : "error",
		},
		messages : {
			editTopicName : {
				required : "This field is required",
			},
			editTopicDesc : {
				required : "This field is required"
			}
		}
	};
}

function validateCreateHelpContent() {
	return {
		ignore : [],
		rules : {
			createHelpContentTitle : {
				required : true,
				maxlength : 255
			},
			createHelpContentText : {
				required : true
			},
			helpContentAps : {
				required : true
			},
			createHelpContentText : {
				required: function() 
                {
                 CKEDITOR.instances.createHelpContentText.updateElement();
                }
			},
			errorClass : "error",
		},
		messages : {
			createHelpContentTitle : {
				required : "This field is required",
			},
			createHelpContentText : {
				required : "This field is required",
			},
			helpContentAps : {
				required : "This field is required",
			},
			createHelpContentText : {
				required : "This field is required"
			}
		}
	};
}

function clearHelpDataFeilds() {
	$("#editTopicId").val("");
	$("#editTopicSlug").val("");
	$("#createTopicName").val("");
	$("#createTopicDesc").val("");
	$("#createTopicName").validate().resetForm();
	$("#createTopicDesc").validate().resetForm();
	$("#editTopicName").val("");
	$("#editTopicDesc").val("");
	$("#editTopicName").validate().resetForm();
	$("#editTopicDesc").validate().resetForm();
}

$("#createHelpTopic").on("click",function(event) {
	event.preventDefault();
	var dialogTitle = "Create Topic";
	createHelpTopic(dialogTitle);
});

function showHelpOperationMessage(message) {
	$('#topicSucessMsg').text(message);
	$('#topicSucessMsg').show();
	$('#topicSucessMsg').css('display', 'inline');
	setTimeout("$('#topicSucessMsg').hide();$('#topicSucessMsg').text('');", 3000);
}
function createHelpTopic(dialogTitle) {
	clearHelpDataFeilds();
	$('#addHelpTopicForm').validate(validateCreateTopic()).form();
	$('#CreateHelpTopicWindow').dialog({
		autoOpen : false,
		modal : true,
		width : 800,
		height : 500,
		title : escapeHtml(dialogTitle),
		buttons : {
			"Save" : function(event, ui) {
				var isvalidTopic = $('#addHelpTopicForm').valid();
				if (isvalidTopic) {
					$.ajax({
						url : 'saveHelpTopic.htm',
						data : {
							name : $('#createTopicName').val(),
							description : $('#createTopicDesc').val(),
							slug : $('#createTopicSlug').val()
						},
						dataType : 'JSON',
						type : 'POST'
					}).done(function(data) {
							if (data != undefined && data != null && data != '') {
								if (data.status === 'success') {
									showHelpOperationMessage(data.successMessage);
								}
								else{
									$('#helpTopicErrorMessage').show();
									$('#helpTopicErrorMessage').text(data.errorMessage);
									setTimeout("$('#helpTopicErrorMessage').hide()", 5000);
								}
								if (data.status === 'success') {
									$('#CreateHelpTopicWindow').dialog("close");
									clearHelpTopicFormData('CreateHelpTopicWindow');
								}
							}
					}).fail(function(jqXHR, textStatus, errorThrown) {
							console.log(errorThrown);
					});
				} else {
					$('label.error').css('display', 'inline-block').css('border', '0px');
				}
			},
			"Cancel" : function(ev, ui) {
				$(this).dialog("close");
				clearHelpTopicFormData('CreateHelpTopicWindow');
			}
		},
		open : function(ev, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');     
			$(".ui-dialog-buttonset", widget).css('float', 'none');
			$('.ui-dialog-buttonset').find('button:contains("Cancel"), button:contains("Save")').addClass('btnCreateMsg');
			$('.ui-dialog-buttonset').find('button:contains("Cancel"), button:contains("Save")').removeClass('ui-button ui-corner-all ui-widget');
		},
		close : function(ev, ui) {
			$("#addHelpTopicForm").validate().resetForm();
			$("#addHelpTopicForm").find('input, textarea, select').removeClass('error');
			$("#addHelpTopicForm").find('input, textarea, select').removeData( "previousValue" );
		}
	}).dialog('open');

}

function determineEditTopicVisibility() {
	$.ajax({
		url : 'getHelpTopicCount.htm',
		data : {},
		dataType : 'JSON',
		type : 'GET'
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {
			if (data.status === 'success') {
				if (data.count >= 1) {
					$('#editTopic').show();
				} else {
					$('#editTopic').hide();
				}
			}
		}

	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

$("#editTopic").on("click",function(event) {
	event.preventDefault();
	var dialogTitle = "Edit Topic";
	editHelpTopic(dialogTitle);
});

function editHelpTopic(dialogTitle) {
	clearHelpDataFeilds();
	$('#editHelpTopicForm').validate(validateEditTopic()).form();
	$('#EditHelpTopicWindow').dialog({
		autoOpen : false,
		modal : true,
		width : 800,
		height : 500,
		title : escapeHtml(dialogTitle),
		buttons : {
			"Save" : function(event, ui) {
				var isvalidTopic = $('#editHelpTopicForm').valid();
				if (isvalidTopic) {
					$.ajax({
						url : 'updateHelpTopic.htm',
						data : {
							helpTopicId : $('#editHelpTopicDropdown').val(),
							name : $('#editTopicName').val(),
							description : $('#editTopicDesc').val(),
							slug : $('#editTopicSlug').val()
						},
						dataType : 'JSON',
						type : 'POST'
					}).done(function(data) {
							if (data != undefined && data != null && data != '') {
								if (data.status === 'success') {
									showHelpOperationMessage(data.successMessage);
								}else{
									$('#editHelpTopicErrorMessage').show();
									$('#editHelpTopicErrorMessage').text(data.errorMessage);
									setTimeout("$('#editHelpTopicErrorMessage').hide()", 5000);
								}
								if (data.status === 'success') {
									$('#EditHelpTopicWindow').dialog("close");
									clearHelpTopicFormData('EditHelpTopicWindow');
								}
							}
					}).fail(function(jqXHR, textStatus, errorThrown) {
							console.log(errorThrown);
					});
				} else {
					$('label.error').css('display', 'inline-block').css('border', '0px');
				}
			},
			"Cancel" : function(ev, ui) {
				$(this).dialog("close");
				clearHelpTopicFormData('EditHelpTopicWindow');
			},
			"Delete" : function(event, ui) {
				var isvalidTopic = $('#editHelpTopicForm').valid();
				if (isvalidTopic) {
					$.ajax({
						url : 'helpTopicEligibleForDelete.htm',
						data : {
							helpTopicId : $('#editHelpTopicDropdown').val()
						},
						dataType : 'JSON',
						type : 'GET'
					}).done(function(data) {
							if (data != undefined && data != null && data != '') {
								if (data.status === 'success') {
									if(data.eligible === true){
										showDeleteConfirmMessage('Are you sure you wish to delete the help topic? This action cannot be undone.',
												data.eligible, $('#editHelpTopicDropdown').val());
									} else {
										showDeleteConfirmMessage('This help topic is associated with help content items. '+
										'You must delete the help content before deleting the help topic.', data.eligible, $('#editHelpTopicDropdown').val());
									}
								}else{
									$('#editHelpTopicErrorMessage').show();
									$('#editHelpTopicErrorMessage').text(data.errorMessage);
									setTimeout("$('#editHelpTopicErrorMessage').hide()", 5000);
								}
							}
					}).fail(function(jqXHR, textStatus, errorThrown) {
							console.log(errorThrown);
					});
				} else {
					$('label.error').css('display', 'inline-block').css('border', '0px');
				}
			},
		},
		open : function(ev, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
			$(".ui-dialog-buttonset", widget).css('float', 'none');
			$('.ui-dialog-buttonset').find('button:contains("Cancel"),button:contains("Save"),button:contains("Delete")').addClass('btnCreateMsg');
			$('.ui-dialog-buttonset').find('button:contains("Cancel"),button:contains("Save"),button:contains("Delete")').removeClass('ui-button ui-corner-all ui-widget');
			helpTopicDropDown('editHelpTopicDropdown');
		},
		close : function(ev, ui) {
			clearHelpDataFeilds();
			$("#editHelpTopicForm").validate().resetForm();
			$("#editHelpTopicForm").find('input, textarea, select').removeClass('error');
			$("#editHelpTopicForm").find('input, textarea, select').removeData( "previousValue" );
		}
	}).dialog('open');
}

$("#editHelpTopicDropdown").on("change",function(event) {
	if ($(this).val() !== '') {
		$.ajax({
			url : 'getHelpTopic.htm',
			data : {
				helpTopicId : $(this).val()
			},
			dataType : 'JSON',
			type : 'POST'
		}).done(function(data) {
			if (data != undefined && data != null && data != '') {

				if (data.status === 'success') {
					if (data.helpTopic != null) {
						$('#EditHelpTopicWindow #editTopicId').val(data.helpTopic.id);
						$('#EditHelpTopicWindow #editTopicName').val(data.helpTopic.name);
						$('#EditHelpTopicWindow #editTopicSlug').val(data.helpTopic.slug);
						$('#EditHelpTopicWindow #editTopicDesc').val(data.helpTopic.description);
						$('#EditHelpTopicWindow #editHelpTopicForm').validate(validateEditTopic()).form();
					} else {
						// do nothing.
					}
				}
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		});
	} else {
		$('#editTopicName').val('');
		$('#editTopicDesc').val('');
		$('#editTopicSlug').val('');
		$('#editHelpTopicForm').validate(validateEditTopic()).form();
	}
});

function helpTopicDropDown(dropdownId) {
	var htSelect = $('#' + dropdownId), optionText = '';
	htSelect.find('option').filter(function() {
		return $(this).val() > 0
	}).remove().end();

	$.ajax({
		url : 'getHelpTopics.htm',
		data : {},
		dataType : 'JSON',
		type : 'GET'
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {

			if (data.status === 'success') {
				if (data.helpTopics.length > 0) {
					for (var i = 0, length = data.helpTopics.length; i < length; i++) {
						optionText = data.helpTopics[i].name;
						htSelect.append($('<option></option>').val(data.helpTopics[i].id).html(optionText));
					}
					if (data.helpTopics.length != 1) {
						htSelect.prop('disabled', false);
					}
				}
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

/* script to handle create help content window */
$("#createHelpContent").on("click",function(event) {
	$('#createHelpContentText').ckeditor();
	$('#createHelpContentExpireDate').datepicker();

	loadHelpContentFilters();

	event.preventDefault();
	var dialogTitle = "Create Help Content";
	createHelpContent(dialogTitle);
});

/* script to handle create help content window */
$("#createHelpContent").on("click",function(event) {
	$('#createHelpContentText').ckeditor();
	$('#createHelpContentExpireDate').datepicker();

	loadHelpContentFilters();

	event.preventDefault();
	var dialogTitle = "Create Help Content";
	createHelpContent(dialogTitle);
});

function createHelpContent(dialogTitle) {
	clearHelpContentFormData();
	clearHelpDataFeilds();
	$('#CreateHelpContentWindow').dialog({
		autoOpen : false,
		modal : true,
		width : 900,
		height : 600,
		title : escapeHtml(dialogTitle),
		buttons : {
			"Save" : {
				click: function(event, ui) {
				$('#createHelpContentForm').validate(validateCreateHelpContent()).form();
				$("label[for='helpContentFileDataInput']").css('float', 'left').css('clear', 'left');
				var isvalidTopic = $('#createHelpContentForm').valid();
				if (isvalidTopic) {
					saveConformationDialog();
				}else{
                    $('label.error').css('display','block').css('border','0px');
                }
				},
				id: 'SaveHelpContentButton',
				text: "Save",
				class: 'btnCreateMsg'
			},
			"Publish" : {
				click: function(event, ui) {
				$('#createHelpContentForm').validate(validateCreateHelpContent()).form();
				$("label[for='helpContentFileDataInput']").css('float', 'left').css('clear', 'left');
				var isvalidTopic = $('#createHelpContentForm').valid();
				if (isvalidTopic) {
					publishConformationDialog();
				}else{
                    $('label.error').css('display','inline-block').css('border','0px');
                }
			},
			id: 'PublishHelpContentButton',
			text: "Publish",
			class: 'btnCreateMsg'
			},
			"Cancel" : {
				id: 'CancelHelpContentButton',
				class: 'btnCreateMsg',	
				text: "Cancel",
				click : function(ev, ui) {
					$(this).dialog("close");
					clearHelpContentFormData();
				}
			}
		},
		open : function(ev, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
			$(".ui-dialog-buttonset", widget).css('float', 'none');
			$(".ui-dialog-buttonset", widget).css('margin-top', '410px');
			
			helpTopicDropDown('createHelpContentDropdown');
			$("#CreateHelpContentWindow", widget).css('display','inline');
			$('#createHelpContentTitle, #createHelpContentTags, #createContentSlug').attr('disabled', false);
		},
		close : function(ev, ui) {
			clearHelpDataFeilds();
			$("#createHelpContentForm").validate().resetForm();
			$("#createHelpContentForm").find('input, textarea, select').removeClass('error');
			$("#createHelpContentForm").find('input, textarea, select').removeData( "previousValue" );
			$('#uploadFileName').hide();
		}
	}).dialog('open');
	
	$('.ui-dialog-buttonset').find('button:contains("Save"),button:contains("Publish"),button:contains("Cancel")').removeClass('ui-button ui-corner-all ui-widget');
	
}

function loadHelpContentFilters() {
	$.ajax({
		url : 'getHelpContentAssessments.htm',
		dataType : 'json',
		processData : false,
		contentType : false,
		cache : false,
		type : 'GET'
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {
			if (data.status === 'success') {
				var selected = true;
				var htmldata = '';
				for (x in data.aps) {
					htmldata += "<option value=" + data.aps[x].id + " >" + data.aps[x].programName + "</option>";
				}
				$("#helpContentAps").html(htmldata);
				$('#helpContentAps').select2({
					placeholder : 'Select'
				}).trigger('change.select2');

				$("#helpContentAps").parent().find('div.bcg_select span.ui-multiselect').css('width', '85%');
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {

	});
	
	$.ajax({
		url : 'getHelpContentStates.htm',
		dataType : 'json',
		processData : false,
		contentType : false,
		cache : false,
		type : 'GET'
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {
			if (data.status === 'success') {
				var selected = true;
				var htmldata = '';
				for (x in data.states) {
					htmldata += "<option value=" + data.states[x].id + " >" + data.states[x].organizationName + "</option>";
				}

				$("#helpContentStates").html(htmldata);
				$('#helpContentStates').select2({
					placeholder : 'Select'
				}).trigger('change.select2');

				$("#helpContentStates").parent().find('div.bcg_select span.ui-multiselect').css('width', '85%');
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {

		});

	$.ajax({
				url : 'getHelpContentRoles.htm',
				dataType : 'json',
				processData : false,
				contentType : false,
				cache : false,
				type : 'GET'
			}).done(function(data) {
				if (data != undefined && data != null && data != '') {
					if (data.status === 'success') {
						var selected = true;
						var htmldata = '';
						for (x in data.allRoles) {
							htmldata += "<option value=" + data.allRoles[x].groupId + " >" + data.allRoles[x].groupName
									+ "</option>";
						}
						$("#helpContentRoles").html(htmldata);
						$('#helpContentRoles').select2({
							placeholder : 'Select'
						}).trigger('change.select2');

						$("#helpContentRoles").parent().find('div.bcg_select span.ui-multiselect').css('width', '85%');
					}
				}
			})
			.fail(function(jqXHR, textStatus, errorThrown) {

			});
}

/* handling tags */
function split(val) {
	return val.split(/,\s*/);
}
function extractLast(term) {
	return split(term).pop();
}
$("#createHelpContentTags").on("keydown", function(event) {
	if (event.keyCode === $.ui.keyCode.TAB && $(this).autocomplete("instance").menu.active) {
		event.preventDefault();
	}
}).autocomplete({
	source : function(request, response) {
		$.ajax({
			url : 'getHelpTags.htm',
			dataType : 'json',
			data : {
				term : request.term
			},
			type : 'GET'
		}).done(function(data) {
			if (data != undefined && data != null && data != '') {
				if (data.status === 'success') {
					response($.map(data.helpTags, function(item) {
						return {
							label : item.tag,
							value : item.tag,
							id : item.id
						};
					}));
				}
			}
		});
	},
	minLength : 2,
	select : function(event, ui) {
		var terms = split(this.value);
		// remove the current input
		terms.pop();
		// add the selected item
		terms.push(ui.item.value);
		// add placeholder to get the comma-and-space at the end
		terms.push("");
		this.value = terms.join(", ");
		return false;
	},
	search : function() {
		// custom minLength
		var term = extractLast(this.value);
		if (term.length < 2) {
			return false;
		}
	},
	focus : function() {
		// prevent value inserted on focus
		return false;
	},
});

function saveHelpContent(operation) {
	var isvalidTopic = $('#createHelpContentForm').valid();
	if (isvalidTopic) {
		var assessmentProgramIds = ($("#helpContentAps").val() == null) ? [] : $("#helpContentAps").val();
		var stateIds = ($("#helpContentStates").val() == null) ? [] : $("#helpContentStates").val();
		var rolesIds = ($("#helpContentRoles").val() == null) ? [] : $("#helpContentRoles").val();

		var helpTopicId = $("#createHelpContentDropdown").val();
		var helpContentTitle = $("#createHelpContentTitle").val();
		var helpContentText = $("#createHelpContentText").val();
		var helpContentTags = $("#createHelpContentTags").val();
		var expireHelpContentDate = $("#createHelpContentExpireDate").val();

		var fd = new FormData();
		var filedata = $('#helpContentFileData');
		var filelist = filedata[0].files;
		var file = filelist[0];
		fd.append('uploadFile', file);

		fd.append('assessmentProgramIds', assessmentProgramIds);
		fd.append('stateIds', stateIds);
		fd.append('rolesIds', rolesIds);
		fd.append('helpTopicId', helpTopicId);
		fd.append('helpContentTitle', helpContentTitle);
		fd.append('helpContentText', helpContentText);
		fd.append('helpContentTags', helpContentTags);
		fd.append('expireHelpContentDate', expireHelpContentDate);
		fd.append('operation', operation);
		fd.append('slug', $('#createContentSlug').val());
		fd.append('helpContentId', $('#createContentId').val());
		$.ajax({
			url : 'saveHelpContent.htm',
			data : fd,
			dataType : 'json',
			processData : false,
			contentType : false,
			cache : false,
			type : 'POST'
		}).done(function(data) {
			if (data != undefined && data != null && data != '') {
				if (data.status === 'success') {
					showHelpOperationMessage(data.successMessage);
				}else{
					$('#helpContentErrorMessage').show();
					$('#helpContentErrorMessage').text(data.errorMessage);
					setTimeout("$('#helpContentErrorMessage').hide()", 5000);
					return false;
				}
				if (data.status === 'success') {
					$('#CreateHelpContentWindow').dialog("close");
					$("#helpContentTable").trigger('reloadGrid');
					clearHelpContentFormData();
					$('#createHelpContentTitle, #createHelpContentTags, #createContentSlug').attr('disabled', false);
					$('#PublishHelpContentButton, #SaveHelpContentButton').show();
					return true;
				}	
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		});
	}
	return true;
}

function populateTopicPageHostUrl() {
	$.ajax({
		url : 'getTopicPageHostUrl.htm',
		data : {},
		dataType : 'json',
		type : 'GET'
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {
			if (data.status === 'success') {
				$('.TopicPageHostUrl').text(data.topicPageHostUrl)
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

function manageHelpContentGrid() {
	var $gridAuto = $("#helpContentTable");
	$("#helpContentTable").jqGrid('clearGridData');
	$("#helpContentTable").jqGrid("GridUnload");	
	var gridWidthForVO = 760;

	if ($gridAuto[0].grid && $gridAuto[0]['clearToolbar']) {
		$gridAuto[0].clearToolbar();
	}
	var colModel = [
			{
				name : 'id',
				index : 'id',
				hidden : true,
				sortable : true,
				searchoptions : {title:"Help Id"}
			},
			{
				name : 'createdDate',
				index : 'createdDate',
				align : 'center',
				sortable : true,
				hidedlg : false,
				searchoptions : {title:"Created Date"},
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}, {
				name : 'helpTopicName',
				index : 'helpTopicName',
				align : 'center',
				sortable : true,
				searchoptions : {title:"Topic"}
			}, {
				name : 'helpTitle',
				index : 'helpTitle',
				formatter : viewHelpContentFormatter,
				align : 'center',
				sortable : true,
				searchoptions : {title:"Help Title"}
			}, {
				name : 'programName',
				index : 'programName',
				hidden : true,
				searchoptions : {title:"Program"}
			}, {
				name : 'stateName',
				index : 'stateName',
				hidden : true,
				searchoptions : {title:"States"}
			}, {
				name : 'expireDate',
				index : 'expireDate',
				searchoptions : {title:"Expire Date"},
				hidden : true,
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}, {
				name : 'status',
				index : 'status',
				align : 'center',
				stype : 'select',
				searchoptions : {
					sopt : [ 'eq' ],
					value : ":All;active:Active;expired:Expired;pending:Pending"
				}
			},
			{ name: 'actions', width: 105, sortable: false, hidedlg: true, search: false, searchoptions : {title:"Action"},
				formatter : helpContentActionFormatter
			},{
				name : 'roles',
				index : 'roles',
				hidden : true,
				searchoptions : {title:"Roles"}
			},{
				name : 'fileName',
				index : 'fileName',
				hidden : true,
				searchoptions : {title:"File Name"}
			}, {
				name : 'createdBy',
				index : 'createdBy',
				hidden : true,
				searchoptions : {title:"Created By"}
			},{
				name : 'modifiedBy',
				index : 'modifiedBy',
				hidden : true,
				searchoptions : {title:"Modified By"}
			},{
				name : 'modifiedDate',
				index : 'modifiedDate',
				align : 'center',
				sortable : true,
				hidedlg : false,
				searchoptions : {title:"Modified Date"},
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			},]
	$("#helpContentTable").scb(
			{
				url : "getAllHelpTopics.htm",
				mtype : "POST",
				datatype : "json",
				width : gridWidthForVO,
				rowNum : 5,
				rowList : [ 5, 10, 20, 30, 40, 60, 90 ],
				colNames : [ 'Help Id', 'Created Date', 'Topic', 'Help Title', 'Program', 'States', "Expire Date", "Status",
						"Action", "Roles", "File Name","Created By","Modified By","Modified Date"],
				colModel : colModel,
				sortname : 'id',
				sortorder : 'desc',
				loadonce : false,
				viewable : false,
				pager : '#helpContentPager',
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

function editHelpContentFormatter(cellvalue, options, rowObject) {
	var htmlString = "";
	htmlString = "<a href=\"javascript:editHelpContent(" + rowObject.id + ",'edit'" + ")\">Edit</a>";
	return htmlString;
}

function viewHelpContentFormatter(cellvalue, options, rowObject) {
	var htmlString = "<a href=\"javascript:viewHelpContent(" + rowObject.id + ",'view'" + ")\">" + rowObject.helpTitle + "</a>";
	return htmlString;
}

function editHelpContent(id, operation) {
	$('#createHelpContentText').ckeditor();
	$('#createHelpContentExpireDate').datepicker();

	loadHelpContentFilters();

	if (operation != 'edit' && operation != 'view') {
		event.preventDefault();
	}
	var dialogTitle = "Edit Help Content";
	createHelpContent(dialogTitle);

	populateHelpContent(id);
	
	$('#createHelpContentTitle, #createHelpContentTags, #createContentSlug').attr('disabled', false);
	$('#PublishHelpContentButton, #SaveHelpContentButton').show();
}

function viewHelpContent(id, operation) {
	$('#createHelpContentText').ckeditor();
	$('#createHelpContentExpireDate').datepicker();

	loadHelpContentFilters();

	if (operation != 'edit' && operation != 'view') {
		event.preventDefault();
	}
	var dialogTitle = "View Help Content";
	createHelpContent(dialogTitle);
	populateHelpContent(id);
	if(operation === 'view'){
		$('#createHelpContentTitle, #createHelpContentTags, #createContentSlug').attr('disabled', true);
		$('#PublishHelpContentButton, #SaveHelpContentButton').hide();
	}
	
}

function populateHelpContent(id) {
	$.ajax({
		url : 'getHelpContentById.htm',
		data : {
			id : id
		},
		dataType : 'JSON',
		type : 'GET' 
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {
			if (data.status === 'success') {
				$('#createContentId').val(data.helpContent.id);
				$('#createHelpContentTitle').val(data.helpContent.helpTitle);
				$('#createHelpContentDropdown').find('option').each(function() {
					if (data.helpContent.helpTopic.id == $(this).val()) {
						$(this).attr('selected', 'selected');
					}
				});

				$('#helpContentAps').find('option').each(function() {
					var apOptionVal = $(this).val();
					var apOption = $(this);
					$.each(data.helpContent.helpContentContext, function(i, ap) {
						if (data.helpContent.helpContentContext[i].assessmentProgramId == apOptionVal) {
							apOption.attr('selected', 'selected');
						}
					});
				});
				$('#helpContentAps').trigger('change.select2');
				$("#helpContentAps").parent().find('div.bcg_select span.ui-multiselect').css('width', '85%');

				$('#helpContentStates').find('option').each(function() {
					var apOptionVal = $(this).val();
					var apOption = $(this);
					$.each(data.helpContent.helpContentContext, function(i, ap) {
						if (data.helpContent.helpContentContext[i].stateId == apOptionVal) {
							apOption.attr('selected', 'selected');
						}
					});
				});
				$('#helpContentStates').trigger('change.select2');
				$("#helpContentStates").parent().find('div.bcg_select span.ui-multiselect').css('width', '85%');

				$('#helpContentRoles').find('option').each(function() {
					var apOptionVal = $(this).val();
					var apOption = $(this);
					$.each(data.helpContent.helpContentContext, function(i, ap) {
						if (data.helpContent.helpContentContext[i].rolesId == apOptionVal) {
							apOption.attr('selected', 'selected');
						}
					});
				});
				$('#helpContentRoles').trigger('change.select2');
				$("#helpContentRoles").parent().find('div.bcg_select span.ui-multiselect').css('width', '85%');

				var tagsData = '';
				$.each(data.helpContent.helpTags, function(i, ap) {
					if (data.helpContent.helpTags[i].tag.trim() != '') {
						tagsData = tagsData + data.helpContent.helpTags[i].tag + ', ';
					}
				});

				$('#createHelpContentTags').val(tagsData);

				$('#createHelpContentExpireDate').val(data.helpContent.expireDate);
				$('#createHelpContentExpireDate').datepicker();

				$('#createContentSlug').val(data.helpContent.slug);

				$('#createHelpContentText').val(data.helpContent.content);
				
				$('#uploadFileName').text(data.helpContent.fileName);
				$('#uploadFileName').show();
				
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

$('input[id=helpContentFileData]').on("change",function() {
	$('#helpContentFileDataInput').val($('#helpContentFileData')[0].files[0].name);
});

function clearHelpUploadFile() {
	$('#helpContentFileData').val("");
	$('#helpContentFileDataInput').val("");
}

function clearHelpContentFormData() {
	clearHelpUploadFile();
	$('#CreateHelpContentWindow input, #CreateHelpContentWindow textarea').val("");
}

function clearHelpTopicFormData(dialogId) {
	$('#' + dialogId + ' input,#' + dialogId + ' textarea').val("");
	$('#createTopicSlug').val('');
}

function saveConformationDialog() {
	$('#saveConfDialog').dialog({
		autoOpen : false,
		modal : true,
		width : 400,
		height : 200,
		buttons : {
			"OK" : function(event, ui) {
				saveHelpContent('save');
				$(this).dialog("close");
			},
			"Cancel" : function(ev, ui) {
				$(this).dialog("close");
			}
		},
		open : function(ev, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
			$(".ui-dialog-buttonset", widget).css('float', 'none');
			$('.ui-dialog-buttonset').find('button:contains("OK"), button:contains("Cancel") ').addClass('btnCreateMsg');
			$('.ui-dialog-buttonset').find('button:contains("OK"), button:contains("Cancel")').removeClass('ui-button ui-corner-all ui-widget');
		},
		close : function(ev, ui) {

		}
	}).dialog('open');

}

function publishConformationDialog() {
	$('#publishConfDialog').dialog({
		autoOpen : false,
		modal : true,
		width : 400,
		height : 200,
		buttons : {
			"OK" : function(event, ui) {
				saveHelpContent('publish');
				$(this).dialog("close");
			},
			"Cancel" : function(ev, ui) {
				$(this).dialog("close");
			}
		},
		open : function(ev, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
			$(".ui-dialog-buttonset", widget).css('float', 'none');
			$('.ui-dialog-buttonset').find('button:contains("OK"), button:contains("Cancel") ').addClass('btnCreateMsg');
			$('.ui-dialog-buttonset').find('button:contains("OK"), button:contains("Cancel")').removeClass('ui-button ui-corner-all ui-widget');
		},
		close : function(ev, ui) {

		}
	}).dialog('open');

}

function helpContentActionFormatter(cellValue, options, rowObject){
	return '<div id="hcActions_'+rowObject.id+'" data-helpcontentid="'+ rowObject.id+'" align="center">' +
		'<span id="viewHelpContent_'+rowObject.id+'" class="hcjqactions">'+ 
		'<span onclick="viewHelpContent('+rowObject.id+','+'\'view\''+');" class="ui-icon ui-icon-newwin" title="View Help Content"></span>'+
		'</span>' +
		'<span id="editHelpContent_'+rowObject.id+'" class="hcjqactions">'+
		'<span onclick="editHelpContent('+rowObject.id+','+'\'edit\''+');" class="ui-icon ui-icon-pencil" title="Edit Help Content"></span>'+
		'</span>' +
		'<span id="deleteHelpContent_'+rowObject.id+'" class="hcjqactions">'+
		'<span onclick="deleteHelpContent('+rowObject.id+','+'\'delete\''+');" class="ui-icon ui-icon-trash" title="Delete Help Content"></span>'+
		'</span>' +
	'</div>';
}

function showDeleteConfirmMessage(message, eligible, helpTopicId) {
	$('#helptopicDeleteConfDialog').find('p').text(message);
	if(eligible){
		$('#helptopicDeleteConfDialog').dialog({
			autoOpen : false,
			modal : true,
			width : 400,
			height : 200,
			buttons : {
				"Yes" : function(event, ui) {
					if(eligible){
						deleteHelpTopic(helpTopicId);
					}
					$(this).dialog("close");
				},
				"No" : function(ev, ui) {
					$(this).dialog("close");
					$('#EditHelpTopicWindow').dialog("close");
				}
			},
			open : function(ev, ui) {
				var widget = $(this).dialog("widget");
				$(".ui-dialog-buttonset", widget).css('text-align', 'center');
				$(".ui-dialog-buttonset", widget).css('float', 'none');
				$('.ui-dialog-buttonset').find('button:contains("Yes"), button:contains("No") ').addClass('btnCreateMsg');
				$('.ui-dialog-buttonset').find('button:contains("Yes"), button:contains("No")').removeClass('ui-button ui-corner-all ui-widget');
			},
			close : function(ev, ui) {

			}
		}).dialog('open');
	} else {
		$('#helptopicDeleteConfDialog').dialog({
			autoOpen : false,
			modal : true,
			width : 400,
			height : 200,
			buttons : {
				"OK" : function(event, ui) {
					$(this).dialog("close");
					$('#EditHelpTopicWindow').dialog("close");
				}
			},
			open : function(ev, ui) {
				var widget = $(this).dialog("widget");
				$(".ui-dialog-buttonset", widget).css('text-align', 'center');
				$(".ui-dialog-buttonset", widget).css('float', 'none');
				$('.ui-dialog-buttonset').find('button:contains("OK")').addClass('btnCreateMsg');
				$('.ui-dialog-buttonset').find('button:contains("OK")').removeClass('ui-button ui-corner-all ui-widget');
			},
			close : function(ev, ui) {

			}
		}).dialog('open');
	}
}

function deleteHelpTopic(helpTopicId){
	$.ajax({
		url : 'deleteHelpTopic.htm',
		data : {
			helpTopicId : helpTopicId
		},
		dataType : 'JSON',
		type : 'POST'
	}).done(function(data) {
		if (data != undefined && data != null && data != '') {
			if (data.status === 'success') {
				showHelpOperationMessage(data.successMessage);
			}
			else{
				$('#helpTopicErrorMessage').show();
				$('#helpTopicErrorMessage').text(data.errorMessage);
				setTimeout("$('#helpTopicErrorMessage').hide()", 5000);
			}
			if (data.status === 'success') {
				$('#EditHelpTopicWindow').dialog("close");
				clearHelpTopicFormData('EditHelpTopicWindow');
			}
		}
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
	});
}

function deleteHelpContent(helpContentId, operation){
	$('#helpContentDeleteConfDialog').find('p').text('Are you sure you wish to delete the help content? This action cannot be undone.');
	$('#helpContentDeleteConfDialog').dialog({
		autoOpen : false,
		modal : true,
		width : 400,
		height : 200,
		buttons : {
			"Yes" : function(event, ui) {
				$.ajax({
					url : 'deleteHelpContent.htm',
					data : {
						helpContentId : helpContentId
					},
					dataType : 'JSON',
					type : 'POST'
				}).done(function(data) {
						if (data != undefined && data != null && data != '') {
							if (data.status === 'success') {
								showHelpOperationMessage(data.successMessage);
							}else{
								$('#helpContentErrorMessage').show();
								$('#helpContentErrorMessage').text(data.errorMessage);
								setTimeout("$('#helpContentErrorMessage').hide()", 5000);
								return false;
							}
							if (data.status === 'success') {
								$("#helpContentTable").trigger('reloadGrid');
								return true;
							}	
						}
					})
					.fail(function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					});
				$(this).dialog("close");
			},
			"No" : function(ev, ui) {
				$(this).dialog("close");
			}
		},
		open : function(ev, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-buttonset", widget).css('text-align', 'center');
			$(".ui-dialog-buttonset", widget).css('float', 'none');
			$('.ui-dialog-buttonset').find('button:contains("Yes"), button:contains("No") ').addClass('btnCreateMsg');
			$('.ui-dialog-buttonset').find('button:contains("Yes"), button:contains("No")').removeClass('ui-button ui-corner-all ui-widget');
			$('.ui-dialog').removeAttr("role");
		},
		close : function(ev, ui) {

		}
	}).dialog('open');
}