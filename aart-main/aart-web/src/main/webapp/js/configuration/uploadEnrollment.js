    var invalidGridData=[];
    var uploadCategoryCode;
    var uploadEnrollMentCategoryCode = "ENRL_RECORD_TYPE";   
    var uploadEnrollmentFile = $('#enrollmentFileData');
	function uploadEnrollmentInit(categoryCode){
		uploadCategoryCode = categoryCode;
    	gUploadEnrollmentLoadOnce = true;
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
		
		$('#uploadEnrollmentOrgFilter').orgFilter({
			'containerClass': '',
			requiredLevels: [60]
		});
		
		
		//$('#uploadEnrollmentOrgFilter').orgFilter('option','requiredLevels',[60]);
		
		$('#uploadEnrollmentOrgFilterForm').validate({
			ignore: "",
			rules: {
				enrollmentFileData: {
		      		required: true,
		      		extension: "csv"
		    	}
		  	},
		  	 messages: {
		  		enrollmentFileData: "Currently only CSV files are supported. Please select a file with the extension of '.csv'."
		     }
		});
		
		$('#enrollmentUploadReportDetails').dialog({
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
		
	//	buildUploadEnrollmentGrid();
		
		$('input[id=enrollmentFileData]').on("change",function() {
			$('#enrollmentFileDataInput').val($('#enrollmentFileData')[0].files[0].name);
		});
		
		$('#uploadEnrollment').on("click",function(event) {
			if($('#uploadEnrollment').attr("disabled")=="disabled") {
				event.preventDefault();
	    	} else {
	    		uploadEnrollment(null);
	    	}
		});
		
		showEnrollmentUploadGrid();
		
		filteringOrganizationSet($('#uploadEnrollmentOrgFilterForm'));
		
	};	

/*	function buildUploadEnrollmentGrid() {

		$("#enrollmentGrid").trigger('GridDestroy');
		var rGrid = $("#enrollmentGrid");

	    getColumnIndexByName = function (grid, columnName) {
	        var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
	        for (i = 0; i < l; i++) {
	            if (cm[i].name === columnName) {
	                return i; // return the index
	            }
	        }
	        return -1;
	    };

	    var colNamesForenrollmentGrid = [
	                                 	"Id",
	                        			"Date",
	                        			"Time",
	                        			"Status",
	                        			"File",
	                        		];
	                        	    
	                        	    var cmForenrollmentGrid = [
	                        		   	{name: 'id', index: 'id', hidden: true }, 	
	                        			{name: 'uploadEnrollmentDate', index: 'uploadEnrollmentDate', align: 'center', width: 150, height: 40,  
	                        				sortable : true, search : true, hidden: false, hidedlg : false, formatter: dateFormatter},
	                        			{name: 'uploadEnrollmentTime', index: 'uploadEnrollmentTime', align: 'center', width: 150, height: 40,  
	                        				sortable : true, search : true, hidden: false, hidedlg : false, formatter:timeFormatter	},
	                        			{name: 'uploadEnrollmentStatus', index: 'uploadEnrollmentStatus', align: 'center', width: 250, height: 40,  
	                        				sortable : true, search : true, hidden: false, hidedlg : false	},	
	                        			{name: 'uploadEnrollmentFile', index: 'uploadEnrollmentFile', align: 'center', width: 150, height: 40,  
	                        					sortable : true, search : true, hidden: false, hidedlg : false	}																				
	                        	   ];
	                  

		rGrid.jqGrid({
			data: [],
			datatype: "local",
			loadonce: "true",
	        colNames: colNamesForenrollmentGrid,                       
	        colModel: cmForenrollmentGrid, 
	 	    height : 'auto',
	 	    shrinkToFit: false,
	 	    gridview: true,
	 	    width: 700,
	        rowNum:10, 
	        rowList:[10,20,30], 
	        pager: '#enrollmentGridPager',  
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
	*/
	function browseUploadEnrollmentCsv(event){
		 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }
		 $('input[id=enrollmentFileData]').click();
		}
	
	function uploadEnrollment(continueOnWarning) {
		var fd = new FormData();
		var date = new Date();
		var milliSec =date.getMilliseconds();
		var filedata = $('#enrollmentFileData');
		var filelist = filedata[0].files;
		var file = filelist[0];
		var stateId = $('#uploadEnrollmentOrgFilter_state').val();
		var regionId = $('#uploadEnrollmentOrgFilter_region').val();
		var areaId = $('#uploadEnrollmentOrgFilter_area').val();
		var districtId = $('#uploadEnrollmentOrgFilter_district').val();
		var buildingId = $('#uploadEnrollmentOrgFilter_building').val();
		var schoolId = $('#uploadEnrollmentOrgFilter_school').val();
		if($('#uploadEnrollmentOrgFilterForm').valid()) {
			$('#uploadEnrollment').attr("disabled","disabled");
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
				
			fd.append('categoryCode',uploadCategoryCode);
			fd.append('reportUpload',"false");
			fd.append('date', date.getTime());
			fd.append('milliSec',milliSec);
			fd.append('uploadFile',file);
			if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
			  	fd.append('continueOnWarning',continueOnWarning);
		  	}
			$('#enrollmentUploadReport').html('');
   			/*$('#enrollmentGrid').jqGrid('clearGridData');*/

			$.ajax({
				//url: 'uploadEnrollment.htm',
				url: 'uploadFileData.htm',//US16352: Modified
				data: fd,
				dataType: 'json',
				processData: false,
				contentType: false,
				cache: false,
				type: 'POST'
			}).done(function(data){
				loadEnrollmentUploadData(uploadEnrollMentCategoryCode);
				if(data.showWarning) {
					$('#uploadEnrollment').removeAttr('disabled');
	/*				$('#enrollmentUploadReport').html('');
		    		$('<div></div>').html(data.warningMessage).dialog({
		    		      height: 190,
		    		      width: 460,
		    		      modal: true,
		    		      closeOnEscape: false,
		    		      buttons: {
		    		        "Yes": function() {
		    		        	uploadEnrollment(true);
		    		        	$(this).dialog("close");
		    		        },
		    		      	"Cancel": function() {
		    		          	$(this).dialog( "close" );
		    		       	}
		    		      }
		    		});
		    		$('#uploadEnrollment').removeAttr('disabled');
	*/	    	} else if(data.errorFound) {
					$('#uploadEnrollment').removeAttr('disabled');
	/*	    		$('#enrollmentUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to upload enrollment file.</span>').show();
		    		$('#uploadEnrollment').removeAttr('disabled');
	*/	    	} 
		    	else if(data.nopermit){
		    		$('#uploadEnrollment').removeAttr('disabled');
	/*	    		$('#enrollmentUploadReport').html('<span class="error_message ui-state-error">Error Msg: Cannot upload enrollment file for Kansas state.</span>').show();
		    		$('#uploadEnrollment').removeAttr('disabled');
	*/	    	}
		    	else {
	//	    		$('#enrollmentUploadReport').html('<span style="">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
		    		monitorUploadEnrollmentFile(data.uploadId);
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
	
	function monitorUploadEnrollmentFile(uploadFileRecordId){
		$.ajax({
	        //url: 'monitorFileUploadEnrollment.htm',
			url: 'monitorUploadFileStatus.htm', //US16252: Modified 
	        type: 'GET',
	        cache: false,
	        data: {uploadFileRecordId: uploadFileRecordId}
		}).done(function(data) {
        	if(data.uploadFileStatus === 'FAILED' || data.errorFound){
      		  clearEnrollmentUploadFile();
      		  loadEnrollmentUploadData(uploadEnrollMentCategoryCode);
      		  $('#uploadEnrollment').removeAttr('disabled');
      	/*	var msg = 'Error Msg: Failed to upload enrollment file. ';
      		
      		if( data.uploadedDetails.failedCount == 0 && data.uploadedDetails.successCount == 0 ){
		    		if( data.uploadErrors.length == 1 ){
		    			var reason = data.uploadErrors[0].reason;
		    			msg += reason;
		    		}
      		}	
      		$('#enrollmentUploadReport').html('<span class="error">' + msg + '</span>').show();
      		$('#uploadEnrollment').removeAttr('disabled');
      	*/
        	} else if(data.uploadFileStatus === 'COMPLETED'){
        		  clearEnrollmentUploadFile();
      		  loadEnrollmentUploadData(uploadEnrollMentCategoryCode);
      		  $('#uploadEnrollment').removeAttr('disabled');
      		/*
				invalidGridData=[];
				if(data.showWarning) {
					$('#enrollmentUploadReport').html('');
		    		$('<div></div>').html(data.warningMessage).dialog({
		    		      height: 190,
		    		      width: 460,
		    		      modal: true,
		    		      closeOnEscape: false,
		    		      buttons: {
		    		        "Yes": function() {
		    		        	uploadEnrollment(true);
		    		        	$(this).dialog("close");
		    		        },
		    		      	"Cancel": function() {
		    		          	$(this).dialog( "close" );
		    		       	}
		    		      }
		    		});
		    	} else if(data.errorFound) {
		    		$('#enrollmentUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to upload enrollment file.</span>').show();
		    	}
		    	else {
						if(data.invalidParentOrg != null) {
						$(".studentARTSmessages").show();
						$('#invalidParentOrgMessage').html(data.invalidParentOrg);
						$('#invalidParentOrgMessage').show();
						setTimeout("aart.clearMessages()", 5000);
						setTimeout(function(){ $(".studentARTSmessages").hide(); },5000);	
					} else {
						var uploadReport = "";
						if(data.uploadCompleted == true) {
							uploadReport += "Upload Status: Complete <br/>";
						} else {
							uploadReport += "<font size='5' color='red'>Upload Status: Failed.</font><br/>";
						}
						//US16252: Modified
				    	if(data.uploadedDetails != undefined && data.uploadedDetails.successCount != undefined && data.uploadedDetails.failedCount !=  undefined) {
				    		uploadReport += "Records Created/Updated:" + data.uploadedDetails.successCount +
  							" Rejected:" + data.uploadedDetails.failedCount + '<br/>'; 
  							//" Updated:" + data.recordsUpdatedCount; //US16252: Modified
				    	}
				    	//US16252: Modified
				    	if( data.uploadedDetails.failedCount > 0) {
				    		var currDate = new Date();
				    		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
				    		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
				    		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
				    		var fileName = data.organizationName +"EnrollmentUpload_Errors_"+dateStringForFile+".csv";
				    		
							uploadReport += '<a href="javascript:showEnrollmentInvalidDetails()" style=""> View Details </a>';
							uploadReport += '<a href="getUploadErrorFile.htm?uploadedId='+data.uploadedDetails.id + '&fileName='+fileName+'" style="margin-left:20px"> Download Details </a>';
								
							//US16252: Modified
							var invalidRecords = data.uploadErrors;
							for (var i=0;i<invalidRecords.length;i++) {	
								var rowObj={};
								var reason = invalidRecords[i].reason;
								var msgs = ["",""];
								if( reason.indexOf("###") > 0 )
									msgs = reason.split("###");
								else
									msgs[1] = reason;
								
								rowObj.ssi = msgs[0];
								rowObj.fieldName = invalidRecords[i].fieldName;
								rowObj.message =  msgs[1];
								invalidGridData.push(rowObj);
							}
							
							var uploadDetails = "<div id='invalidGridContainer' class='kite-table'><table class='responsive' id='invalidEnrollmentrGrid' ></table></div>";
							$('#enrollmentUploadReportDetails').html(uploadDetails);
						}
				    	
						$('#enrollmentUploadReport').html(uploadReport);
						$('#enrollmentUploadReport').show();
					}
		    	}
				$('#uploadEnrollment').removeAttr('disabled');
			*/} else {
				if(data.uploadFileStatus === "IN_PROGRESS"){
					$('#uploadEnrollmentGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
				}
//				$('#enrollmentUploadReport').html('<span style="">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
				window.setTimeout(function(){monitorUploadEnrollmentFile(uploadFileRecordId)}, 90000);
			}  
      });
		
		
	}
	
	function showEnrollmentUploadGrid(){ 

		  var uploadEnrollmentgrid = $('#uploadEnrollmentGridTableId');
		  $("#uploadEnrollmentDivGrid div").show();
		  

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
		                  {label: 'Status', name: 'status', index: 'status',width:'400px',  hidedlg: true,formatter: enrollmentUploadSatusFormatter},
		                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'120px',  formatter:extractEnrollmentUploadLinkFormatter},
		                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
		                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
		                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
		                 ];

		 $(uploadEnrollmentgrid).jqGrid({
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
		  pager: '#uploadEnrollmentGridPager',
		  viewrecords : true,
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
		     },
		     loadComplete:function(data){
		    	 $('#uploadEnrollment').removeAttr("disabled");
			 }  
		  });
		 loadEnrollmentUploadData(uploadEnrollMentCategoryCode);
		}
		function loadEnrollmentUploadData(){
		  $.ajax({
			  url: "getabUploadProgressStatus.htm?&categoryCode="+uploadCategoryCode,
		       type: "POST"
		    }).done(function(data) {
	            $('#uploadEnrollmentGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
		          
		       });

		}
		
		function enrollmentUploadSatusFormatter(cellValue, options, rowObject){
			if( cellValue == "COMPLETED"){
				cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount  + " Alerts: "+rowObject.alertCount;
			}
			return cellValue;
		}

		function extractEnrollmentUploadLinkFormatter(cellValue, options, rowObject){
			var status = rowObject.statusCheck;
			if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
				var dataOrganizationName=rowObject.organizationName;
				var currDate = new Date();
				var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
				var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
				+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
				var file = rowObject.fileName.split(".",1);
				var fileName = file +"_EnrollmentUpload_Errors_"+dateStringForFile+".csv";
				return '<a title="Click to download extract" href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"  ><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
			}
			else
				return '<a> </a>';
		}

	$(document).ready(function(){
		
		
		$("#ENR_TemplatedownloadquickHelpPopupClose").on('click keypress',function(event){
			if(event.type=='keypress'){
				   if(event.which !=13){
				    return false;
				   }
				  }
			$("#ENR_TemplatedownloadquickHelpPopup").hide();
		});
		$("#ENR_Templatedownloadquick").on('click keypress',function(event){
			if(event.type=='keypress'){
				   if(event.which !=13){
				    return false;
				   }
				  }
			$("#ENR_TemplatedownloadquickHelpPopup").show();
		});
		
		$('#ENR_Templatedownloadquick').on('mouseover',function(){
			$(".QuickHelpHint").show();
		});
		$('#ENR_Templatedownloadquick').on('mouseout',function(){
			$(".QuickHelpHint").hide();
		});
		$('.QuickHelpHint').on('mouseover',function(){
			$(".QuickHelpHint").show();
		});
		$('.QuickHelpHint').on('mouseout',function(){
			$(".QuickHelpHint").hide();
		});
		$('#ENR_Templatelink_popup').on('click keypress',function(event){
			if(event.type=='keypress'){
				   if(event.which !=13){
				    return false;
				   }
				  }
			$("#ENR_TemplatedownloadquickHelpPopup").hide();
		});
	});	
		
	function clearEnrollmentUploadFile(){
		$('#enrollmentFileData').val("");
		uploadEnrollmentFile.replaceWith( uploadEnrollmentFile = uploadEnrollmentFile.clone( true ) );
		$('#enrollmentFileDataInput').val("");
	}