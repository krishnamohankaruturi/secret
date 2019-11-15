var invalidGridData=[];
var uploadPNPCategoryCode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE';   
var uploadPNPFile = $('#pnpFileData');
function uploadPNPInit(){
	gUploadPNPLoadOnce = true;
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
	
	$('#uploadPNPOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [60]
	});
	
	$('#uploadPNPOrgFilterForm').validate({
		ignore: "",
		rules: {
			pnpFileData: {
	      		required: true,
	      		extension: "csv,xlsx"
	    	}
	  	},
	  	 messages: {
	  		pnpFileData: "Currently only CSV and Excel files are supported. Please select a file with the extension of '.csv' or '.xlsx'."
	     }
	});
	
	$('#pnpUploadReportDetails').dialog({
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
	
	$('input[id=pnpFileData]').on("change",function() {
		$('#pnpFileDataInput').val($('#pnpFileData')[0].files[0].name);
	});
	
	$('#uploadPNPButton').on('click', function(){
		if($('#uploadPNPButton').attr("disabled")=="disabled") {
			event.preventDefault();
		} else {
			uploadPNP();
		}
	});
	
	showPnpUploadGrid();
	
	filteringOrganizationSet($('#uploadPNPOrgFilterForm'));
	
	$("#pnpQuickHelp").on('mouseover', function(){
		$('#pnpQuickHelpContent').show();
	}).on('mouseout', function(){
		$('#pnpQuickHelpContent').hide();
	});
	
	$('#pnpQuickHelpContent').on('mouseover', function(){
		$(this).show();
	}).on('mouseout', function(){
		$(this).hide();
	});
};

function browseUploadPNPCsv(event){
	 if (event.type == 'keypress') {
		if (event.which != 13) {
			return false;
		}
	}
	$('input[id=pnpFileData]').click();
}

function uploadPNP() {
	var fd = new FormData();
	var date = new Date();
	var milliSec =date.getMilliseconds();
	var filedata = $('#pnpFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	var stateId = $('#uploadPNPOrgFilter_state').val();
	var regionId = $('#uploadPNPOrgFilter_region').val();
	var areaId = $('#uploadPNPOrgFilter_area').val();
	var districtId = $('#uploadPNPOrgFilter_district').val();
	var buildingId = $('#uploadPNPOrgFilter_building').val();
	var schoolId = $('#uploadPNPOrgFilter_school').val();
	if($('#uploadPNPOrgFilterForm').valid()) {
		$('#uploadPNPButton').attr("disabled","disabled");
		$('body, html').animate({scrollTop:0}, 'slow');
		fd.append('stateId',stateId);
		
		if( regionId != null && regionId != undefined && regionId.trim().length > 0 )
			fd.append('regionId',regionId);
		if( areaId != null && areaId != undefined && areaId.trim().length > 0 )
			fd.append('areaId',areaId);
		if( districtId != null && districtId != undefined && districtId.trim().length > 0)
			fd.append('districtId',districtId);
		if( buildingId != null && buildingId != undefined && buildingId.trim().length > 0 )
			fd.append('buildingId',buildingId);
		if( schoolId != null && schoolId != undefined && schoolId.trim().length > 0 )
			fd.append('schoolId',schoolId);
		
		var selectedOrgId = stateId;
		if( schoolId != null && schoolId != undefined && schoolId.trim().length > 0 )
			selectedOrgId = schoolId;
		else if( buildingId != null && buildingId != undefined && buildingId.trim().length > 0 )
			selectedOrgId = buildingId;
		else if( districtId != null && districtId != undefined && districtId.trim().length > 0)
			selectedOrgId = districtId;
		else if( areaId != null && areaId != undefined && areaId.trim().length > 0 )
			selectedOrgId = areaId;
		else if( regionId != null && regionId != undefined && regionId.trim().length > 0 )
			selectedOrgId = regionId;
		
		if(selectedOrgId != null)
			fd.append('selectedOrgId',selectedOrgId);
			
		fd.append('categoryCode',uploadPNPCategoryCode);
		fd.append('reportUpload',"false");
		fd.append('date', date.getTime());
		fd.append('milliSec',milliSec);
		fd.append('uploadFile',file);
		$('#pnpUploadReport').html('');

		$.ajax({
			url: 'uploadFileData.htm',
			data: fd,
			dataType: 'json',
			processData: false,
			contentType: false,
			cache: false,
			type: 'POST',
			complete: function(){
				$('#uploadPNPButton').removeAttr('disabled');
				clearPnpUploadFile();
			}
		}).done(function(data){
			loadPnpUploadData(uploadPNPCategoryCode);
			monitorUploadPNPFile(data.uploadId);
		}).fail(function(jqXHR,textStatus,errorThrown) {
			var e=errorThrown;
		});
	}  else {
		$(".studentARTSmessages").show();
		$('.requiredMessage').show();
		setTimeout("aart.clearMessages()", 3000);
		setTimeout(function(){ $(".studentARTSmessages").hide(); },3000);	
	}
}

function monitorUploadPNPFile(uploadFileRecordId){
	$.ajax({
		url: 'monitorUploadFileStatus.htm',
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function(data) {
    	if(data.uploadFileStatus === 'COMPLETED' || data.uploadFileStatus === 'FAILED' || data.errorFound){
  		  clearPnpUploadFile();
  		  loadPnpUploadData(uploadPNPCategoryCode);
  		  $('#uploadPNPButton').removeAttr('disabled');
		} else {
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadPNPGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
			window.setTimeout(function(){monitorUploadPNPFile(uploadFileRecordId)}, 10000);
		}  
  });
	
	
}

function showPnpUploadGrid(){ 

	  var uploadPNPgrid = $('#uploadPNPGridTableId');
	  $("#uploadPNPDivGrid div").show();
	  

	  var colModel = [
	                  {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
	                  {label: 'Date', name: 'date', index: 'date', width:'110px', hidedlg: true, formatoptions: {newformat: 'm/d/Y'},
	                   formatter: function(cellValue, options, rowObject, action){
	                    return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                   }
	                  },
	                  {label: 'Time', name: 'time', index: 'time',width:'110px',  hidedlg: true, formatoptions: {newformat: 'h:i:s A'},
	                      formatter: function(cellValue, options, rowObject, action){
	                       return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                      }
	                     },
	                  {label: 'Status', name: 'status', index: 'status',width:'400px',  hidedlg: true,formatter: pnpUploadSatusFormatter},
	                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'120px',  formatter:extractPnpUploadLinkFormatter},
	                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
	                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
	                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
	                 ];

	 $(uploadPNPgrid).jqGrid({
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
	  pager: '#uploadPNPGridPager',
	  sortname: 'id',
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
	 loadPnpUploadData(uploadPNPCategoryCode);
	}
	function loadPnpUploadData(){
	  $.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+uploadPNPCategoryCode,
	       type: "POST"
	    }).done(function(data) {
            $('#uploadPNPGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	          
	       });

	}
	
	function pnpUploadSatusFormatter(cellValue, options, rowObject){
		if( cellValue == "COMPLETED"){
			cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount  + " Alerts: "+rowObject.alertCount;
		}
		return cellValue;
	}

	function extractPnpUploadLinkFormatter(cellValue, options, rowObject){
		var status = rowObject.statusCheck;
		if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
			var dataOrganizationName=rowObject.organizationName;
			var currDate = new Date();
			var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
			var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
			+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
			var file = rowObject.fileName.split(".",1);
			var fileName = file +"_PNPUpload_Errors_"+dateStringForFile+".csv";
			return '<a title="Click to download extract" href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"  ><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
		}
		else
			return '<a> </a>';
	}

function clearPnpUploadFile(){
	$('#pnpFileData').val('');
	//$('#pnpFileData').replaceWith( uploadEnrollmentFile = uploadEnrollmentFile.clone( true ) );
	$('#pnpFileDataInput').val('');
}