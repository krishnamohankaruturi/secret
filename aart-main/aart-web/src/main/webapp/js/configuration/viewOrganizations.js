function viewOrganizationsInit(){
	gViewOrganizationLoadOnce = true;
	$('#viewOrgOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});  
	$('#viewOrgFilterForm').validate({ignore: ""});
	buildViewOrgGrid();
	$('#viewOrgButton').on("click",function(event) {
		//event.preventDefault();
		if($('#viewOrgFilterForm').valid()) {
			var $gridAuto = $("#viewOrgGridTableId");
			if($gridAuto[0].grid && $gridAuto[0]['clearToolbar'])
				$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getOrgsToView.htm?q=1', 
				search: false, 
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});	
	
	filteringOrganizationSet($('#viewOrgOrgFilter'));
	
};


/**
 * This method is called when user clicks on the edit button. This method is responsible for displaying the edit user overlay
 * with all the information prepopulated as per existing data for the organization e.g. user details, organization details, role details etc.
 */

function callEditOrganization(dialogTitle, organizationId){
	
	$('#editOrganizationDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1140,
		height: "auto",
		 dialogClass: 'organizationDialogPosition',
		title: escapeHtml(dialogTitle),
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar span").css("width","15% !important;");		    
		},
		close: function(){
		    $(this).html('');
		    var $gridAuto = $("#viewOrgGridTableId");
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url: 'getOrgsToView.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");	
			$('#confirmDialogEditOrg').dialog('destroy').remove();
		}
	}).load('editOrganization.htm', {organizationId: organizationId}).dialog('open');
	$("#ui-dialog-title-editOrganizationDiv").css("width","15%");
	$("#ui-dialog-title-editOrganizationDiv").parent().css("border-bottom","1px solid #ccc");
}


