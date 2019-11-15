var invalidGridData=[];    
var uploadRosterFile = $('#rosterFileData');
function uploadRostersInit(){
	gUploadRostersLoadOnce = true;
		$('#uploadRoster .hidden').hide();
		//initRosterGrid();
		initViewEducatorGrid();
		initViewAssignedStudentGrid();
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
		 
		$('#uploadRosterOrgFilter').orgFilter({
			'containerClass': '',
			requiredLevels: [70]
		});
		
		//$('#uploadRosterOrgFilter').orgFilter('option','requiredLevels',[70]);
	    
		$('#rosterUploadReportDetails').dialog({
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
		
		$('#uploadRosterFilterForm').validate({
			ignore: "",
			rules: {
				rosterFileData: {
		      		required: true,
		      		extension: "csv"
		    	}
		  	}
		});
		
		//$('#uploadRosterOrgFilter').orgFilter();
		//$("#rosterStateFilter").show();
		$("#isRosterUpload").val(1);
		$("#progressbar").progressbar();
	    $("#uploadRosterBtn").on("click", function(e){
	    	if($('#uploadRosterBtn').attr("disabled")=="disabled") {
	    		e.preventDefault();
	    	} else if($('#uploadRosterFilterForm').valid()){
	    		uploadRoster();
	    	}
	    });
		
	    filteringOrganizationSet($('#uploadRosterFilterForm'));
	    
	};

/*	function initRosterGrid(){
		var $gridAuto = $("#rosterGrid");
		//Unload the grid before each request.
		$gridAuto.jqGrid('GridUnload');
		var gridWidthForVR = $('#rosterGrid').parent().width();		
		if(gridWidthForVR < 700) {
			gridWidthForVR = 700;				
		}
		var cellWidthForVR = gridWidthForVR/5;
		
		var cmforViewRosters = [	         		       
		        				{ name : 'id', index : 'id', width : cellWidthForVR, sortable: true, search : false, hidden: true, hidedlg: false },
		        				{ name : 'name', index : 'name', width : cellWidthForVR, sortable: true, search : true, hidden: false, hidedlg: false },
		        				{ name : 'edId', index : 'edId', width : cellWidthForVR, sortable: true, search : true, hidden : false, hidedlg : false},
		        				{ name : 'firstName', index : 'firstName', width : cellWidthForVR, sortable: true, search : true, hidden : false, hidedlg : false},
		        				{ name : 'lastName', index : 'lastName', width : cellWidthForVR, sortable: true, search : true, hidden : false, hidedlg : false},
		        				{ name : 'contentArea', index : 'contentArea', width : cellWidthForVR, sortable: true, search : true, hidden : false, hidedlg : false},
		        				{ name : 'stateCourseCode', index : 'stateCourseCode', width : cellWidthForVR, search : true, hidden : false, hidedlg : false},
		        				{ name : 'edStatus', index : 'edStatus', width : cellWidthForVR, search : false, hidden : false, hidedlg : false},
		        				{ name : 'currentSchoolYear', index : 'currentSchoolYear', width : cellWidthForVR, search : false, hidden : false, hidedlg : false},
		        				{ name : 'schoolName', index : 'schoolName', width : cellWidthForVR, sortable: true, search : true, hidden : false, hidedlg : false}
	
		        				];
		//JQGRID
		$gridAuto.scb({
			
			datatype : "local",
			width: gridWidthForVR,
		  	colModel :cmforViewRosters,
		  	colNames : [ "Id", "Roster Name", "Educator Identifier", "First Name", "Last Name", "Subject", "Course", "Educator Status", "School Year", "School Name" ],
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			pager : '#rosterGridPager',
			sortname : 'id',
			loadonce: false,
			viewable: false,
			onSelectRow: function (rowid,status,e) {
		   		var rowData = this.p.data[this.p._index[rowid]];
		   	    //openUploadRosterPopup(rowData);
		   	},
		    beforeRequest: function() {
		    	//Set the page param to lastpage before sending the request when 
				//the user entered current page number is greater than lastpage number.
				var currentPage = $(this).getGridParam('page');
		        var lastPage = $(this).getGridParam('lastpage');
		              
		        if (lastPage!= 0 && currentPage > lastPage) {
		        	$(this).setGridParam('page', lastPage);
		            $(this).setGridParam({postData: {page : lastPage}});
		        }
		  
		    }
		});	
};*/

	function browseUploadRosterCsv(event){
		 
		 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }		
		$('input[id=rosterFileData]').click();
		}
	
function _validRosterFile(file) {
	$("#uploadRosterError").text("");
	$("#uploadRosterError").hide();
	if(file==null) {
		$("#uploadRosterError").text("File name must be entered.");
		$("#uploadRosterError").show();
		$('html, body').animate({
			scrollTop: $("#uploadRosterError").offset().top
		}, 1500);
		return false;
	}
	if(file.name.trim().length == 0) {
		$("#uploadRosterError").text("File name must be entered.");
		$("#uploadRosterError").show();
		$('html, body').animate({
			scrollTop: $("#uploadRosterError").offset().top
		}, 1500);
		return false;
	}
	var mimeType= file.type;
	if(mimeType=="text/comma-separated-values") {
		return true;
	} 
	if(file.name.split('.').pop().toUpperCase() != 'CSV') {
		$("#uploadRosterError").text("File type must be csv.");
		$("#uploadRosterError").show();
		$('html, body').animate({
			scrollTop: $("#uploadRosterError").offset().top
		}, 1500);
		return false;
	}
	if(file.size >= 16777216){
		$("#uploadRosterError").text("File size exceeds 16mb.  Please upload a smaller file.").show();
		$('html, body').animate({
			scrollTop: $("#uploadRosterError").offset().top
		}, 1500);
		return false;
	}
	return true;
}
  
function uploadRoster(continueOnWarning){
  var fd = new FormData();
  var date = new Date();
  var milliSec =date.getMilliseconds();

  var filedata = $('#rosterFileData');
  var filelist = filedata[0].files;
  var file = filelist[0];
  if(_validRosterFile(file)==false) {
	  $('#rosterError').text("");
	  return;
  }
  $("#uploadRosterBtn").attr("disabled","disabled");
  $('#rosterUploadReport').hide();
  var stateId = $('#uploadRosterOrgFilter_state').val();
  var districtId = $('#uploadRosterOrgFilter_district').val();
  var schoolId = $('#uploadRosterOrgFilter_school').val();
  if(stateId != null)
	  fd.append('stateId',stateId);
  if(districtId != null)
	  fd.append('districtId',districtId);
  if(schoolId != null)
	  fd.append('schoolId',schoolId);
  
	var selectedOrgId = stateId;
	if( schoolId != null && schoolId != undefined && schoolId.trim().length > 0 )
		selectedOrgId = schoolId;
	else if( districtId != null && districtId != undefined && districtId.trim().length > 0)
		selectedOrgId = districtId;
	
	if(selectedOrgId != null)
		fd.append('selectedOrgId',selectedOrgId);
		
	fd.append('categoryCode','SCRS_RECORD_TYPE');
	fd.append('reportUpload',"false");
	fd.append('date', date.getTime());
	fd.append('milliSec',milliSec);

  fd.append('uploadFile',file);
  if(typeof continueOnWarning != 'undefined' && continueOnWarning != null && continueOnWarning != '') {
	  fd.append('continueOnWarning',continueOnWarning);
  }
  
  $.ajax({
	//url: 'uploadRoster.htm',
	url: 'uploadFileData.htm',//US16352: Modified  
    data: fd,
    dataType: 'json',
    processData: false,
    contentType: false,
    cache: false,
    type: 'POST'
	}).done(function(data){
    	loadRosterUploadData();
    	if(data.showWarning) {
    		$("#uploadRosterBtn").removeAttr('disabled');
    	
    /*		$('#rosterUploadReport').html('');
    		$('<div></div>').html(data.warningMessage).dialog({
    		      height: 190,
    		      width: 460,
    		      modal: true,
    		      closeOnEscape: false,
    		      buttons: {
    		        "Yes": function() {
    		        	uploadRoster(true);
    		        	$(this).dialog("close");
    		        },
    		      	"Cancel": function() {
    		          	$(this).dialog( "close" );
    		       	}
    		      }
    		});
    		$("#uploadRosterBtn").removeAttr('disabled');
    */	} else if(data.errorFound) {
    	$("#uploadRosterBtn").removeAttr('disabled');
    /*		$('#rosterUploadReport').html('<span class="error">Error Msg: Failed to upload roster file.</span>').show();
    		$("#uploadRosterBtn").removeAttr('disabled');
    */	} 
    	else if(data.nopermit){
    		$("#uploadRosterBtn").removeAttr('disabled');
    		//$('#rosterUploadReport').html('<span class="error">Error Msg: Cannot upload roster file for Kansas state.</span>').show();
    	/*	$("#uploadRosterBtn").removeAttr('disabled');
    		$("#rostermessages_uploadroster").show();
    		$('#ksPermissionDeniedMessage_uploadroster').show();
    		setTimeout(function(){ $("#rostermessages_uploadroster").hide(); },3000);
    */	}
    	else {
    		//$('#rosterUploadReport').html('<span style="color:blue">Msg: Upload roster file is in-progress.</span>').show();
        	//monitorUploadRosterFile(data.uploadFileRecordId);
    	//	$('#rosterUploadReport').html('<span style="color:blue">Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
        	//$('#uploadUsers').parent().css({"cursor":"progress"});
        	monitorUploadRosterFile(data.uploadId);
    	}
    }).fail(function(jqXHR, textStatus, errorThrown) {
			var e = errorThrown;
	});

}

function monitorUploadRosterFile(uploadFileRecordId){
	$.ajax({
        //url: 'monitorFileUploadConfiguration.htm',
		url: 'monitorUploadFileStatus.htm', //US16252: Modified 
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId},
	}).done(function(data) {
    	
    	
    	if(data.uploadFileStatus === 'FAILED' || data.errorFound){
    		loadRosterUploadData();
    		$("#uploadRosterBtn").removeAttr('disabled');
    		clearRosterUploadFile();
    		/*		var msg = 'Error Msg: Failed to upload roster file. ';
    		
    		if( data.uploadedDetails.failedCount == 0 && data.uploadedDetails.successCount == 0 ){
	    		if( data.uploadErrors.length == 1 ){
	    			var reason = data.uploadErrors[0].reason;
	    			msg += reason;
	    		}
    		}	
   */	}else if(data.uploadFileStatus === 'COMPLETED'){
	   		loadRosterUploadData();
	   		$("#uploadRosterBtn").removeAttr('disabled');
	   		clearRosterUploadFile();
/*	    	invalidGridData=[];
	    	if(data.showWarning) {
	    		$('#rosterUploadReport').html('');
	    		$('<div></div>').html(data.warningMessage).dialog({
	    		      height: 190,
	    		      width: 460,
	    		      modal: true,
	    		      closeOnEscape: false,
	    		      buttons: {
	    		        "Yes": function() {
	    		        	uploadRoster(true);
	    		        	$(this).dialog("close");
	    		        },
	    		      	"Cancel": function() {
	    		          	$(this).dialog( "close" );
	    		       	}
	    		      }
	    		});
	    	} else if(data.errorFound) {
	    		$('#rosterUploadReport').html('<span class="error">Error Msg: Failed to upload roster file.</span>').show();
	    	} else {
	    		var uploadReport = "";
				
				if(data.uploadCompleted == true) {
					uploadReport += "Upload Status: Complete <br/>";
				} else {
					uploadReport += "<font size='5'>Upload Status: Failed.</font><br/>";
				}
				//US16252: Modified
		    	if(data.uploadedDetails != undefined && data.uploadedDetails.successCount != undefined && data.uploadedDetails.failedCount !=  undefined) {
		    		uploadReport += "Records Created/Updated/Removed:" + data.uploadedDetails.successCount +
						" Rejected:" + data.uploadedDetails.failedCount + ' <br/> '; 
						//" Updated:" + data.recordsUpdatedCount; //US16252: Modified
		    	}
		    	//US16252: Modified
		    	if( data.uploadedDetails.failedCount > 0) {
		    		var currDate = new Date();
		    		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
		    		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
		    		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
		    		var fileName = data.organizationName +"RosterUpload_Errors_"+dateStringForFile+".csv";
		    		
					uploadReport += '<a href="javascript:_showRosterInvalidDetails()" style=""> View Details </a>';
					uploadReport += '<a href="getUploadErrorFile.htm?uploadedId='+data.uploadedDetails.id + '&fileName='+fileName+'" style="margin-left:20px"> Download Details </a>';
						
					//US16252: Modified
					var invalidRecords = data.uploadErrors;
					for (var i=0;i<invalidRecords.length;i++) {	
						var rowObj={};
						var msgs = ["",""];
						var reason = invalidRecords[i].reason;
						
						if( reason.indexOf("###") > 0 )
							msgs = reason.split("###");
						else
							msgs[1] = reason; 
						
						rowObj.ssi = msgs[0];
						rowObj.fieldName = invalidRecords[i].fieldName;
						rowObj.message = msgs[1] ;
						invalidGridData.push(rowObj);
					}
					
					var uploadDetails = "<div id='invalidGridContainer' class='kite-table'><table class='responsive' id='invalidRosterGrid' ></table></div>";
					$('#rosterUploadReportDetails').html(uploadDetails);
				}
		  				$('#rosterUploadReport').html(uploadReport);
				$('#rosterUploadReport').show();
			}
	    	$("#uploadRosterBtn").removeAttr('disabled');
*/		} else {
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadRosterGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
//			$('#rosterUploadReport').html('<span>Upload Status: in progress.<br> Upload Status will change when the job is complete </span>').show();
			window.setTimeout(function(){monitorUploadRosterFile(uploadFileRecordId)}, 90000);
		}  
    });

}

