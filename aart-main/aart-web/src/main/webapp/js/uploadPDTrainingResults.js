
$(function() {
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required') || element.attr('type') == 'file') {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});
	
	$('#resultsuserUploadReportDetails').dialog({
		resizable: false,
		height: 700,
		width: 1200,
		modal: true,
		autoOpen:false,
		buttons: {
		    Close: function() {
		    	 $(this).dialog('close');
		    }			    
		}
	});
	
	//Validation rules for the filter form.
	$('#uploadPDTrainingResultsForm').validate({
		ignore: "",
		rules: {
			pdResultsFileData: {
	      		required: true,
	      		extension: "csv"
	    	}
	  	}
	});
	/*initUploadGrid();*/
	
	/**
	 * This method is called when user clicks on upload button to upload users.
	 */
	$('#uplodResults').on("click",function(event) {
		if($('#uplodResults').attr("disabled")=="disabled") {
			event.preventDefault();
    	} else {
    		//alert('hi');
    		uploadUsers();
    		$("#pdResultsFileDataInput").val("");
    		$("#recordsRejectedCount").html("");
			$("#recordsSkippedCount").html("");
	    	$("#uploadFileStatus").html("");
	    	$("#recordsUpdatedCount").html("");
	    	$("#recordsCreatedCount").html("");
    	}		
	});
	
	$('input[id=pdResultsFileData]').on("change",function() {
		$('#pdResultsFileDataInput').val($('#pdResultsFileData')[0].files[0].name);
	});
	
	function uploadUsers(continueOnWarning) {
		aart.clearMessages();
		$("#UserARTSmessages").hide();
		//$('#resultsuserUploadReport').html('').hide();
		//alert($('#uploadPDTrainingResultsForm').valid());
		if($('#uploadPDTrainingResultsForm').valid()) {
			$('#uplodResults').attr("disabled","disabled");
			$('#load_resultsuploadUserGridTableId').show();
			
			var fd = new FormData();
			var filedata = $('#pdResultsFileData');
			var filelist = filedata[0].files;
			var file = filelist[0];
						  
			fd.append('uploadFile',file);
			  	
			 if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
				  	fd.append('continueOnWarning',continueOnWarning);
			 }
			 
			 //alert('hi');
			  $.ajax({
				url: 'uploadPDTrainingResults.htm',//uploadUser.htm
			    data: fd,
			    dataType: 'json',
			    processData: false,
			    contentType: false,
			    cache: false,
			    type: 'POST',
			    beforeSend: function() {
			    	$("#uploadFileStatusInprogress").html("<td>Upload File Status &emsp;&emsp;</td><td>: INPROGRESS</td>");
			    }
			  }).done(function(data){
			    	//alert(data);
				  $("#uploadFileStatusInprogress").html("");
			    	$("#recordsRejectedCount").html("<td>Records Rejected Count</td><td>: " + data.recordsRejectedCount +"</td>");
			    	$("#recordsSkippedCount").html("<td>Records Skipped Count</td><td>: " + data.recordsSkippedCount +"</td>");
			    	$("#uploadFileStatus").html("<td>Upload File Status</td><td>: " + data.uploadFileStatus +"</td>");
			    	$("#recordsUpdatedCount").html("<td>Records Updated Count</td><td>: " + data.recordsUpdatedCount +"</td>");
			    	$("#recordsCreatedCount").html("<td>Records Created Count</td><td>: " + data.recordsCreatedCount +"</td>");
			    	 if(data.showWarning) {
			    		$('#resultsuserUploadReport').html('');
			    		$('<div></div>').html(data.warningMessage).dialog({
			    		      height: 190,
			    		      width: 460,
			    		      modal: true,
			    		      closeOnEscape: false,
			    		      buttons: {
			    		        "Yes": function() {
			    		        	uploadUsers(true);
			    		        	$(this).dialog("close");
			    		        },
			    		      	"Cancel": function() {
			    		          	$(this).dialog( "close" );
			    		       	}
			    		      }
			    		});
			    		$('#uplodResults').removeAttr('disabled');
			    	} else if(data.errorFound) {
			    		$('#resultsuserUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to Upload PD Training Results file.</span>').show();
			    		$('#uplodResults').removeAttr('disabled');
			    	} else {			    		
			    		//alert(data.uploadFileRecordId);
			    		$('#resultsuserUploadReport').html('<span style="color:blue">Msg: Upload PD Training Results CSV file is in-progress.</span>').show();
			    		monitorUploadPDResultsFile(data.uploadFileRecordId);
			    	}
			    }).fail(function(jqXHR,textStatus,errorThrown) {
			    	$('#load_resultsuploadUserGridTableId').hide();
			    	var e=errorThrown;
			    });
		} else{
			$("#UserARTSmessages,#UserrequiredMessage").show();
			setTimeout("aart.clearMessages()", 10000);
			setTimeout(function(){ $("#UserARTSmessages").hide(); },10000);
		}	
	}
});