function buildViewOrgGrid() {
	var $gridAuto = $("#viewOrgGridTableId");
	//Unload the grid before each request.
	$("#viewOrgGridTableId").jqGrid('clearGridData');
	$("#viewOrgGridTableId").jqGrid("GridUnload");
	var gridWidthForVO = $('#viewOrgGridTableId').parent().width();		
	if(gridWidthForVO < 740) {
		gridWidthForVO = 740;				
	}
	var cellWidthForVO = gridWidthForVO/5;
	
	var cmforViewOrgGrid = [
        { label: 'Id', name : 'id', index : 'id', width : cellWidthForVO, search : true, hidden: true, hidedlg: false },
		{ label: 'Organization', name : 'displayIdentifier', index : 'displayIdentifier', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
        	formatter: escapeHtml
        },
		{ label: 'Name', name : 'organizationName', index : 'organizationName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
        	formatter: escapeHtml},
		{ label: 'Level', name : 'typeCode', index : 'typeCode', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},
		{ label: 'Org Parent', name : 'parentOrgDisplayName', index : 'parentOrgDisplayName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: escapeHtml},
		{ label: 'Org Top Level', name : 'parentOrgTypeCode', index : 'parentOrgTypeCode', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},
		{ label: 'Contracting Organization', name : 'contractingOrganization', index : 'contractingOrganization', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: escapeHtml, stype : 'select', searchoptions: { value : ':All;T:Yes;F:No;-1:Not Available', sopt:['eq'] }},
		{ label: 'Start Date', name : 'schoolStartDate', index : 'schoolStartDate', width : cellWidthForVO, sorttype : 'date', search : true, hidden : false, hidedlg : false,  formatter: dateFormatter},
		{ label: 'End Date', name : 'schoolEndDate', index : 'schoolEndDate', width : cellWidthForVO, sorttype : 'date', search : true, hidden : false, hidedlg : false,  formatter: dateFormatter},
		{ label: 'Parent Organization Name', name : 'parentOrganizationName', index : 'parentOrganizationName', width : cellWidthForVO+50, search : true, hidden : true, hidedlg : false },
		{ label: 'Assessment Program', name : 'assessmentProgram', index : 'assessmentProgram', width : cellWidthForVO, search : true, hidden : true, hidedlg : false },
		{ label: 'Testing Model', name : 'testingModelName', index : 'testingModelName', width : cellWidthForVO, search : true, hidden : true, hidedlg : false }, 
		{ label: ' Organization', name : 'sourceorgdisplayidentifier', index : 'sourceorgdisplayidentifier', width : cellWidthForVO, search : true, hidden : false, hidedlg : false },
		{ label: 'Testing Begin Time', name : 'testBeginTime', index : 'testBeginTime', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},
		{ label: 'Testing End Time', name : 'testEndTime', index : 'testEndTime', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},
		{ label: 'Testing Days', name : 'testDays', index : 'testDays', width : cellWidthForVO, search : true, hidden : false, hidedlg : false}
		];
	function dateFormatter(cellval, opts) {  
		if(cellval != 'Not Available'){		     
	        return formatDate(cellval);
        }else{
     	   return cellval;
        }
    	 
  	};
  	
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
		colNames : [ 'Id', 'Organization', 'Name', 'Level', 'Org Parent', 'Org Top Level', 'Contracting Organization', 'Start Date',
		             'End Date' , 'Parent Organization Name' , 'Assessment Program' , 'Testing Model', 'Merged Schools' ,
		             'Testing Begin Time', 'Testing End Time', 'Testing Days'/*'Password Expire', 'Expiration Date'*/],
	  	colModel :cmforViewOrgGrid,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewOrgGridPager',
		sortname: 'parentOrgDisplayName,organizationName',
		sortorder: 'asc',
		loadonce: false,
		viewable : false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    beforeRequest: function() {
	    	if(!$('#viewOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
	        
	        if($('#viewOrgOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#viewOrgOrgFilter').orgFilter('value'));
	        	$(this).setGridParam({postData: {'orgChildrenIds': orgs}});
	        } else if($(this).getGridParam('datatype') == 'json') {
        		return false;
	        }
	    },
	    loadComplete:function(){
	    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
	    	var tableid=$(this).attr('id');	    	 
	    	var objs= $( '#gbox_'+tableid).find('[id^=gs_]');;
	    	$.each(objs, function(index, value) {
	    	  var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));
           	 $(value).attr('title',$(nm).text()+' filter');
             if ( $(value).is('select')) {
          	   $(value).removeAttr("role");
          	                 };    
              			    });
	   	 $('.select2-hidden-accessible').removeAttr("aria-hidden");
	    	}
	});
   	
	if (typeof(editPermission) !== 'undefined' && editPermission){
		$gridAuto.jqGrid('navButtonAdd', 'viewOrgGridPager', {
					id: 'editOrganization',
					caption: "",
					title: "Edit Selected Organization",
					buttonicon: "ui-icon-pencil",
					onClickButton: function(a) {
							var $this = $(this);
							var selectedRowId = $this.jqGrid('getGridParam', 'selrow');
							if (selectedRowId == null){
								$.jgrid.viewModal('#alertmod_viewOrgGridTableId',
									{gbox: '#gbox_' + $.jgrid.jqID(this.p.id), jqm: true});
								$('#jqg_alrt_viewOrgGridTableId').focus();
								return;
							}				
							var dialogTitle = 'Edit Organization';
							var contractingOrgId = $this.jqGrid('getCell', selectedRowId, 'contractingOrganization');
							if(contractingOrgId != "Yes"){
							callEditOrganization(dialogTitle, selectedRowId);
							}else{
								$('#editOrgErrorMsg').show();
								setTimeout(function(){
									$('#editOrgErrorMsg').hide();
								}, 5000);
							}
					}
		 	
		 	});

		}  
	$gridAuto.jqGrid('navButtonAdd', 'viewOrgGridPager', {
		id: 'viewOrganization',
		caption: "",
		title: "View selected row",
		buttonicon: "ui-icon-document",
		onClickButton: function(a) {
			var $this = $(this);
			var selectedRowId = $this.jqGrid('getGridParam', 'selrow');
			var organizationName = $this.jqGrid ('getCell', selectedRowId , 'organizationName');
			if (selectedRowId == null){
				$.jgrid.viewModal('#alertmod_viewOrgGridTableId',
					{gbox: '#gbox_' + $.jgrid.jqID(this.p.id), jqm: true});
				$('#jqg_alrt_viewOrgGridTableId').focus();
				return;
			}				
			var dialogTitle = 'View Organization:';
			callViewOrganization(dialogTitle, organizationName, selectedRowId);
		}
	
	});
	
	// Manage Organization
	
	if (typeof(managePermission) !== 'undefined' && managePermission){
	$gridAuto.jqGrid('navButtonAdd', 'viewOrgGridPager', {
			id: 'manageOrganization',
			caption: "",
			title: "Manage Organization",
			buttonicon: 'manageIcon',
			onClickButton: function(a) {
				var $this = $(this);
				var selectedRowId = $this.jqGrid('getGridParam', 'selrow');
				if (selectedRowId == null){
					$.jgrid.viewModal('#alertmod_viewOrgGridTableId',
						{gbox: '#gbox_' + $.jgrid.jqID(this.p.id), jqm: true});
					$('#jqg_alrt_viewOrgGridTableId').focus();
					return;
				}	
				var contractingOrgId = $this.jqGrid('getCell', selectedRowId, 'contractingOrganization');
				
				var dialogTitle = 'Edit Contracting Organization: '+$('#viewOrgGridTableId').jqGrid ('getCell', selectedRowId, 'organizationName');
				if(contractingOrgId == "Yes"){
					callManageOrganization(dialogTitle,selectedRowId);
				} else {
					$('#manageOrgErrorMsg').show();
					setTimeout(function(){
						$('#manageOrgErrorMsg').hide();
					}, 5000);
				} 
				
			}
		});
	}
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
	
	
};