function getEducatorStatus(status) {
	if(status) {
		return "Active";
	}
	return "Inactive";
}

function _showRosterInvalidDetails() {	
	$("#invalidRosterGrid").jqGrid('clearGridData');
	$("#invalidRosterGrid").jqGrid("GridUnload");
	$("#invalidRosterGrid").jqGrid({
		datatype: "local",
		data: invalidGridData,
		height: 500,
		width: 'auto',
		altclass: 'altrow',
		shrinkToFit: true,
	   	colNames:['State Student Identifier','Identifier', 'Reasons For Not Valid'],
	   	colModel:[
	   		{name:'ssi' ,align:"left",width:50, height: 60},
	   		{name:'fieldName',width:300, height: 60},
	   		{name:'message',align:"left",width:800, height: 60}
	   	],
	   	viewrecords: true,
	    loadonce: true,
	    altRows : true,
	   	hoverrows : true,
	    grouping:true,
	    rowNum:99999, 
	    hidegrid: false,
	   	groupingView : {
	   		groupField : ['ssi'],
	   		groupColumnShow : [false]
	   	},
	    caption: "Upload Roster - Invalid Record Details "
	});
	$('#rosterUploadReportDetails').dialog('open');
}

function initViewEducatorGrid(){ 
	var $gridAuto = $("#viewEducatorGrid");
	//Unload the grid before each request.
	$("#viewEducatorGrid").jqGrid('clearGridData');
	$("#viewEducatorGrid").jqGrid("GridUnload");
	var gridWidthForVR = $('#viewEducatorGrid').parent().width();		
	if(gridWidthForVR < 700) {
		gridWidthForVR = 700;				
	}
	var cellWidthForVR = gridWidthForVR/5;
	
	var cmForViewEdGrid = [ {
		name : 'id',
		index : 'id',
		lable:'roster_educator_id',
		align : 'center',
		width : cellWidthForVR,
		sortable : false,
		hidden : false,
	}, {
		name : 'firstName',
		index : 'firstName',
		label : 'roster_educator_firstname',
		align : 'center',
		width : cellWidthForVR,
		sortable : false,
		hidden : false,
	}, {
		name : 'lastName',
		index : 'lastName',
		label : 'roster_educator_lastname',
		align : 'center',
		width : cellWidthForVR,
		sortable : false,
		hidden : false,
	} ];

	//JQGRID
	$gridAuto.scb({
		datatype : "local",
		width: gridWidthForVR,
	  	colModel :cmForViewEdGrid,
	  	colNames : [ "Educator Identifier", "First Name", "Last Name" ],   
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewEducatorGridPager',
		loadonce: true
	});	
	
	
}