function uploadPdResultsFileDta(event){
	 if(event.type=='keypress'){
	  if(event.which !=13){
	   return false;
	  }
	 }
	 $('input[id=pdResultsFileData]').click();
	}


function monitorUploadPDResultsFile(uploadFileRecordId){
	$.ajax({
        url: 'monitorUploadPDTrainingResultsFile.htm',
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function(data) {
    	if(data.uploadFileStatus === 'FAILED'){
    		if(data.errorFound) {
	    		$('#resultsuserUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to Upload User PD Training Results CSV.</span>').show();
	    	}
    	}else if(data.uploadFileStatus === 'COMPLETED'){
	    	$('#load_resultsuploadUserGridTableId').hide();
	    	if(data == null || data == undefined){
	    		$('#resultsuserUploadReport').html('');
	    		var messaget = 'Systerm error !!! Please Contact System administrator.';
	    		$('#UserInvalidFormatMessage').html(messaget);
	    		$("#UserARTSmessages,#UserInvalidFormatMessage").show();
				setTimeout("aart.clearMessages()", 10000);
				setTimeout(function(){ $("#UserARTSmessages").hide(); $('#UserInvalidFormatMessage').html('');},10000);						
	    	} else if(data.showWarning) {
	    		$('#resultsuserUploadReport').html('');
	    		$('<div></div>').html(data.warningMessage).dialog({
	    		      height: 190,
	    		      width: 460,
	    		      modal: true,
	    		      closeOnEscape: false,
	    		      buttons: {
	    		        "Yes": function() {
	    		        	uploadUsers(true);
	    		        	$(this).dialog("close");
	    		        },
	    		      	"Cancel": function() {
	    		          	$(this).dialog( "close" );
	    		       	}
	    		      }
	    		});
	    	} if(data.errorFound) {
	    		$('#resultsuserUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to Upload PD Training Results CSV file.</span>').show();
	    	}
	    	else {
		    	if(data!=null && data!=undefined && data.inValidDetail != null && data.inValidDetail != undefined){
		    		var invdtl = data.inValidDetail;
		    		var messaget = invdtl.fieldName + ' ' + invdtl.invalidType.message;
		    		$('#UserInvalidFormatMessage').html(messaget);
		    		$("#UserARTSmessages,#UserInvalidFormatMessage").show();
					setTimeout("aart.clearMessages()", 10000);
					setTimeout(function(){ $("#UserARTSmessages").hide(); $('#UserInvalidFormatMessage').html('');},10000);									    	
		    	} else {
		    		/*var users = data.successUsers;
			    	var gridData = [];
			    	for (var i=0;i<users.length;i++) {
			    		var row = {"id": users[i].id,
			    				   "uniqueCommonIdentifier": users[i].educatorIdentifier,
			    				   "firstName": users[i].firstName,
			    				   "surName": users[i].surName,
			    				   "email": users[i].email,
			    				   "uniqueOrgNo": users[i].displayIdentifier,
			    				   "orgLevel": users[i].organizationTypeCode};
			    	  gridData.push(row); 
			     	}*/
			    	var uploadReport = "";
			    	if(data.uploadCompleted==true) {
			    		uploadReport += "Upload Completed Successfully. ";
			    	} else {
			    		uploadReport += "Upload Failed. ";
			    	}
			    	if(data.recordsCreatedCount != undefined && data.recordsRejectedCount !=  undefined && data.recordsUpdatedCount != undefined) {
			    		uploadReport += 
							" Rejected:" + data.recordsRejectedCount +
							" Updated:" + data.recordsUpdatedCount;
			    	}
			    	
			    	if(data.inValidRecords && data.recordsRejectedCount > 0) {
						uploadReport += '<a href="javascript:showPDTrainingResultsInvalidDetails()"> View Details </a>';
						var uploadDetails = '<table class="gridtable">';
						uploadDetails += '<tr> <th>Last Name</th> <th>First Name</th> <th>ID Number</th>';
//						uploadDetails += '<th>Identifier</th>';
						uploadDetails += '<th>Reasons For Not Valid </th> </tr>';	
						
						var invalidRecords = data.inValidRecords;
						for (var i=0;i<invalidRecords.length;i++) {	
							uploadDetails += '<tr>';
							var identifier = '';
							var reasons = '';
							//alert(invalidRecords[i]);
							var inValidDetails = invalidRecords[i].inValidDetails;
							var firstName="";
							var lastName="";
							var idNumber="";
							for (var j=0;j<inValidDetails.length;j++) {
								var fieldName = inValidDetails[j].fieldName;
								var formattedFieldValue = inValidDetails[j].formattedFieldValue;
								var invalidType = inValidDetails[j].invalidType;
								if(fieldName=='FirstName'|| fieldName=='First Name'){
									firstName = inValidDetails[j].fieldValue;
								}
								if(fieldName=='LastName'|| fieldName=='Last Name'){
									lastName = inValidDetails[j].fieldValue;
								}
								if(fieldName=='IDNumber'|| fieldName=='ID Number'){
									idNumber = inValidDetails[j].fieldValue;
								}
								var isRejected = inValidDetails[j].rejectRecord;
								if(!isRejected || ((!('ID Number' == fieldName)) && fieldName !="FirstName" && fieldName!='First Name' &&fieldName !="LastName" && fieldName!='Last Name')){
									identifier += inValidDetails[j].fieldValue + '</br>';
									if(fieldName =='Rt Complete Date'){
										reasons += 'The record is rejected because ' + fieldName + ' ' + inValidDetails[j].formattedFieldValue + '</br>';
									} else if (inValidDetails[j].actualFieldName == 'RT Complete Date') {
										reasons += 'The record is rejected because ' + inValidDetails[j].actualFieldName + ' ' + inValidDetails[j].reason + '</br>';
									} else {
										reasons += 'The record is rejected because ' + fieldName + inValidDetails[j].reason + '</br>';
									}
								}
							}
							if(invalidRecords[i].failedReason != null && invalidRecords[i].failedReason!=''){
								reasons += invalidRecords[i].failedReason;
							}
							uploadDetails += '<td>' + lastName +'</td>'
							uploadDetails += '<td>' + firstName +'</td>'
							uploadDetails += '<td>' + idNumber +'</td>'
//							uploadDetails += '<td>' + identifier +'</td>';
							uploadDetails += '<td>' + reasons +'</td>';
							
							uploadDetails += '</tr>';
						}							
						uploadDetails += '</table>';
				        var failedfileUrl = 'downloadFailedPDTrainingRecords.htm?uploadFileRecordId='+ uploadFileRecordId;
						uploadDetails += "<div><a href='"+ failedfileUrl +"'>Export Records That Failed to Update to a New CSV File</a></div>";
						$('#resultsuserUploadReportDetails').html(uploadDetails);
					}
			    	
			    	
			    	$('#resultsuserUploadReport').html(uploadReport);
			    	$('#resultsuserUploadReport').show();				    	
			    	/*$('#uploadUserGridTableId').jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');*/
		    	}
	    	}
	    	$('#uplodResults').removeAttr('disabled');
		} else {
			$('#resultsuserUploadReport').html('<span style="color:blue">Msg: Upload PD Training Results CSV file is in-progress.</span>').show();
			window.setTimeout(function(){monitorUploadPDResultsFile(uploadFileRecordId)}, 5000);
		}  
    });
}

/**
 * Initialize upload grid to display for user upload, this will be refreshed latet on data change.
 */
/*function initUploadGrid() {
	$("#uploadUserGridTableId").trigger('GridDestroy');
	var rGrid = $("#uploadUserGridTableId");
	
    getColumnIndexByName = function (grid, columnName) {
        var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
        for (i = 0; i < l; i++) {
            if (cm[i].name === columnName) {
                return i; // return the index
            }
        }
        return -1;
    };
   
    var cmForUploadUserGrid = [
		{name: 'id', index: 'id', hidden: true },
		{name: 'uniqueCommonIdentifier', index: 'uniqueCommonIdentifier', align: 'center', width: 175,  
			sortable : true, search : true, hidden: false, hidedlg : false	},
		{name: 'firstName', index: 'firstName', align: 'center', width: 175,  
			sortable : true, search : true, hidden: false, hidedlg : false	},
		{name: 'surName', index: 'surName', align: 'center', width: 175,  
			sortable : true, search : true, hidden: false, hidedlg : false	},
		{name: 'email', index: 'email', align: 'center', width: 175,  
			sortable : true, search : true, hidden: false, hidedlg : false	},
		{name: 'uniqueOrgNo', index: 'uniqueOrgNo', align: 'center', width: 175,  
				sortable : true, search : true, hidden: false, hidedlg : false	},
		{name: 'orgLevel', index: 'orgLevel', align: 'center', width: 175,  
					sortable : true, search : true, hidden: false, hidedlg : false	}
				
   ];

	rGrid.jqGrid({
		data: [],
		datatype: "local",
		loadonce: "true",
        colNames: [
                   "Id",
				   "Educator Identifier",
				   "First Name",
				   "Last Name",
				   "Email",
				   "Unique Org#",
				   "Org Level"
                  ],                     
                   
        colModel: cmForUploadUserGrid, 
 	    height : 'auto',
 	    shrinkToFit: false,
 	    gridview: true,
 	    width: 700,
        rowNum:10, 
        rowList:[10,20,30], 
        pager: '#uploadUserGridPager',  
        viewrecords: true, 
        page: 1,
		search: false,
		sortname: 'id',
	   	altclass: 'altrow',
	   	emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
	   	altRows : true,
	   	hoverrows : true,
	   	multiselect : false,
	   	toppager: false,
	   	onSelectRow: function (rowid,status,e) {
	   		
	   		var rowData = this.p.data[this.p._index[rowid]]
	   	    //openUploadUserPopup(rowData);
	   	},
	   	loadComplete: function (data) {
           },
           gridComplete: function() {
           	
           }
	});
	rGrid.trigger("reloadGrid");
}*/

function showPDTrainingResultsInvalidDetails() {
	$('#resultsuserUploadReportDetails').dialog('open');	
}

