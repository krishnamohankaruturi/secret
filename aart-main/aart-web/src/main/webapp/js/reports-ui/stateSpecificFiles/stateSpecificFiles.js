$(function(){
	getLoggedInUserApandStateDetails();
	stateSpecificFilesJQGrid();
});

function postFile() 
{
	 $("#uploadPostFileDialog").dialog({

			width : 400, 
			height : 250,
			autoOpen: false,
			modal:true,
			position:{
		    'my': 'center',
		    'at': 'center'
		},
		open : function(event, ui) {
			$('#postFileData').val('');
			$('#descriptionName').val('');
			$('#uploadPostFileDialog').parent().find('.ui-dialog-titlebar-close').hide();
			$('.ui-dialog').removeAttr("role");
		},
		buttons: {
			Cancel: function(){
				$(this).dialog('close');	
			},
			Submit: function(){
				uploadCustomFiles($(this));
			}
		}

	 }).dialog("open")
}

function getLoggedInUserApandStateDetails(){
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(assessmentPrograms) {
			if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
				$.each(assessmentPrograms, function(i, assessmentProgram) {
					if(assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
						$('#currentUserAssessmentProgram').html(' '+assessmentPrograms[i].programName);
					}
				});
			}
	});

	$.ajax({
		url : 'getStatesOrgsForUser.htm',
		data : {},
		dataType : 'json',
		type : "GET"
	}).done(function(states) {
			if (states !== undefined && states !== null && states.length > 0) {
				$.each(states, function(i, stateOrg) {
					$('#currentOrganization').html(' '+states[i].organizationName);
				});
			}
	});
}

function stateSpecificFilesJQGrid(){
	var $grid= $('#stateSpecificFilesTableId');
	var grid_width =900;
	//var cell_width = (grid_width)/4;
	var cm;
	var columnNames =["File Name","Description","Date Added","Actions"];
	if(hasEditCustomFilePermission){
		cm=[ 
			{name:'fileName', index: 'fileName', sorttype : 'text',formatter: customFileLinkFormatter, search : true, hidden: false,width: '225'},
			{name:'fileDescription', index: 'fileDescription', sorttype : 'text', search : true, hidden: false,width: '225'},		
			{name:'createdDate', sorttype : 'date', search : false, hidden: false,width: '225',
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}
			,{ name: 'actions', sortable: false, hidden : false, hidedlg: true, search: false, width: '225',
			formatter: function(cellValue, options, rowObject){
				return '<span onclick="removeStateSpecificFile('+rowObject.id+')" class="ui-icon ui-icon-trash" title="Remove file"></span>';
			},unformat: function(cellValue, options, rowObject){
				return;
			}
	      	}
			];	
	}else{
		//cell_width = (grid_width)/3;
		columnNames =["File Name","Description","Date Added"];
		cm=[ 
			{name:'fileName', index: 'fileName', sorttype : 'text',formatter: customFileLinkFormatter, search : true, hidden: false,width: '300'},		
			{name:'fileDescription', index: 'fileDescription', sorttype : 'text', search : true, hidden: false,width: '300'},
			{name:'createdDate', sorttype : 'date', search : false, hidden: false,width:'300',
				formatoptions : {
					newformat : 'm/d/Y'
				},
				formatter : function(cellValue, options, rowObject, action) {
					return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options),
							rowObject, action);
				}
			}
			];	
	}

	$grid.scb({
		mtype: "POST",
		datatype : "local",
		//width: grid_width,
		colNames: columnNames,
	  	colModel :cm,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#pstateSpecificFilesTableId',
		sortname: 'fileDescription',
	    sortorder: 'asc',
		loadonce: false,
		viewable: false,
	    beforeRequest: function() {
	    		//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');

	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	        	$(this).setGridParam({postData: {page : lastPage}});
	        }
	        $("#stateSpecificFilesTableId").jqGrid('clearGridData');
	    },jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
		    	root: function(obj) { 
		    		return obj.stateSpecificFilesData;
		    	} 
	    },  
	    loadComplete: function() {
	    		this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
        },beforeSelectRow: function(rowid, e) {
            return false;
        },
        gridComplete: function() {						   
	         var tableid=$(this).attr('id'); 			           
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			           
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');                          
	                    });
		}
	});

	$grid[0].clearToolbar();
	$grid.jqGrid('setGridParam',{
		datatype:"json", 
		url : 'getStateSpecificFileData.htm?q=1', 
		sortname: 'fileDescription',
	    sortorder: 'asc',
		search: false, 
	}).trigger("reloadGrid").trigger('resize');	

	$grid.jqGrid('navGrid', '#pstateSpecificFilesTableId', {edit: false, add: false, del: false});
}

