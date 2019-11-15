
    function uploadTecInit(){
    	gUploadTECLoadOnce = true;
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
		
		$('#uploadTecOrgFilter').orgFilter({
			'containerClass': '',
			requiredLevels: [60]
		});
		
		$('#uploadTecOrgFilterForm').validate({
			ignore: "",
			rules: {
				tecFileData: {
		      		required: true,
		      		extension: "csv"
		    	}
		  	},
		  	 messages: {
		  		tecFileData: "Currently only CSV files are supported. Please select a file with the extension of '.csv'."
			     }
		});
		
		$('#tecUploadReportDetails').dialog({
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
		
		$('input[id=tecFileData]').on("change",function() {
			$('#tecFileDataInput').val($('#tecFileData')[0].files[0].name);
		});
		
		$('#uploadTecExits').on("click",function(event) {
			if($('#uploadTecExits').attr("disabled")=="disabled") {
				event.preventDefault();
			} else {
				uploadTecExits();
			}
		});
		showUploadTECGrid();
		
		filteringOrganizationSet($('#uploadTecOrgFilterForm'));
		
	};

	function browseUploadRosterCsv(event){
		 
		 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }
		 $('input[id=tecFileData]').click();
		}
	function uploadTecExits(continueOnWarning) {
		
		var date = new Date();
		var milliSec =date.getMilliseconds();
	   
		var fd = new FormData();
		var filedata = $('#tecFileData');
		var filelist = filedata[0].files;
		var file = filelist[0];
		var stateId = $('#uploadTecOrgFilter_state').val();
		var regionId = $('#uploadTecOrgFilter_region').val();
		var areaId = $('#uploadTecOrgFilter_area').val();
		var districtId = $('#uploadTecOrgFilter_district').val();
		var buildingId = $('#uploadTecOrgFilter_building').val();
		var schoolId = $('#uploadTecOrgFilter_school').val();
		if($('#uploadTecOrgFilterForm').valid()) {
			$('#uploadTecExits').attr("disabled","disabled");
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
			
			fd.append('categoryCode','TEC_RECORD_TYPE');
			fd.append('reportUpload',"false");
			
			fd.append('uploadFile',file);
			fd.append('date', date.getTime());
			fd.append('milliSec',milliSec);
		
			
			
			
			if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
			  	fd.append('continueOnWarning',continueOnWarning);
		  	}
			
			$('#tecUploadReport').html('');   			

			$.ajax({
				//url: 'uploadTEC.htm',
				url: 'uploadFileData.htm',
				data: fd,
				dataType: 'json',
				processData: false,
				contentType: false,
				cache: false,
				type: 'POST',
				beforeSend: function() {
					$("#loadingMessageTECupload").show();
				},
				complete: function() {
					$("#loadingMessageTECupload").hide();
				}
			}).done(function(data){
				loadUploadTECData();
				if(data.showWarning) {

                                	} else if(data.errorFound) {
		                } 
		    	else if(data.nopermit){
		        }
		    	else {
		    	   monitorTECUploadFile(data.uploadId);
		    	}
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
	
	function showTecInvalidDetails() {
		$('#tecUploadReportDetails').dialog('open');	
	}
	
	function monitorTECUploadFile(uploadFileRecordId){
		$.ajax({
	        //url: 'monitorFileUploadEnrollment.htm',
			url: 'monitorUploadFileStatus.htm',
	        type: 'GET',
	        cache: false,
	        data: {uploadFileRecordId: uploadFileRecordId}
		}).done(function(data) {
        	if (data.uploadFileStatus === 'FAILED' || data.errorFound){
        		$('#tecFileDataInput').val('');
        		$('#uploadTecExits').removeAttr('disabled');
        		loadUploadTECData();	        		
        	}else if(data.uploadFileStatus === 'COMPLETED'){
        		$('#tecFileDataInput').val('');
        		$('#uploadTecExits').removeAttr('disabled');
        		loadUploadTECData();	        	
			} else {
				if(data.uploadFileStatus === "IN_PROGRESS"){
					$('#tecUploadGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
				}
				window.setTimeout(function(){monitorTECUploadFile(uploadFileRecordId)}, 90000);
			}  
        });
	
	}

	function showUploadTECGrid(){ 
		  var uploadTECgrid = $('#tecUploadGridTableId');
		  $("#tecUploadDivtest div").show();
		  var colModel = [
		                  {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
		                  {label: 'Date', name: 'date', index: 'date', width:'100px', hidedlg: true, formatoptions: {newformat: 'm/d/Y'},
		                   formatter: function(cellValue, options, rowObject, action){
		                    return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
		                   }
		                  },
		                  {label: 'Time', name: 'time', index: 'time',width:'100px',  hidedlg: true, formatoptions: {newformat: 'h:i:s A'},
		                      formatter: function(cellValue, options, rowObject, action){
		                       return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
		                      }
		                     },
		                  {label: 'Status', name: 'status', index: 'status',width:'350px',  hidedlg: true, formatter: uploadTECSatusFormatter},
		                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'100px',  formatter:tecExtractUploadLinkFormatter},
		                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
		                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
		                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
		                 ];
		 $(uploadTECgrid).jqGrid({
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
		  pager: '#tecUploadGridPager',
		  sortname: 'id',
		  sortorder: 'DESC',
		  altclass: 'altrow',
		  altRows: true,
		  viewrecords : true,
		  //emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
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
		 loadUploadTECData();
		}
		function loadUploadTECData(){
			var categoryCode= "TEC_RECORD_TYPE";
		  $.ajax({
			  url: "getabUploadProgressStatus.htm?&categoryCode="+categoryCode,
		       type: "POST"
		    }).done(function(data) {
		           //  console.log(data);     
		          $('#tecUploadGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
		          
		       });

		}

		function uploadTECSatusFormatter(cellValue, options, rowObject){
			if( cellValue == "COMPLETED"){
				cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+rowObject.alertCount;
			}
			return cellValue;
		}
		
		function tecExtractUploadLinkFormatter(cellValue, options, rowObject){
			var status = rowObject.statusCheck;
			if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
				var dataOrganizationName=rowObject.organizationName;
				var currDate = new Date();
				var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
				var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
				+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
				var file = rowObject.fileName.split(".",1);
				var fileName = file + "_TECUpload_Errors_"+dateStringForFile+".csv";
				return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+encodeURIComponent(fileName)+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
			}
			else
				return '<a> </a>'
		}
	

$(function(){
	
	$("#TEC_uploadTemplatedownloadquickHelpPopup").hide();
	$("#TEC_uploadTemplatedownloadquickHelpPopupClose").on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#TEC_uploadTemplatedownloadquickHelpPopup").hide();
	});
	$("#TEC_TemplatedownloadquickHelpInTestTab").on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#TEC_uploadTemplatedownloadquickHelpPopup").show();
	});
	
	$('#TEC_TemplatedownloadquickHelpInTestTab').on('mouseover',function(){
		$(".QuickHelpHint").show();
	});
	$('#TEC_TemplatedownloadquickHelpInTestTab').on('mouseout',function(){
		$(".QuickHelpHint").hide();
	});
	$('.QuickHelpHint').on('mouseover',function(){
		$(".QuickHelpHint").show();
	});
	$('.QuickHelpHint').on('mouseout',function(){
		$(".QuickHelpHint").hide();
	});
	$('#TECUpload_Templatelink').on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#TEC_uploadTemplatedownloadquickHelpPopup").hide();
	});
	
	
});		