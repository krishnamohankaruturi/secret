var selectedUserArray = [];
function view_EditInternalUsers_tab() {
	
	$('#viewInternalUserOrgFilter').orgFilter({
		containerClass: '',
		requiredLevels: [20]
	});

	$('#viewInternalUserOrgFilterForm').validate({ignore: ""});
	
	buildViewOrgGrid();
	selectedUserArray = [];
	
	$('#viewInternalUserButton').on("click",function(event) {
		selectedUserArray = [];
		if($('#viewInternalUserOrgFilterForm').valid()) {
			var $gridAuto = $("#viewInternalUserGridTableId");
			$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getInternalUsersByOrganization.htm?q=1', 
				search: false, 
				postData: { "filters": "",
			        "requestFor":"view"}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});	 
	filteringOrganizationSet($('#viewInternalUserOrgFilterForm'));
}

function buildViewOrgGrid() {
	var $gridAuto = $("#viewInternalUserGridTableId");
	
	selectedUserArray = [];
	//Unload the grid before each request.
	$("#viewInternalUserGridTableId").jqGrid('clearGridData');
	$("#viewInternalUserGridTableId").jqGrid("GridUnload");
	
	var gridWidthForVO = $gridAuto.parent().width();		
	if(gridWidthForVO < 740) {
		gridWidthForVO = 740;				
	}
	var cellWidthForVO = gridWidthForVO/4;
	

	var cmforViewUserGrid = [
		{ name : 'id', index : 'id', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
		
		{ name : 'firstName', index : 'firstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,formatter: userFirstNameFormatter
		},
		{ name : 'surName', index : 'surName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: userLastNameFormatter
		},				
		{ name : 'email', index : 'email', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,formatter: userEmailFormatter
		},
		{ name : 'internaluserindicator', index : 'internaluserindicator', width : cellWidthForVO, search : false, hidden : false, hidedlg : false,
			formatter: internalUserFormatter
		}
	];
	
	
	//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
		colNames: ["User Id",       // Changed During US16242
				   "First Name",
				   "Last Name",					   
				   "Email",
				   "Internal User"],
	  	colModel :cmforViewUserGrid,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewInternalUserGridPager',
		viewrecords : true,
		multiselect: true,
		page: 1,
	    sortname: 'firstName',
	    sortorder: 'desc',
		loadonce: false,
		viewable: false,
	    beforeSelectRow: function(rowid, e) {
	    	selectedUserArray = [];
	    	return true;
	    },
	    onSelectRow: function(rowid, status, e){
	    	var rData = $('#viewInternalUserGridTableId').jqGrid(
					'getRowData', rowid);
	    	var userObj = {
	    			id : rData.id,
	    			firstName : rData.firstName,
	    			surName : rData.surName,
	    			email : rData.email,
	    			internaluserindicator : rData.internaluserindicator
				};
	    	selectedUserArray.push(userObj);
     	   if(status){
     		  var grid = $(this);
     		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
     		   if(cbsdis.is(":disabled")){
     			  cbsdis.prop('checked', false);         			   
     		   }
     	   } else{
     		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
     		   //do nothing.
     	   }               	      
     	},
	    onSelectAll: function(aRowids,status) {
	    	var userObj = {
	    			id : rData.id,
	    			firstName : rData.firstName,
	    			surName : rData.surName,
	    			email : rData.email,
	    			internaluserindicator : rData.internaluserindicator
				};
	    	selectedUserArray.push(userObj);
	        if (status) {
	        	var grid = $(this);
	            // uncheck "protected" rows
	            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
	            cbs.removeAttr("checked");
	            var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
			        if($("#jqg_viewInternalUserGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
			        	grid.jqGrid('setSelection', allRowsIds[i], false);
			        }
			    }	
	        }
	    },
	    beforeRequest: function() {
	    	if(!$('#viewInternalUserOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
	    		return false;
	    	}
			
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	        	$(this).setGridParam({postData: {page : lastPage}});
	        }
	        
	        if($('#viewInternalUserOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#viewInternalUserOrgFilter').orgFilter('value'));
	        	$(this).setGridParam({postData: {
	        		'orgChildrenIds': function() {return orgs;}}
	        	});
	        } else if($(this).getGridParam('datatype') == 'json') {
	        		return false;
	        }
	        $("#viewInternalUserGridTableId").jqGrid('clearGridData');
	    },  
	    loadComplete: function (data) {
	    	var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'surName')+ ' Check Box');
	            }
	            $('input').removeAttr("aria-checked");
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	             $('#cb_'+tableid).attr('title','User Grid All Check Box');
		             $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');                          
		                    });
        },
	});
	
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
	
	$gridAuto.jqGrid('navGrid', '#viewInternalUserGridPager', {edit: false, add: false, del: false, search: false, refresh: false})
    .jqGrid('navButtonAdd','#viewInternalUserGridPager', {
    	caption: "",
    	title: "Modify User",
    	buttonicon: "ui-icon-pencil",
    	onClickButton: function() {
    		openUserPopup();
    	}
    });
}