function customFileLinkFormatter(cellValue, options, rowObject){
	return '<a href="downloadStateSpecificFile.htm?stateSpecificFileId=' + rowObject.id  +'" title="Click to download Custom File.">'+rowObject.fileName+'</a>';
}

function uploadCustomFiles(dialog, maxFileSize){
	var fd = new FormData();
	var filedata = $('#postFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	var descriptionName=$('#descriptionName').val();
	if(filelist.length === 0 || descriptionName === null || descriptionName===""){
		if(filelist.length === 0){
			$("#uploadCustomNoFileError").text("File name must be entered.");
			$("#uploadCustomNoFileError").show();
			setTimeout("$('#uploadCustomNoFileError').hide()", 5000);
		}
		if(descriptionName===""){
			$("#uploadCustomFileDescriptionError").text("File description must be entered.");
			$("#uploadCustomFileDescriptionError").show();
			setTimeout("$('#uploadCustomFileDescriptionError').hide()", 5000);
		}
		return false;
	} else {
		var maxFileSize = $('#stateSpecificFileMaxSize').val();
		if(file.size > maxFileSize*1024*1024){
			$("#uploadCustomNoFileError").text("File size must be less than 20MB");
			$("#uploadCustomNoFileError").show();
			setTimeout("$('#uploadCustomNoFileError').hide()", 5000);
		} else {
			var assessmentProgramId=$('#hiddenCurrentAssessmentProgramId').val();
			fd.append('assessmentProgramId',assessmentProgramId);
			var stateId=$('#hiddenCurrentOrganizationId').val();
			fd.append('stateId',stateId);
			fd.append('uploadCustomFile',file);
			fd.append('descriptionName',descriptionName);

			$.ajax({
				url: 'uploadCustomFilesData.htm',
				data: fd,
				dataType: 'json',
				processData: false,
				contentType: false,
				cache: false,
				type: 'POST'
			}).done(function(data){
					if (data.success) {
						$('#uploadCustomFileSucess').text(data.success);
						$('#uploadCustomFileSucess').show();
						setTimeout("$('#uploadCustomFileSucess').hide()", 5000);
					}else {
						$('#uploadCustomFileError').text(data.error);
						$('#uploadCustomFileError').show();
						setTimeout("$('uploadCustomFileError').hide()", 5000);
					}
					$('#stateSpecificFilesTableId').trigger('reloadGrid');
			});
			dialog.dialog('close');
		}
	}
}

function removeStateSpecificFile(stateSpecificFileId){
	$('#removeStateSpecificFileDialog').dialog({
		autoOpen : false,
		modal : true,
		width : 400,
		height : 150,
	    open : function(event, ui) {
	    	$('#removeStateSpecificFileDialog').parent().find('.ui-dialog-titlebar-close').hide();
		},
		buttons : {
			"Confirm" : function(event, ui) {
				if(stateSpecificFileId!=null && stateSpecificFileId!=''){
					$.ajax({
						url: 'removeStateSpecificFile.htm',
						data: {stateSpecificFileId:stateSpecificFileId},
						dataType: 'json',
						type: 'POST'	 
					}).done(function(data){
							if (data.successMessage) {
								$('#removeCustomFileSucess').text(data.successMessage);
								$('#removeCustomFileSucess').show();
								setTimeout("$('#removeCustomFileSucess').hide()", 5000);
							}else {
								$('#removeCustomFileError').text(data.error);
								$('#removeCustomFileError').show();
								setTimeout("$('#removeCustomFileError').hide()", 5000);
							}
							$('#stateSpecificFilesTableId').trigger('reloadGrid');
					});
				}
				$(this).dialog("close");				
			},
			"Cancel" : function(ev, ui) {
				$(this).dialog("close");
			}
		},
		close : function(ev, ui) {
			$(this).dialog('close');
		}
	}).dialog('open');

} 