function initViewAssignedStudentGrid() {
	
	var $gridAuto = $("#viewAssignedStudentGrid");
	//Unload the grid before each request.
	$("#viewAssignedStudentGrid").jqGrid('clearGridData');
	$("#viewAssignedStudentGrid").jqGrid("GridUnload");
	var gridWidthForVR = $('#viewAssignedStudentGrid').parent().width();		
	if(gridWidthForVR < 700) {
		gridWidthForVR = 700;				
	}
	var cellWidthForVR = gridWidthForVR/5;
	
	var cmforViewRosters = [ 
                			{name: 'id', index: 'id', label:'roster_student_id', hidden:true},
                   			{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', label : 'stateStudentIdentifierUploadRoster', width : cellWidthForVR, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false, hidedlg : true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
                   				    
                   								} 							
                   							  }	
                   			
                   			},
                 			{ name : 'legalFirstName', index : 'legalFirstName', label : 'legalFirstNameUploadRoster', width : cellWidthForVR, viewable: true, 
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
                   				    
                   				} 							
                   			}
                   			},
                   			{ name : 'legalLastName', index : 'legalLastName', label : 'legalLastNameUploadRoster', width : cellWidthForVR,  viewable: true,  hidden : false, hidedlg : true,
                   				searchoptions:{ sopt:['cn'], dataInit: function(elem) {
                   				    
                   				} 							
                   			}	
                   			},
                   			{name: 'gradeCourse', index: 'gradeCourse', label : 'gradeCourseUploadRoster', width : cellWidthForVR, hidden:true}
                   		];
                        
	//JQGRID
	$gridAuto.scb({
		//url : "getStudentEnrollmentsByRosterId.htm?q=1",
		//mtype: "POST",
		datatype : "local",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
        colNames : ["Id",
   		   			"Student Identifier",
   					"First Name",
   					"Last Name",
   					"Grade Course"
   		           ],      
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewAssignedStudentGridPager',
		sortname : 'id',
		loadonce: false,
	    beforeRequest: function() {
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        };	  
	    }
	});	
	
}