function openUserPopup(){
	var id = $('#viewInternalUserGridTableId').jqGrid ('getGridParam','selrow');
	var selectRowLength = $('#viewInternalUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
	//var internalUserFlag =selectedUserArray[0].internaluserindicator;
	
	if(selectRowLength == 1){
		
		$('#viewinternalUsers').dialog({
			autoOpen: false,
			modal: true,
			//resizable: false,
			width: 600,
			height: 400,
			title: "Modify User",
			create: function(event, ui) { 
				var widget = $(this).dialog("widget");
			    $(".ui-dialog-title", widget).css('font-size', '1em');
			    $('#internalUserId').html(' '+selectedUserArray[0].id);
				$('#firstName').html(' '+selectedUserArray[0].firstName);
				$('#lastName').html(' '+selectedUserArray[0].surName);
				$('#email').html(' '+selectedUserArray[0].email);
				if(selectedUserArray[0].internaluserindicator=="true"){
					$("#internalUser").prop("checked", true);
				}else{
					$("#internalUser").prop("checked", false);
				}
			},
			open : function(ev, ui) {
				$('#internalUserId').html(' '+selectedUserArray[0].id);
				$('#firstName').html(' '+selectedUserArray[0].firstName);
				$('#lastName').html(' '+selectedUserArray[0].surName);
				$('#email').html(' '+selectedUserArray[0].email);
				if((selectedUserArray[0].internaluserindicator)=="true"){
					$("#internalUser").prop("checked", true);
				}else{
					$("#internalUser").prop("checked", false);
				}
			},
			buttons : {
				"OK" : function() {
					saveInternalUser(id,selectedUserArray[0].internaluserindicator);
				},
				Cancel : function() {
					selectedUserArray = [];
					$(this).dialog("close");
				}
			},
			close: function(ev, ui) {
				selectedUserArray = [];
				$('#viewInternalUserGridTableId').trigger('reloadGrid');
			}
		}).dialog('open');
	}else {
		$(".full_main #messageViewInternalUsers").html('<span class="error_message ui-state-highlight">Please select only one user.</span>').show();
	}
}

function saveInternalUser(id,internalUserFlag){
	var isInternalUserFlag = $('#internalUser').attr('checked');
	
	if(isInternalUserFlag === undefined){
		isInternalUserFlag = "false";
	}else{
		isInternalUserFlag = "true";
	}
	if(internalUserFlag !=isInternalUserFlag){
		$.ajax({
			url : 'getInternalUserDetails.htm',
			dataType: 'json',
			type: "POST",
			data:{
				userId:id,
				isInternalUserFlag:isInternalUserFlag
			}
		}).done(function(data) {
			if (data.success) {
				$('#editUserDetailsSucess').text(data.success);
				$('#editUserDetailsSucess').show();
				setTimeout("$('#editUserDetailsSucess').hide()", 3000);
			}else {
				$('#editUserDetailsError').text(data.error);
				$('#editUserDetailsError').show();
				setTimeout("$('#editUserDetailsError').hide()", 3000);
			}
		 });
	}else{
		$('#editUserDetailsSucess').text('Successfully Updated User');
		$('#editUserDetailsSucess').show();
		setTimeout("$('#editUserDetailsSucess').hide()", 3000);
	}
}

function userFirstNameFormatter(cellValue, options, rowObject){
	return rowObject[3];
} 

function userLastNameFormatter(cellValue, options, rowObject){
	return rowObject[4];
}

function userEmailFormatter(cellValue, options, rowObject){
	return rowObject[5];
} 

function internalUserFormatter(cellValue, options, rowObject){
	return rowObject[9];
}