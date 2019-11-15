var uploadFileData = $('#includeExcludeFileData');

$('#uploadIncludeExclude').on("click",function() {
	if ($('#uploadIncludeExclude').attr("disabled") == "disabled") {
		event.preventDefault();
	} else {
		uploadIncludeExcludeFile();
	}

});

$('input[id=includeExcludeFileData]').on("change",function() {
    $('#includeExcludeFileDataInput').val($('#includeExcludeFileData')[0].files[0].name);
});

function uploadIncludeExcludeFile(continueOnWarning) {
	var fd = new FormData();
	var filedata = $('#includeExcludeFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];

	fd.append('uploadFile', file);
	if (typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
		fd.append('continueOnWarning', continueOnWarning);
	}
	// Pass the selected operational window id
	var operationalWindowId = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow');
	fd.append('operationalWindowId',  operationalWindowId);
	if(file!=null && file.name != null && file.name != '' && file.name != undefined && file.name.toLowerCase().indexOf('csv') > -1){
		if(filelist.length > 0){
			$.ajax({
				url : 'uploadIncludeExclude.htm',
				data : fd,
				dataType : 'json',
				processData : false,
				contentType : false,
				cache : false,
				type : 'POST'
			}).done(function(data) {
				if (data.showWarning) {
					$('<div></div>').html(data.warningMessage).dialog({
						height : 190,
						width : 460,
						modal : true,
						closeOnEscape : false,
						buttons : {
							"Yes" : function() {
								uploadIncludeExcludeFile(true);
								$(this).dialog("close");
							},
							"Cancel" : function() {
								$(this).dialog("close");
							}
						}
					});

					$('#uplodResults').removeAttr('disabled');
				} else if (data.errorFound) {
					$('#resultsuserUploadReport')
							.html(
									'<span class="error_message ui-state-error">Error Msg: Failed to Upload Include/Exclude students file.</span>')
							.show();
					$('#uplodResults').removeAttr('disabled');
				} else {
					monitorUploadIncludeExcludeFile(data.uploadFileRecordId);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				$('#load_resultsuploadUserGridTableId').hide();
				var e = errorThrown;
			});
		} else {
			$('#resultsuserUploadReport')
			.html(
					'<span class="error_message ui-state-highlight">Warning Msg: Please select Include/Exclude students file to upload.</span>')
			.show();
			$('#uplodResults').removeAttr('disabled');
			return false;
		}
	}else {
		$('#resultsuserUploadReport')
		.html(
				'<span class="error_message ui-state-highlight">Warning Msg: Only CSV files are supported for upload</span>')
		.show();
		$('#uplodResults').removeAttr('disabled');
		return false;
	}
}

function monitorUploadIncludeExcludeFile(uploadFileRecordId) {
	$.ajax({
		url : 'monitorUploadIncludeExcludeFile.htm',
		type : 'GET',
		cache : false,
		data : {
			uploadFileRecordId : uploadFileRecordId
		}
	}).done(function(data) {
		uploadReport = '';
		$('#resultsuserUploadReport').html('');
		if (data.uploadFileStatus === 'FAILED') {
			if (data.errorFound) {
				$('#resultsuserUploadReport').html('<span class="error_message ui-state-error">Upload Failed</span>').show();

			}
		} else if (data.uploadFileStatus === 'COMPLETED') {
			if (data == null || data == undefined) {
				$('#resultsuserUploadReport').html('');
				var messaget = 'Systerm error !!! Please Contact System administrator.';
				$('#UserInvalidFormatMessage').html(messaget);
				$("#UserARTSmessages,#UserInvalidFormatMessage").show();
				setTimeout("aart.clearMessages()", 10000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
					$('#UserInvalidFormatMessage').html('');
				}, 10000);
			} else if (data.showWarning) {
				$('#resultsuserUploadReport').html('');
				$('<div></div>').html(data.warningMessage).dialog({
					height : 190,
					width : 460,
					modal : true,
					closeOnEscape : false,
					buttons : {
						"Yes" : function() {
							uploadIncludeExcludeFile(true);
							$(this).dialog("close");
						},
						"Cancel" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			if (data.errorFound) {
				$('#resultsuserUploadReport').html('<span class="error_message ui-state-error">Upload Failed</span>').show();
			} else {
				if (data != null && data != undefined && data.inValidDetail != null && data.inValidDetail != undefined) {
					var invdtl = data.inValidDetail;
					var messaget = invdtl.fieldName + ' ' + invdtl.invalidType.message;
					$('#UserInvalidFormatMessage').html(messaget);
					$("#UserARTSmessages,#UserInvalidFormatMessage").show();
					setTimeout("aart.clearMessages()", 10000);
					setTimeout(function() {
						$("#UserARTSmessages").hide();
						$('#UserInvalidFormatMessage').html('');
					}, 10000);
				} else {

					if (data.uploadCompleted == true) {
						uploadReport += "Upload Completed Successfully. ";
					} else {
						uploadReport += "Upload Failed. ";
					}
					if (data.recordsCreatedCount != undefined && data.recordsRejectedCount != undefined
							&& data.recordsUpdatedCount != undefined) {
						uploadReport += " Rejected:" + data.recordsRejectedCount + " Created:" + data.recordsCreatedCount
						+ " Updated:" + data.recordsUpdatedCount;
					}

					if(data.inValidRecords && data.recordsRejectedCount > 0) {
						uploadReport += '<br/><a href="javascript:showIncludeExcludeStudentsInvalidDetails()">View Details </a>';
						var uploadDetails = '<table class="gridtable">';
						uploadDetails += '<tr><th>Include/Exclude</th> <th>Student Id</th> <th>Operational Test Window Id</th> ';
						uploadDetails += '<th>Reasons For Not Valid </th> </tr>';	
						
						var invalidRecords = data.inValidRecords;
						for (var i=0;i<invalidRecords.length;i++) {	
							uploadDetails += '<tr>';
							var identifier = '';
							var reasons = '';
							var inValidDetails = invalidRecords[i].inValidDetails;
							var include=invalidRecords[i].exclude;
							var studentId = invalidRecords[i].stateStudentIdentifier;
							var operationalTestWindowId = data.OperationalWindowId;
							for (var j=0;j<inValidDetails.length;j++) {
								var fieldName = inValidDetails[j].fieldName;
								var formattedFieldValue = inValidDetails[j].formattedFieldValue;
								var invalidType = inValidDetails[j].invalidType;
								
								var isRejected = inValidDetails[j].rejectRecord;
								if(isRejected){
									// Custom handling of fields
									reasons += fieldName + " : " +inValidDetails[j].inValidMessage + "<br/>";
								}
							}
							if(invalidRecords[i].failedReason != null && invalidRecords[i].failedReason!=''){
								reasons += invalidRecords[i].failedReason + "<br/>";
							}
							uploadDetails += '<td>' + include +'</td>'
							uploadDetails += '<td>' + studentId +'</td>'
							uploadDetails += '<td>' + operationalTestWindowId +'</td>'
							uploadDetails += '<td>' + reasons +'</td>';
							
							uploadDetails += '</tr>';
						}							
						uploadDetails += '</table>';
				        var failedfileUrl = 'downloadFailedIncludeExcludeStudents.htm?uploadFileRecordId='+ uploadFileRecordId;
						uploadDetails += "<div><a href='"+ failedfileUrl +"'>Export Records That Failed to Update to a New CSV File</a></div>";
						$('#resultsIncludeExcludeStudentUploadResults').html(uploadDetails);
					}
					
					$('#resultsuserUploadReport').html(uploadReport);
					$('#resultsuserUploadReport').show();
				}
			}
			$('#uplodResults').removeAttr('disabled');
		} else {
			$('#resultsuserUploadReport').html(
					'<span style="color:blue">Msg: Upload Include/Exclude Students CSV file is in-progress.</span>').show();
			window.setTimeout(function() {
				monitorUploadIncludeExcludeFile(uploadFileRecordId)
			}, 10000);
		}
	});
}

function showIncludeExcludeStudentsInvalidDetails() {
	$('#resultsIncludeExcludeStudentUploadResults').show();
	$('#includeExcludeDialog').dialog({width : 'auto', height : 'auto', position:{
        'my': 'center',
        'at': 'center'
    }
    });
}