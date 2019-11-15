/**
 * Biyatpragyan Mohanty : US14308 : User Management Upload Users CSV
 * Contains all javascript needful for upload user functionality.
 */
var uploadUserFile = $('#userFileData');
$(function() {
	
	$("#User_TemplatedownloadquickHelpPopup").hide();
	$("#User_TemplatedownloadquickHelpPopupClose").on('click keypress',function(event){
		if(event.type=='keypress'){
			if(event.which !=13){
				return false;
			}
		}
		$("#User_TemplatedownloadquickHelpPopup").hide();
	});
	$("#User_TemplatedownloadquickHelp").on('click keypress',function(event){
		if(event.type=='keypress'){
			if(event.which !=13){
				return false;
			}
		}
		
		$("#User_TemplatedownloadquickHelpPopup").show();
	});
	
	$('#User_TemplatedownloadquickHelp').on('mouseover focus',function(){
			$(".QuickHelpHint").show();
	});
	$('#User_TemplatedownloadquickHelp').on('mouseout blur',function(){
		$(".QuickHelpHint").hide();
	});
	$('.QuickHelpHint').on('mouseover focus',function(){
		$(".QuickHelpHint").show();
	});
	$('.QuickHelpHint').on('mouseout blur',function(){
		$(".QuickHelpHint").hide();
	});
	$('#User_TemplatelinkPopup').on('click keypress',function(event){
		if(event.type=='keypress'){
			if(event.which !=13){
				return false;
			}
		}
		$("#User_TemplatedownloadquickHelpPopup").hide();
	});
	
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
	
	$('#userUploadReportDetails').dialog({
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
	 * This method is called when user clicks on upload button to upload users.
	 */
	$('#uplodUser').on("click",function(event) {
		if($('#uplodUser').attr("disabled")=="disabled") {
			event.preventDefault();
    	} else {
    		uploadUsers();
    	}	
		$('#UserrequiredMessage').html('Correct below error(s).');
	});
	
	$('input[id=userFileData]').on("change",function() {
		$('#userFileDataInput').val($('#userFileData')[0].files[0].name);
	});
	
	function uploadUsers(continueOnWarning) {
		aart.clearMessages();
		var date = new Date();
		var milliSec =date.getMilliseconds();

		$("#UserARTSmessages").hide();
		$('#userUploadReport').html('').hide();
		if($('#uploadUserFilterForm').valid()) {
			$('#uplodUser').attr("disabled","disabled");
			$('#load_uploadUserGridTableId').show();
			var stateId = $('#uploadUserOrgFilter_state').val();
			var districtId = $('#uploadUserOrgFilter_district').val();
			var schoolId = $('#uploadUserOrgFilter_school').val();
			var selectedOrgId = $('#uploadUserOrgFilter').orgFilter('value');
			
			var fd = new FormData();
			var filedata = $('#userFileData');
			var filelist = filedata[0].files;
			var file = filelist[0];
			if(stateId != null)
			  fd.append('stateId',stateId);
			if(districtId != null)
			  fd.append('districtId',districtId);
			if(schoolId != null)
			  fd.append('schoolId',schoolId);
			if(selectedOrgId != null)
			  fd.append('selectedOrgId',selectedOrgId);
			
			fd.append('categoryCode','USER_RECORD_TYPE');
			fd.append('reportUpload',false);
			fd.append('uploadFile',file);
			fd.append('date', date.getTime());
			fd.append('milliSec',milliSec);
			  if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
				  	fd.append('continueOnWarning',continueOnWarning);
			  	}
			  $.ajax({
				//url: 'uploadUser.htm',
				url: 'uploadFileData.htm',//US16352: Modified
			    data: fd,
			    dataType: 'json',
			    processData: false,
			    contentType: false,
			    cache: false,
			    type: 'POST'
			  }).done(function(data){
			    	loadUserUploadData();
			    	if(data.showWarning) {
			    		$('#uplodUser').removeAttr('disabled');
			    	/*	$('#userUploadReport').html('');
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
			    		$('#uplodUser').removeAttr('disabled');
			    */	} else if(data.errorFound) {
			    	$('#uplodUser').removeAttr('disabled');
			    	/*	$('#userUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to Upload User file.</span>').show();
			    		$('#uplodUser').removeAttr('disabled');
			    	*/} else {
			    		/**
			    		 * US16252 : Download error file 
			    		 */
			    		//$('#userUploadReport').html('<span style="color:blue">Msg: Upload User file is in-progress.</span>').show();
			    	/*	$('#userUploadReport').html('<span style="">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
			    		$('#uploadUsers').parent().css({"cursor":"progress"});
			    	*/	//monitorUploadUserFile(data.uploadFileRecordId);
				    	monitorUploadUserFile(data.uploadId);
			    	}
			    }).fail(function(jqXHR,textStatus,errorThrown) {
			    	$('#load_uploadUserGridTableId').hide();
			    	var e=errorThrown;
			    });
		} else{
			$("#UserARTSmessages,#UserrequiredMessage").show();
			setTimeout("aart.clearMessages()", 10000);
			setTimeout(function(){ $("#UserARTSmessages").hide(); },10000);
		}	
	}
	

});

function browseUploadUserCsv(event){
	
	if(event.type=='keypress'){
		if(event.which !=13){
			return false;
		}
	}
$('input[id=userFileData]').click();
}
function clearUserUploadFile(){
	$('#userFileData').val("");
	uploadUserFile.replaceWith( uploadUserFile = uploadUserFile.clone( true ) );
	$('#userFileDataInput').val("");
}
function monitorUploadUserFile(uploadFileRecordId){
	$.ajax({
        //url: 'monitorUploadUserFile.htm',
		url: 'monitorUploadFileStatus.htm', //US16252: Modified 
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function(data) {
    	
    	if(data.uploadFileStatus === 'FAILED' || data.errorFound){
    		clearUserUploadFile();
    		loadUserUploadData();
    		$('#uplodUser').removeAttr('disabled');
    		/*$('#uploadUsers').parent().css({"cursor":""});
    		var msg = 'Error Msg: Failed to Upload User file.';
    		
    		if( data.uploadedDetails.failedCount == 0 && data.uploadedDetails.successCount == 0 ){
	    		if( data.uploadErrors.length == 1 ){
	    			var reason = data.uploadErrors[0].reason;
	    			msg += reason;
	    		}
    		}	
    		
    		$('#userUploadReport').html('<span class="error_message ui-state-error">'+ msg +'</span>').show();
    		$('#uplodUser').removeAttr('disabled');*/
    	}else if(data.uploadFileStatus === 'COMPLETED'){
    		clearUserUploadFile();
    		loadUserUploadData();
    		$('#uplodUser').removeAttr('disabled');
    	/*	$('#uploadUsers').parent().css({"cursor":""});
	    	$('#load_uploadUserGridTableId').hide();
	    	if(data.showWarning) {
	    		$('#userUploadReport').html('');
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
	    		$('#userUploadReport').html('<span class="error_message ui-state-error">Error Msg: Failed to Upload User file.</span>').show();
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
		    		var users = data.successUsers;
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
			     	}
			    	var uploadReport = "";
			    	if(data.uploadCompleted==true) {
			    		uploadReport += "Upload Status: Complete <br/>";
			    	} else {
			    		uploadReport += "Upload Status: Failed. <br/>";
			    	}
			    	//US16252: Modified
			    	if(data.uploadedDetails != undefined && data.uploadedDetails.successCount != undefined && data.uploadedDetails.failedCount !=  undefined) {
			    		uploadReport += "Records Created:" + data.uploadedDetails.successCount +
							" Rejected:" + data.uploadedDetails.failedCount + " <br/> "; 
							//" Updated:" + data.recordsUpdatedCount; //US16252: Modified
			    	}
			    	//US16252: Modified
			    	if( data.uploadedDetails.failedCount > 0) {
			    		var currDate = new Date();
			    		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
			    		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
			    		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
			    		var fileName = data.organizationName +"UserUpload_Errors_"+dateStringForFile+".csv";
			    		
						uploadReport += '<a href="javascript:showUserInvalidDetails()" style=""> View Details </a>';
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
						
						$('#userUploadReportDetails').html(uploadDetails);
					}
			    	
			    	
			    	$('#userUploadReport').html(uploadReport);
			    	$('#userUploadReport').show();				    	
			    	$('#uploadUserGridTableId').jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
		    	}
	    	}
	    	$('#uplodUser').removeAttr('disabled');
		*/} else {
			
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadUserGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
			/**
    		 * US16252 : Download error file 
    		 */
			//$('#userUploadReport').html('<span style="color:blue">Msg: Upload User file is in-progress.</span>').show();
		//	$('#userUploadReport').html('<span style="">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
			window.setTimeout(function(){monitorUploadUserFile(uploadFileRecordId)}, 90000);
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

function showUserInvalidDetails() {
	$('#userUploadReportDetails').dialog('open');	
}
/* Added for US16548*/
function showUserUploadGrid(){ 

	  var uploadUsergrid = $('#uploadUserGridTableId');
	  
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
	                  {label: 'Status', name: 'status', index: 'status',width:'350px',  hidedlg: true,  formatter: userUploadSatusFormatter},
	                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:cellWidthForVO,  formatter:extractUserUploadErrorLinkFormatter},
	                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
	                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
	                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
	                 ];

	 $(uploadUsergrid).jqGrid({
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
	  pager: '#uploadUserGridPager',
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
	 loadUserUploadData();
	}
	function loadUserUploadData(){
		var categoryCode= "USER_RECORD_TYPE";
	  $.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+categoryCode,
	       type: "POST"
	    }).done(function(data) {
	           //  console.log(data);     
	          $('#uploadUserGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	          
	       });

	}
	
	function userUploadSatusFormatter(cellValue, options, rowObject){
		if( cellValue == "COMPLETED"){
			cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+rowObject.alertCount;
		}
		return cellValue;
	} 

	function extractUserUploadErrorLinkFormatter(cellValue, options, rowObject){
		var status = rowObject.statusCheck;
		if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
			var dataOrganizationName=rowObject.organizationName;
			var currDate = new Date();
			var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
			var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
			+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
			var file = rowObject.fileName.split(".",1);
			var fileName = file +"_UserUpload_Errors_"+dateStringForFile+".csv";
			return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
		}
		else
			return '<a> </a>';
	}
	
	function upload_Init_User_Tab(){
		//Initiate organization filter
		$('#uploadUserOrgFilter').orgFilter({
			'containerClass': '',
			requiredLevels: [20,30,50]
		});
		
		//$('#uploadUserOrgFilter').orgFilter('option','requiredLevels',[50]);

		//Validation rules for the filter form.
		$('#uploadUserFilterForm').validate({
			ignore: "",
			rules: {
				userFileData: {
		      		required: true,
		      		extension: "csv"
		    	}
		  	}
		});
		
		filteringOrganizationSet($('#uploadUserFilterForm'));
		
	}