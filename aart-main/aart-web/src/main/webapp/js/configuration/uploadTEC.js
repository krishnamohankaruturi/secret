   var uploadTECFile = $('#exitsFileData');
    function uploadExitsInit(){
    	uploadTECFile= $('#exitsFileData'); 
    	gUploadExitsLoadOnce = true;
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
		
		$('#uploadExitsOrgFilter').orgFilter({
			'containerClass': '',
			requiredLevels: [60]
		});
		
		$('#uploadExitsOrgFilterForm').validate({
			ignore: "",
			rules: {
				exitsFileData: {
		      		required: true,
		      		extension: "csv"
		    	}
		  	},
		  	 messages: {
		  		exitsFileData: "Currently only CSV files are supported. Please select a file with the extension of '.csv'."
			     }
		});
		
		$('#exitsUploadReportDetails').dialog({
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
		
	//	buildUploadTECGrid();
		
		$('input[id=exitsFileData]').on("change",function() {
			$('#exitsFileDataInput').val($('#exitsFileData')[0].files[0].name);
		});
		
		$('#uploadExits').on("click",function(event) {
			if($('#uploadExits').attr("disabled")=="disabled") {
				event.preventDefault();
			} else {
				uploadExits();
			}
		});
		showTECUploadGrid();
		
		filteringOrganizationSet($('#uploadExitsOrgFilterForm'));
		
	};	
/*
	function buildUploadTECGrid() {

		$("#exitsGrid").trigger('GridDestroy');
		var rGrid = $("#exitsGrid");

	    getColumnIndexByName = function (grid, columnName) {
	        var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
	        for (i = 0; i < l; i++) {
	            if (cm[i].name === columnName) {
	                return i; // return the index
	            }
	        }
	        return -1;
	    };

	    var colNamesForExitsGrid = [
			"Id",
			"Date",
			"Time",
			"Status",
			"File",
		];
	    
	    var cmForExitsGrid = [
		   	{name: 'id', index: 'id', hidden: true }, 	
			{name: 'uploadDate', index: 'uploadDate', align: 'center', width: 150, height: 40,  
				sortable : true, search : true, hidden: false, hidedlg : false, formatter: dateFormatter},
			{name: 'uploadTime', index: 'uploadTime', align: 'center', width: 150, height: 40,  
				sortable : true, search : true, hidden: false, hidedlg : false, formatter:timeFormatter	},
			{name: 'uploadStatus', index: 'uploadStatus', align: 'center', width: 250, height: 40,  
				sortable : true, search : true, hidden: false, hidedlg : false	},	
			{name: 'uploadFile', index: 'uploadFile', align: 'center', width: 150, height: 40,  
					sortable : true, search : true, hidden: false, hidedlg : false	}																				
	   ];
		
		rGrid.jqGrid({
			data: [],
			datatype: "local",
			loadonce: "true",
	        colNames: colNamesForExitsGrid,                       
	        colModel: cmForExitsGrid, 
	 	    height : 'auto',
	 	    shrinkToFit: false,
	 	    gridview: true,
	 	    width: 700,
	        rowNum:10, 
	        rowList:[10,20,30], 
	        pager: '#exitsGridPager',  
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
		   	    openViewRosterPopup(rowData);
		   	},
		   	loadComplete: function (data) {
	           },
	           gridComplete: function() {
	           	
	           }
		});
    

		rGrid.trigger("reloadGrid");
	
	}
	
	function dateFormatter(cellval, opts) {
        if(cellval != 'Not Available'){
     	var date = new Date(cellval);
	         opts = $.extend({}, $.jgrid.formatter.date, opts);
	         return $.fmatter.util.DateFormat("", date, 'm/d/Y', opts);
        }else{
     	   return cellval;
        }
    	 
  	};
  
  	function timeFormatter(cellval, opts) {
        if(cellval != 'Not Available'){
     	var date = new Date(cellval);
	         opts = $.extend({}, $.jgrid.formatter.date, opts);
	         return $.fmatter.util.DateFormat("", date, 'g:i:s A', opts);
        }else{
     	   return cellval;
        }
    	 
  	};
	*/
	function browseUploadRosterCsv(event){
		 
		 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }
		 $('input[id=exitsFileData]').click();
		}
	
	function uploadExits(continueOnWarning) {
		
		var date = new Date();
		var milliSec =date.getMilliseconds();
	   
		var fd = new FormData();
		var filedata = $('#exitsFileData');
		var filelist = filedata[0].files;
		var file = filelist[0];
		var stateId = $('#uploadExitsOrgFilter_state').val();
		var regionId = $('#uploadExitsOrgFilter_region').val();
		var areaId = $('#uploadExitsOrgFilter_area').val();
		var districtId = $('#uploadExitsOrgFilter_district').val();
		var buildingId = $('#uploadExitsOrgFilter_building').val();
		var schoolId = $('#uploadExitsOrgFilter_school').val();
		if($('#uploadExitsOrgFilterForm').valid()) {
			$('#uploadExits').attr("disabled","disabled");
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
			
			$('#exitsUploadReport').html('');
   			/*$('#exitsGrid').jqGrid('clearGridData');*/

			$.ajax({
				//url: 'uploadTEC.htm',
				url: 'uploadFileData.htm',//US16352: Modified
				data: fd,
				dataType: 'json',
				processData: false,
				contentType: false,
				cache: false,
				type: 'POST',
				beforeSend: function() {
					$("#loadingMessageTEC").show();
				},
				complete: function() {
					$("#loadingMessageTEC").hide();
				}
			}).done(function(data){
				loadTECUploadData();
				if(data.showWarning) {
		/*			$('#exitsUploadReport').html('');
		    		$('<div></div>').html(data.warningMessage).dialog({
		    		      height: 190,
		    		      width: 460,
		    		      modal: true,
		    		      closeOnEscape: false,
		    		      buttons: {
		    		        "Yes": function() {
		    		        	uploadExits(true);
		    		        	$(this).dialog("close");
		    		        },
		    		      	"Cancel": function() {
		    		          	$(this).dialog( "close" );
		    		       	}
		    		      }
		    		});
		    		$('#uploadExits').removeAttr('disabled');
		    */	} else if(data.errorFound) {
		    /*		$('#exitsUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to upload TEC file.</span>').show();
		    		$('#uploadExits').removeAttr('disabled');
		    */	} 
		    	else if(data.nopermit){
		    /*		$('#exitsUploadReport').html('<span class="error_message ui-state-error">Error Msg: Cannot upload TEC file for Kansas state.</span>').show();
		    		$('#uploadExits').removeAttr('disabled');
		    */	}
		    	else {
		    		//$('#exitsUploadReport').html('<span style="color:blue">Msg: Upload TEC file is in-progress.</span>').show();

		    	//	$('#exitsUploadReport').html('<span style="">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
					//monitorUploadTECFile(data.uploadFileRecordId);
					monitorUploadTECFile(data.uploadId);
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
	
	function showExitsInvalidDetails() {
		$('#exitsUploadReportDetails').dialog('open');	
	}
	
	function monitorUploadTECFile(uploadFileRecordId){
		$.ajax({
	        //url: 'monitorFileUploadEnrollment.htm',
			url: 'monitorUploadFileStatus.htm', //US16252: Modified
	        type: 'GET',
	        cache: false,
	        data: {uploadFileRecordId: uploadFileRecordId}
		}).done(function(data) {
        	if (data.uploadFileStatus === 'FAILED' || data.errorFound){
        		$('#exitsFileData').val("");
        		uploadTECFile.replaceWith( uploadTECFile = uploadTECFile.clone( true ) );
        		$('#exitsFileDataInput').val("");
        		$('#uploadExits').removeAttr('disabled');
        		loadTECUploadData();
        		/*	var msg = 'Error Msg: Failed to upload Student TEC file. ';
        		
        		if( data.uploadedDetails.failedCount == 0 && data.uploadedDetails.successCount == 0 ){
		    		if( data.uploadErrors != null && data.uploadErrors && data.uploadErrors.length == 1 ){
		    			var reason = data.uploadErrors[0].reason;
		    			msg += reason;
		    		}
        		}	
        		$('#exitsUploadReport').html('<span class="error">' + msg + '</span>').show();
        		$('#uploadExits').removeAttr('disabled');
        		/*if(data.errorFound) {
		    		$('#exitsUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to upload TEC file.</span>').show();
		    	}
		    	*/
        	}else if(data.uploadFileStatus === 'COMPLETED'){
        		$('#exitsFileData').val("");
        		uploadTECFile.replaceWith( uploadTECFile = uploadTECFile.clone( true ) );
        		$('#exitsFileDataInput').val("");
        		$('#uploadExits').removeAttr('disabled');
        		loadTECUploadData();
        	/*	if(data.showWarning) {
					$('#exitsUploadReport').html('');
		    		$('<div></div>').html(data.warningMessage).dialog({
		    		      height: 190,
		    		      width: 460,
		    		      modal: true,
		    		      closeOnEscape: false,
		    		      buttons: {
		    		        "Yes": function() {
		    		        	uploadExits(true);
		    		        	$(this).dialog("close");
		    		        },
		    		      	"Cancel": function() {
		    		          	$(this).dialog( "close" );
		    		       	}
		    		      }
		    		});
		    	} else if(data.errorFound) {
		    		$('#exitsUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to upload TEC file.</span>').show();
		    	}
		    	else {
						var uploadReport = "";
							if(data.uploadCompleted == true) {
								uploadReport += "Upload Status: Complete <br/>";
							} else {
								uploadReport += "<font size='5'>Upload Status: Failed.</font><br/>";
							}
							//US16252: Modified
					    	if(data.uploadedDetails != undefined && data.uploadedDetails.successCount != undefined && data.uploadedDetails.failedCount !=  undefined) {
					    		uploadReport += "Records Created:" + data.uploadedDetails.successCount +
	    							" Rejected:" + data.uploadedDetails.failedCount  + ' <br/>'; 
	    							//" Updated:" + data.recordsUpdatedCount; //US16252: Modified
					    	}
					    	
				
				    	//US16252: Modified
				    	if( data.uploadedDetails.failedCount > 0) {
				    		var currDate = new Date();
				    		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
				    		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
				    		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
				    		var fileName = data.organizationName +"TECUpload_Errors_"+dateStringForFile+".csv";
				    		
							uploadReport += '<a href="javascript:showExitsInvalidDetails()" style=""> View Details </a>';
							uploadReport += '<a href="getUploadErrorFile.htm?uploadedId='+data.uploadedDetails.id + '&fileName='+fileName+'" style="margin-left:20px"> Download Details </a>';
							var uploadDetails = '<table class="gridtable">';
							uploadDetails += "<tr> <th>State Student Identifier</th>";
							uploadDetails += '<th>Identifier</th>';
							uploadDetails += '<th>Reasons For Not Valid </th> </tr>';	
							//US16252: Modified
							var invalidRecords = data.uploadErrors;
							for (var i=0;i<invalidRecords.length;i++) {	
								uploadDetails += '<tr>';
								var identifier = '';
								var reasons = '';
								//var inValidDetails = invalidRecords[i].inValidDetails;						
								//for (var j=0;j<inValidDetails.length;j++) {							
									var fieldName = invalidRecords[i].fieldName;
									var formattedFieldValue = invalidRecords[i].reason;
									//var invalidType = invalidRecords[j].invalidType;
									var msgs = [];
									var reason = formattedFieldValue;
									if( reason.indexOf("###") > 0 )
										msgs = reason.split("###");
									else
										msgs[0] = reason ;
									
									identifier = "Line Number : " + invalidRecords[i].line + ": Field : " + fieldName ;//+ '</br>';
									reasons  = 'The record is rejected because ' + (msgs.length > 1 ? msgs[1] : msgs[0]);  // +' with '+formattedFieldValue+' is '+ invalidType.message + '</br>';
								//}
									
								uploadDetails += '<td>' + (msgs.length > 1 ? msgs[0]:"") +'</td>';	
								uploadDetails += '<td>' + identifier +'</td>';
								uploadDetails += '<td>' + reasons +'</td>';
								
								uploadDetails += '</tr>';
							}
							
							uploadDetails += '</table>';
							
							$('#exitsUploadReportDetails').html(uploadDetails);
					}
				    
					$('#exitsUploadReport').html(uploadReport);
					$('#exitsUploadReport').show();
		    	}*/
			//	$('#uploadExits').removeAttr('disabled');
			} else {
				if(data.uploadFileStatus === "IN_PROGRESS"){
					$('#uploadTECGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
				}
				//$('#exitsUploadReport').html('<span style="color:blue">Msg: Upload TEC file is in-progress.</span>').show();
	    //		$('#exitsUploadReport').html('<span style="">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
				window.setTimeout(function(){monitorUploadTECFile(uploadFileRecordId)}, 90000);
			}  
        });
	
	}

	function showTECUploadGrid(){ 
		  var uploadTECgrid = $('#uploadTECGridTableId');
		  $("#uploadTECDiv div").show();
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
		                  {label: 'Status', name: 'status', index: 'status',width:'400px',  hidedlg: true, formatter: tecUploadSatusFormatter},
		                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'120px',  formatter:extractTESUploadLinkFormatter},
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
		  pager: '#uploadTECGridPager',
		  sortname: 'id',
		  sortorder: 'DESC',
		  altclass: 'altrow',
		  viewrecords : true,
		  altRows: true,
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
		 loadTECUploadData();
		}
		function loadTECUploadData(){
			var categoryCode= "TEC_RECORD_TYPE";
		  $.ajax({
			  url: "getabUploadProgressStatus.htm?&categoryCode="+categoryCode,
		       type: "POST"
		    }).done(function(data) {
		           //  console.log(data);     
		          $('#uploadTECGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
		          
		       });

		}

		function tecUploadSatusFormatter(cellValue, options, rowObject){
			if( cellValue == "COMPLETED"){
				cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+rowObject.alertCount;
			}
			return cellValue;
		}
		
		function extractTESUploadLinkFormatter(cellValue, options, rowObject){
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
	
	$("#TEC_TemplatedownloadquickHelpPopup").hide();
	$("#TEC_TemplatedownloadquickHelpPopupClose").on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#TEC_TemplatedownloadquickHelpPopup").hide();
	});
	$("#TEC_TemplatedownloadquickHelp").on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#TEC_TemplatedownloadquickHelpPopup").show();
	});
	
	$('#TEC_TemplatedownloadquickHelp').on('mouseover',function(){
		$(".QuickHelpHint").show();
	});
	$('#TEC_TemplatedownloadquickHelp').on('mouseout',function(){
		$(".QuickHelpHint").hide();
	});
	$('.QuickHelpHint').on('mouseover',function(){
		$(".QuickHelpHint").show();
	});
	$('.QuickHelpHint').on('mouseout',function(){
		$(".QuickHelpHint").hide();
	});
	$('#TEC_Templatelink_popup').on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#TEC_TemplatedownloadquickHelpPopup").hide();
	});
	
	
});
		