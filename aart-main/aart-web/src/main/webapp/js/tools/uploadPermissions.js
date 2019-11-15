$(function() {
	permissionsUpload_Initmethod();
});
var  uploadPermissionFile= $('#PermissionFileData');
function permissionsUpload_Initmethod() {
	upload_Init_Permission_Tab();
	showPermissionUploadGrid();
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
	
	$('#permissionUploadReportDetails').dialog({
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
	/*initUploadGrid();*/	
	
	/**
	 * This method is called when user clicks on upload button to upload permissions.
	 */
	$('#uploadPermission').on("click",function(event) {
		if($('#uploadPermission').attr("disabled")=="disabled") {
			event.preventDefault();
    	} else {
    		uploadPermission();
    	}	
		$('#permissionrequiredMessage').html('Correct below error(s).');
	});
	
	$('input[id=permissionFileData]').on("change",function() {
		$('#permissionFileDataInput').val($('#permissionFileData')[0].files[0].name);
	});
	
	function uploadPermission(continueOnWarning) {
		aart.clearMessages();
		var date = new Date();
		var milliSec =date.getMilliseconds();

		$("#permissionARTSmessages").hide();
		$('#permissionUploadReport').html('').hide();
		if($('#uploadPermissionFilterForm').valid()) {
			$('#uploadPermission').attr("disabled","disabled");
			$('#load_uploadPermissionGridTableId').show();
		
			
			var fd = new FormData();
			var filedata = $('#permissionFileData');
			var filelist = filedata[0].files;
			var file = filelist[0];
		
			
			fd.append('categoryCode','PERMISSION_RECORD_TYPE');
			fd.append('reportUpload',false);
			fd.append('uploadFile',file);
			fd.append('date', date.getTime());
			fd.append('milliSec',milliSec);
			  if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
				  	fd.append('continueOnWarning',continueOnWarning);
			  	}
			  $.ajax({
				
				url: 'uploadFileData.htm',
			    data: fd,
			    dataType: 'json',
			    processData: false,
			    contentType: false,
			    cache: false,
			    type: 'POST'
			  }).done(function(data){
			    	loadpermissionUploadData();
			    	if (data.errorMessage != null){
						$('#reportPermissionUploadErrorMessage')
						.html(data.errorMessage)
						.dialog('open');
						
						$('#uploadPermission').removeAttr('disabled');
								} else {
				    	monitorUploadPermissionFile(data.uploadId);
			    	}
			    })
			  .fail(function(jqXHR,textStatus,errorThrown) {
			    	$('#load_uploadPermissionGridTableId').hide();
			    	var e=errorThrown;
			    });
		} else{
			$("#permissionARTSmessages,#permissionrequiredMessage").show();
			setTimeout("aart.clearMessages()", 10000);
			setTimeout(function(){ $("#permissionARTSmessages").hide(); },10000);
		}	
	}
	
	$('#reportPermissionUploadErrorMessage').dialog({
		resizable: false,
		height: 200,
		width: 400,
		modal: true,
		autoOpen:false,
		title:'ERROR',
		buttons: {
			Ok: function() {
				clearpermissionUploadFile();
				$(this).dialog('close');
		    },	    
		}
	});
	
	
}

function browseuploadPermissionCsv(event){
	
	if(event.type=='keypress'){
		if(event.which !=13){
			return false;
		}
	}
$('input[id=permissionFileData]').on();
}
function clearpermissionUploadFile(){
	$('#permissionFileData').val("");
	uploadPermissionFile.replaceWith( uploadPermissionFile = uploadPermissionFile.clone( true ) );
	$('#permissionFileDataInput').val("");
}
function monitorUploadPermissionFile(uploadFileRecordId){
	$.ajax({
        
		url: 'monitorUploadFileStatus.htm', 
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function(data) {
    	if(data.uploadFileStatus === 'FAILED' || data.errorFound || data.uploadFileStatus === 'COMPLETED' ){
    		clearpermissionUploadFile();
    		loadpermissionUploadData();
    		$('#uploadPermission').removeAttr('disabled');
    		
    	}else {
			
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadPermissionGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
				window.setTimeout(function(){monitorUploadPermissionFile(uploadFileRecordId)}, 5000);
		}  
    });
}



function showpermissionInvalidDetails() {
	$('#permissionUploadReportDetails').dialog('open');	
}

function showPermissionUploadGrid(){ 

	  var uploadPermissiongrid = $('#uploadPermissionGridTableId');
	  
	  gridWidthForVO = 711;
	  var cellWidthForVO = (gridWidthForVO-350)/3;
	  
	  var colModel = [
	                  {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
	                  {label: 'Date', name: 'date', index: 'date', width:cellWidthForVO, hidedlg: true, formatoptions: {newformat: 'm/d/Y'},
	                   formatter: function(cellValue, options, rowObject, action){
	                    return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                   }
	                  },
	                  {label: 'Time', name: 'time', index: 'time',width:cellWidthForVO,  hidedlg: true, formatoptions: {newformat: 'h:i:s A'},
	                      formatter: function(cellValue, options, rowObject, action){
	                       return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                      }
	                     },
	                  {label: 'Status', name: 'status', index: 'status',width:'350px',  hidedlg: true,  formatter: permissionUploadSatusFormatter},
	                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:cellWidthForVO,  formatter:extractpermissionUploadErrorLinkFormatter},
	                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
	                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
	                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
	                 ];

	 $(uploadPermissiongrid).jqGrid({
	  datatype: 'local',
	  width: gridWidthForVO,
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
	  pager: '#uploadPermissionGridPager',
	  sortname: 'id',
	  viewrecords : true,
	  sortorder: 'DESC',
	  altclass: 'altrow',
	  altRows: true,
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
	 loadpermissionUploadData();
	}
	function loadpermissionUploadData(){
		var categoryCode= "PERMISSION_RECORD_TYPE";
	  $.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+categoryCode,
	       type: "POST"
	    }).done(function(data) {
	           
	          $('#uploadPermissionGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	          
	       });

	}
	
	function permissionUploadSatusFormatter(cellValue, options, rowObject){
		if( cellValue == "COMPLETED"){
			cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+rowObject.alertCount;
		}
		return cellValue;
	} 

	function extractpermissionUploadErrorLinkFormatter(cellValue, options, rowObject){
		var status = rowObject.statusCheck;
		if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
			var dataOrganizationName=rowObject.organizationName;
			var currDate = new Date();
			var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
			var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
			+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
			var file = rowObject.fileName.split(".",1);
			var fileName = file +"_PermissionsUpload_Errors_"+dateStringForFile+".csv";
			return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
		}
		else
			return '<a> </a>';
	}
	
	function upload_Init_Permission_Tab(){
		$('#uploadPermissionFilterForm').validate({
			ignore: "",
			rules: {
				permissionFileData: {
		      		required: true,
		      		extension: "csv"
		    	}
		  	}
		});
			
	}
	