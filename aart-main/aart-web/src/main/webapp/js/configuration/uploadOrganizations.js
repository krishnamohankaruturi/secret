var uploadOrgFile = $('#orgFileData');
function uploadOrganizationsInit(){
	gUploadOrganizationLoadOnce = true;
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
	
	$('#uploadOrgOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});
    
	$('#orgUploadReportDetails').dialog({
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
	
	$('#uploadOrgFilterForm').validate({
		ignore: "",
		rules: {
			orgFileData: {
	      		required: true,
	      		extension: "csv"
	    	},
			uploadOrgOrgFilter_state: 'required'
	  	}
	});
	
	$('#uplodOrg').on("click",function (event) {
    	$("#message").html('');
		/*$('#orgUploadGrid').jqGrid('clearGridData');*/
    	if($('#uplodUser').attr("disabled")=="disabled") {
			event.preventDefault();
    	} else {
    		$('#orgUploadReport').html('');
        	var data = $('#orgFileData').data('filedata');
    		if($('#uploadOrgFilterForm').valid()) {
    			$('#uplodUser').attr("disabled","disabled");
            	data.submit();
    		}  else {
    			$('#message').html('<span class="error_message ui-state-error">Choose all required fields.</span>').show();
    		}
    	}
    });
		
	//buildUploadOrgGrid();
	//	getOrgUploadProgressTable();
	showUploadOrgGrid();
	
	filteringOrganizationSet($('#uploadOrgFilterForm'));
			 
};
       	
$(function () {
	
   
    $('#orgFileData').fileupload({
        dataType: 'json',
        dropZone: null,
        replaceFileInput: false,
        //url: 'uploadOrganization.htm',
        url: 'uploadFileData.htm',//US16352: Modified
        submit: function(e, data){
        	var date = new Date();
        	var milliSec =date.getMilliseconds();
        	data.formData = {
        		'stateId':$('#uploadOrgOrgFilter_state').val(),
				//'organizationId':$('#uploadOrgOrgFilter').orgFilter('value'),
        		'selectedOrgId':$('#uploadOrgOrgFilter').orgFilter('value'),
				'categoryCode':'ORG_RECORD_TYPE',
				'reportUpload':false,
				'date':date.getTime(),
				'milliSec':milliSec
			};
        },
        add: function (e, data) {
            //data.context = $('#uplodOrg');
            $(this).data('filedata', data);
        },
        done: function (e, d) {
        	var data = d.result;
        	loadOrgUploadData();
        	if(data.invalidParentOrg != null) {
        		$('#uplodUser').removeAttr('disabled');
			//	$("#orgUploadReport").html('<span class="error_message ui-state-error">' + data.invalidParentOrg + '</span>').show();
			//	$('#uplodUser').removeAttr('disabled');
			} else if(data.errorFound) {
				$('#uplodUser').removeAttr('disabled');
		//		$("#orgUploadReport").html('<span class="error_message ui-state-error">Error Msg: Failed to upload Organization file.</span>').show();
		//		$('#uplodUser').removeAttr('disabled');
			} else {
				//$('#orgUploadReport').html('<span style="color:blue">Msg: Upload Organization file is in-progress.</span>').show();
				//$('#orgUploadReport').html('<span>Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
		    	//monitorUploadOrganizationFile(data.uploadFileRecordId);
				 
				monitorUploadOrganizationFile(data.uploadId);
			}
        }
    });
});

function monitorUploadOrganizationFile(uploadFileRecordId){
	$.ajax({
       // url: 'monitorUploadOrganizationFile.htm',
		url: 'monitorUploadFileStatus.htm', //US16252: Modified 
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function(data) {
    	if(data.uploadFileStatus === 'FAILED'){
    		loadOrgUploadData();
    		clearOrganizationUploadFile();
    		if(data.errorFound) {
				$('#orgUploadReport').html('');
		//		$("#message").html('<span class="error_message ui-state-error">Error Msg: Failed to upload Organization file.</span>').show();
			}
    		$('#uplodUser').removeAttr('disabled');
    		
    	} else if(data.uploadFileStatus === 'COMPLETED'){
    		 loadOrgUploadData();
    		 clearOrganizationUploadFile();
            //d.context.text('Upload finished.');
            if(data.invalidParentOrg != null) {
            //	$('#orgUploadReport').html('');
			//	$("#message").html('<span class="error_message ui-state-error">' + data.invalidParentOrg + '</span>').show();
			} else if(data.errorFound) {
			//	$('#orgUploadReport').html('');
			//	$("#message").html('<span class="error_message ui-state-error">Error Msg: Failed to upload Organization file.</span>').show();
			} else {
			//	var uploadReport = "";
			/*	if(data.uploadCompleted == true) {
			//		uploadReport += "Upload Status: Complete <br/>";
				} else {
			//		uploadReport += "Upload Status: Failed. <br/>";
				}
				//US16252: Modified
		    	if(data.uploadedDetails != undefined && data.uploadedDetails.successCount != undefined && data.uploadedDetails.failedCount !=  undefined) {
		    		uploadReport += "Records Created:" + data.uploadedDetails.successCount +
						" Rejected:" + data.uploadedDetails.failedCount +'<br/>'; 
						//" Updated:" + data.recordsUpdatedCount; //US16252: Modified
		    	}
		    	//US16252: Modified
		    	if( data.uploadedDetails.failedCount > 0) {
		    		var currDate = new Date();
		    		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
		    		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
		    		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
		    		var fileName = data.organizationName +"OrganizationUpload_Errors_"+dateStringForFile+".csv";
		    		
					uploadReport += '<a href="javascript:showOrgInvalidDetails()" style="">View Details </a>';
					uploadReport += '<a href="getUploadErrorFile.htm?uploadedId='+data.uploadedDetails.id + '&fileName='+fileName+'" style="margin-left:20px"> Download Details </a>';
					var uploadDetails = '<table class="gridtable">';
					uploadDetails += '<tr> <th>Identifier</th>';
					uploadDetails += '<th>Reasons For Not Valid </th> </tr>';	
					//US16252: Modified
					var invalidRecords = data.uploadErrors;
					for (var i=0;i<invalidRecords.length;i++) {	
						uploadDetails += '<tr>';
						var identifier = '';
						var reasons = '';
						var fieldName = invalidRecords[i].fieldName;
						var formattedFieldValue = invalidRecords[i].reason;
						
						identifier = "Line Number : " + invalidRecords[i].line + ": Field : " + fieldName ;//+ '</br>';
						reasons  = 'The record is rejected because ' + formattedFieldValue ; // +' with '+formattedFieldValue+' is '+ invalidType.message + '</br>';
						uploadDetails += '<td>' + identifier +'</td>';
						uploadDetails += '<td>' + reasons +'</td>';
						uploadDetails += '</tr>';
					}
					
					uploadDetails += '</table>';
					
					$('#orgUploadReportDetails').html(uploadDetails);
				}
				*/
				/*var organizations = data.createdOrgs;
				var gridData = [];
				if(organizations != null) {
					for (var i=0;i<organizations.length;i++) {
						var row = {"id": organizations[i].id,
								   "displayIdentifier": organizations[i].displayIdentifier,
								   "organizationName": organizations[i].organizationName,
								   "typeCode": organizations[i].typeCode,
								   "parentOrgDisplayName": organizations[i].parentOrgDisplayName};
					  gridData.push(row); 
				 	}
				}*/
				//$('#orgUploadReport').html(uploadReport);
			//	$('#orgUploadReport').show();
				/*$('#orgUploadGrid').jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid',[{page:1}]);*/
			}
            $('#uplodUser').removeAttr('disabled');
			$('.loading').hide();
			//$('#uplodUser').removeAttr('disabled');
		} else {
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadOrgGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
			//$('#orgUploadReport').html('<span style="color:blue">Msg: Upload Organization file is in-progress.</span>').show();
			//$('#orgUploadReport').html('<span style="color:blue">Msg:PLEASE WAITâ€¦ FILE UPLOAD IN PROGRESS</span>').show();
		//	$('#orgUploadReport').html('<span>Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
			window.setTimeout(function(){monitorUploadOrganizationFile(uploadFileRecordId)}, 90000);
		}  
    });
}

function validateOrgUpload() {
	var validData = false;
	var stateId = $('#uploadOrgOrgFilter_state').val();
	var districtId = $('#uploadOrgOrgFilter_district').val();
	var schoolId = $('#uploadOrgOrgFilter_school').val();
	var areaId = $('#uploadOrgOrgFilter_area').val();
	var regionId = $('#uploadOrgOrgFilter_region').val();
	var buildingId = $('#uploadOrgOrgFilter_building').val();
	var filedata = $('#orgFileData');
	
	if(stateId != 0 || districtId != 0 || schoolId != 0 ||
			areaId != 0 || regionId != 0 || buildingId != 0) {
		validData = true;
	}

	if(filedata[0].files.length > 0) {
		validData = true;
	} else {
		validData = false;
	}
	
	return validData;
	
}

function showOrgInvalidDetails() {
	$('#orgUploadReportDetails').dialog('open');	

}


function showUploadOrgGrid(){ 
  var uploadOrganizationgrid = $('#uploadOrgGridTableId');
  $("#uploadOrganizationDiv div").show();
  

 var colModel = [
           {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
           {label: 'Date', name: 'date', index: 'date', hidedlg: true,width:'110px',  formatoptions: {newformat: 'm/d/Y'},
            formatter: function(cellValue, options, rowObject, action){
             return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
            }
           },
           {label: 'Time', name: 'time', index: 'time', hidedlg: true, width:'110px', formatoptions: {newformat: 'h:i:s A'},
               formatter: function(cellValue, options, rowObject, action){
                return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
               }
              },
           {label: 'Status', name: 'status', index: 'status',width:'390px',  hidedlg: true,formatter: organizationUploadSatusFormatter},
           {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'130px',  formatter:extractOrgLinkFormatter},
           {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
           {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
           {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
          ];

 $(uploadOrganizationgrid).jqGrid({
  datatype: 'local',
  width: '740px',
  height: 'auto',
  filterstatesave: true,
  pagestatesave: true,
  colModel: colModel,
  filterToolbar: false,
  rowNum: 10,
  rowList: [5, 10, 20, 30, 40, 60, 90],
  columnChooser: false, 
     multiselect: false,
  footerrow : true,
  pager: '#uploadOrgGridPager',
  sortname: 'id',
  sortorder: 'DESC',
  altclass: 'altrow',
  altRows: true,
  emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
  jsonReader: {
         page: function (obj) {
             return obj.page !== undefined ? obj.page : "0";
         },
         repeatitems:false,
      root: function(obj) { 
       return obj.rows;
      } 
     }
  });
 loadOrgUploadData();
}
function loadOrgUploadData(){
	var categoryCode= "ORG_RECORD_TYPE";
  $.ajax({
	  url: "getabUploadProgressStatus.htm?&categoryCode="+categoryCode,
       type: "POST"
    }).done(function(data) {
        //  console.log(data);     
        $('#uploadOrgGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
        
     });
}

function organizationUploadSatusFormatter(cellValue, options, rowObject){
	if( cellValue == "COMPLETED"){
		cellValue = 'Completed: Records Created: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+rowObject.alertCount;
	}
	return cellValue;
}

function extractOrgLinkFormatter(cellValue, options, rowObject){
	var status = rowObject.statusCheck;
	if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
		var dataOrganizationName=rowObject.organizationName;
		var currDate = new Date();
		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
		var file = rowObject.fileName.split(".",1);
		var fileName = file +"_OrganizationUpload_Errors_"+dateStringForFile+".csv";
		return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
	}
	else
		return '<a> </a>';
}
function browseUploadRosterCsv(event){
	 
	 if(event.type=='keypress'){
	  if(event.which !=13){
	   return false;
	  }
	 }
	 $('input[id=orgFileData]').click();
	}
function clearOrganizationUploadFile(){
	$('#orgFileData').val("");
	uploadOrgFile.replaceWith( uploadOrgFile = uploadOrgFile.clone( true ) );
	$('#orgFileDataInput').val("");
}