function refreshViewEducatorGrid(rowData) {
	var myData = [ {
		id : rowData.edId,
		firstName : rowData.firstName,
		lastName : rowData.lastName
	} ];
	
	var $gridAuto = $("#viewEducatorGrid");
	$gridAuto[0].clearToolbar();
	$gridAuto.setGridParam({ 'data': myData}).trigger("reloadGrid");
}

function refreshViewAssignedStudentGrid(rosterId) {
	var $gridAuto = $("#viewAssignedStudentGrid");
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		url : 'getStudentEnrollmentsByRosterId.htm?q=1', 
		search: false, datatype:"json", 
		postData: { "filters": null, rosterId: rosterId}
	}).trigger("reloadGrid",[{page:1}]);
}

function openUploadRosterPopup(rowData) {

	$('#uploadRosterPopup').dialog({
		autoOpen : false,
		modal : true,
		//resizable: false,
		width : 860,
		height : 540,
		title : "View Roster - " + rowData.name,
		open : function(event, ui) {
			var widget = $(this).dialog("widget");
			$('#rosterName', this).html(rowData.name);
			$('#contentArea', this).html(rowData.contentArea);
			
			refreshViewEducatorGrid(rowData);
			refreshViewAssignedStudentGrid(rowData.id);
		},
		close : function(ev, ui) {
		}
	}).dialog('open');
}