function callViewOrganization(dialogTitle, organizationName, organizationId){
	
	$('#viewOrganizationDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1140,
		height: "auto",
		 dialogClass: 'organizationDialogPosition',
		title: escapeHtml(dialogTitle),
		create: function(event, ui){
		    var widget = $(this).dialog("widget");		   
		    $(".ui-dialog-titlebar span").css("width","100% !important;");
		    $(".ui-dialog-titlebar span").css("text-align","center;");
		},
		close: function(){
		    $(this).html('');
		    $("#viewOrgGridTableId").jqGrid('setGridParam',{
				datatype:"json", 
				url: 'getOrgsToView.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");			
		}
	}).load('viewOrganization.htm', {organizationId: organizationId}).dialog('open');	
	$("#ui-dialog-title-viewOrganizationDiv").css("width","100%");	
	$("#ui-dialog-title-viewOrganizationDiv").html('');
	
	$("#ui-dialog-title-viewOrganizationDiv").append('<div class="viewOrgDivtable"><div class="viewOrgDivtr">'+
	'<div class="viewOrgd1">'+dialogTitle+'</div><div class="viewOrgd2">'+organizationName+'</div><div class="viewOrgd3"></div></div></div>');
}

// Manage Organization
function callManageOrganization(dialogTitle,organizationId){
	$('#manageOrganizationDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1140,		
		height: "auto",	
		 dialogClass: 'organizationDialogPosition',
		title: escapeHtml(dialogTitle),
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar span").css("width","15% !important;");		    
		},
		close: function(){
			 $(this).html('');
			    var $gridAuto = $("#viewOrgGridTableId");
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url: 'getOrgsToView.htm?q=1', 
					search: false, 
				}).trigger("reloadGrid");				
		}
	}).load('manageOrganization.htm', {organizationId: organizationId}).dialog('open');
}
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [month, day, year].join('/');
}

