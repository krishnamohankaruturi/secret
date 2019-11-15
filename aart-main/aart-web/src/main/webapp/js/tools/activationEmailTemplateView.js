$(function(){
	viewActivationEmailInit();
});

function templateSearch(){
if($("#viewActivationEmailGridTableId")!=null)	
	buildViewActivationEmailGrid();
	
	if($('#editActivationEmailForm').valid()) {
				
		var $gridAuto = $("#viewActivationEmailGridTableId");
		if($gridAuto[0].grid && $gridAuto[0]['clearToolbar']){
			$gridAuto[0].clearToolbar();
		}
		$gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getEmailActivationToView.htm?q=1', 
			search: false, 
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
		
	}
	
}


function viewActivationEmailInit(){
	try {
		$('#editActivationEmailForm').validate({ignore: ""});
		if($("#viewActivationEmailGridTableId")!=null)
			buildViewActivationEmailGrid();
		
		if($('#editActivationEmailForm').valid()) {
					
			var $gridAuto = $("#viewActivationEmailGridTableId");
			if($gridAuto[0].grid && $gridAuto[0]['clearToolbar']){
				$gridAuto[0].clearToolbar();
			}
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getEmailActivationToView.htm?q=1', 
				search: false, 
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
			
		}
		
		$("#editdefaultemail").on("click",function(){
			var dialogTitle = "EDIT EMAIL TEMPLATE";
			createDefaultEmailTemplate(dialogTitle);			 
		});		
		
		$("#createcustomemail").on("click",function(){
			var dialogTitle = "CREATE EMAIL TEMPLATE";
			createCustomEmailTemplate(dialogTitle,0);			 
		});	
	}catch (e){
		console.log(e);
	}
};

function createDefaultEmailTemplate(dialogTitle){
	$('#editActivationEmailDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1140,
		height: "auto",
		minHeight: 'auto',
		position: { my: "center", at: "top"},
		title: escapeHtml(dialogTitle),
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar span").css("width","15% !important;");		    
		},
		close: function(){
//			$("#editActivationEmailDiv").dialog().dialog('close');
			$("#editActivationEmailDiv").dialog('close');
			$(".rightNoButton").trigger('click');
		    $(this).html('');
		    var $gridAuto = $("#viewActivationEmailGridTableId");
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url: 'getEmailActivationToView.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");		
		}
	}).load('createEmailTemplate.htm', {isDefault:true,templateId : 0}).dialog('open');
	$("#ui-dialog-title-editActivationEmailDiv").css("width","22%");
	$("#ui-dialog-title-editActivationEmailDiv").parent().css("border-bottom","1px solid #ccc");
}

function createCustomEmailTemplate(dialogTitle,templateId){
	$('#editActivationEmailDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1140,
		height: "auto",
		minHeight: 'auto',
		position: { my: "center", at: "top"},
		title: escapeHtml(dialogTitle),
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar span").css("width","15% !important;");		    
		},
		close: function(){
		    $(this).html('');
		    $("#confirmDialogStatesMove").dialog('close');
		    var $gridAuto = $("#viewActivationEmailGridTableId");
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url: 'getEmailActivationToView.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");		
		}
	}).load('createEmailTemplate.htm', {isDefault:false,templateId : templateId}).dialog('open');
	$("#ui-dialog-title-editActivationEmailDiv").css("width","24%");
	$("#ui-dialog-title-editActivationEmailDiv").parent().css("border-bottom","1px solid #ccc");	
}

function buildViewActivationEmailGrid() {
	var $gridAuto = $("#viewActivationEmailGridTableId");
	//Unload the grid before each request.
	$("#viewActivationEmailGridTableId").jqGrid('clearGridData');
	
	$("#viewActivationEmailGridTableId").jqGrid("GridUnload");
	var gridWidthForVO = $('#viewActivationEmailGridTableId').parent().width();		
	if(gridWidthForVO < 707) {
		gridWidthForVO = 980;				
	}
	//$.jgrid.defaults.styleUI = 'Bootstrap';
	var cellWidthForVO = gridWidthForVO/5;
	
	var cmforViewActivationEmailGrid = [
        { label: 'Assessment Program', name : 'assessmentProgramName', index : 'assessmentProgramName', width : cellWidthForVO-40, search : true, hidden: false, hidedlg: false ,formatter: leftFontFormat, key:true},
		{ label: 'State(s)', name : 'states', index : 'states', width : cellWidthForVO+160, search : true, sortable:false, hidden: false, hidedlg: false,formatter: fontStyleFormat, key:true},
		{ label: 'Default Template?', name : 'defaultTemplate', index : 'defaultTemplate', width : cellWidthForVO-60, search : true, hidden : false, hidedlg : false,
		stype : 'select',searchoptions: { value : ':All;Yes:Yes;No:No', sopt:['eq'] }, key:true},
		{ label: 'Template Name', name : 'templateName', index : 'templateName', width : cellWidthForVO+60, search : true, hidden : false, hidedlg : false, key:true},
		{ label: 'Action', name : 'action', index : 'action', width : cellWidthForVO-40, search : false, hidden : false, sortable:false, hidedlg : false,formatter: editTemplateLinkFormatter , key:true}
	];

	function leftFontFormat(cellvalue, options, rowObject) {
		 var cellHtml = "<div style='text-align:left;margin: 5px;'>" + cellvalue + "</div>";
		 return cellHtml;
	}
	
	function fontStyleFormat(cellvalue, options, rowObject) {
		 var cellHtml = "<div style='line-height: 20px;text-align:left;margin: 5px;' originalValue='" + cellvalue + "'>" + cellvalue + "</div>";
		 return cellHtml;
	}
	

	function editTemplateLinkFormatter(cellvalue, options, rowObject) {
		var isDefault = rowObject[2];
		var templateId = rowObject[4];
		var url="";
		if(isDefault=="No"){
			if(templateId!=null && templateId!=undefined && templateId!==""){
				url = '<a class="editLink" href="javascript:createCustomEmailTemplate(\'EDIT EMAIL TEMPLATE\',\''  + escapeHtml(templateId) + '\');">Edit</a>';
			}			
		}
		else{
			url = 'n/a';
		}
		
		
		return url;
	}
	
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
		colNames : [ 'Assessment <br>Program', 'State(s)', 'Default <br>Template?', 'Template Name', 'Action'],
	  	colModel :cmforViewActivationEmailGrid,
		rowNum : 5,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#viewActivationEmailGridPager',
		sortname : 'assessmentProgramName',
		loadonce: false,		
		columnChooser: false,
		viewable: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    beforeRequest: function() {
	    	if(!$('#editActivationEmailForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
	        
	        if($('#viewRosterOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#viewRosterOrgFilter').orgFilter('value'));
	        	$(this).setGridParam({postData: {'orgChildrenIds': function() {
						return orgs;
					}}});
	        } else if($(this).getGridParam('datatype') == 'json') {
        		return false;
	        }
	        
	      
	    },
	    loadComplete:function(){
	    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
	    	 var tableid=$(this).attr('id'); 			           
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			           
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');                          
	                    });
	             var obj= $('#'+tableid).find("tr");	
	             var i=0;
	             $.each(obj, function(index, value) {
	                    $(value).attr('id',i++);                          
	                    });
	      }
	});
//  	if($('#first_viewActivationEmailGridPager')!=null)
//  		$('#first_viewActivationEmailGridPager').addClass('_padGridPager');
  	
 
  		
	
  		
};