function showRosterUploadGrid(){ 

	  var uploadRostergrid = $('#uploadRosterGridTableId');
	  $("#uploadRosterDiv div").show();
	  var colModel = [
	                  {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
	                  {label: 'Date', name: 'date', index: 'date',width:'110px', hidedlg: true, formatoptions: {newformat: 'm/d/Y'},
	                   formatter: function(cellValue, options, rowObject, action){
	                    return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                   }
	                  },
	                  {label: 'Time', name: 'time', index: 'time',width:'110px', hidedlg: true, formatoptions: {newformat: 'h:i:s A'},
	                      formatter: function(cellValue, options, rowObject, action){
	                       return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                      }
	                     },
	                  {label: 'Status', name: 'status', index: 'status',width:'400px',hidedlg: true, formatter: rosterUploadSatusFormatter},
	                  {label: 'File', name: 'filePath', index: 'filePath',width:'120px', hidedlg: true, formatter:extractRosterUploadLinkFormatter},
	                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
	                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
	                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
	                 ];

	 $(uploadRostergrid).jqGrid({
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
	  viewrecords : true,
	  pager: '#uploadRosterGridPager',
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
	 loadRosterUploadData();
	}
	function loadRosterUploadData(){
		var categoryCode= "SCRS_RECORD_TYPE";
	  $.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+categoryCode,
	       type: "POST",
	    }).done(function(data) {
	           //  console.log(data);     
	          $('#uploadRosterGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	          
	       });

	}
	function rosterUploadSatusFormatter(cellValue, options, rowObject){
		if( cellValue == "COMPLETED"){
			cellValue = 'Completed: Records Created/Updated/Removed: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+ rowObject.alertCount;
		}
		return cellValue;
	}
	function extractRosterUploadLinkFormatter(cellValue, options, rowObject){
		var status = rowObject.statusCheck;
			if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
			var dataOrganizationName=rowObject.organizationName;
			var currDate = new Date();
			var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
			var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
			+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
			var file = rowObject.fileName.split(".",1);
			var fileName = file +"_RosterUpload_Errors_"+dateStringForFile+".csv";
			return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
		}
		else
			return '<a> </a>';
	}

$(function(){
	
	$("#Roster_TemplatedownloadquickHelpPopup").hide();
	$("#Roster_TemplatedownloadquickHelpPopupClose").on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#Roster_TemplatedownloadquickHelpPopup").hide();
	});
	$("#Roster_TemplatedownloadquickHelp").on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#Roster_TemplatedownloadquickHelpPopup").show();
		
	});
	
	$('#Roster_TemplatedownloadquickHelp').on('mouseover',function(){
		$(".QuickHelpHint").show();
	});
	$('#Roster_TemplatedownloadquickHelp').on('mouseout',function(){
		$(".QuickHelpHint").hide();
	});
	$('.QuickHelpHint').on('mouseover',function(){
		$(".QuickHelpHint").show();
	});
	$('.QuickHelpHint').on('mouseout',function(){
		$(".QuickHelpHint").hide();
	});
	$('#Roster_RosterTemplatelink_Popup').on('click keypress',function(event){
		if(event.type=='keypress'){
			   if(event.which !=13){
			    return false;
			   }
			  }
		$("#Roster_TemplatedownloadquickHelpPopup").hide();
		
	});
	
});
	
function clearRosterUploadFile(){
	$('#rosterFileData').val("");
	uploadRosterFile.replaceWith( uploadRosterFile = uploadRosterFile.clone( true ) );
	$('#rosterFileDataInput').val("");
}