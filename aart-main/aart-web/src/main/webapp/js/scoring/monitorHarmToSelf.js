
function scoringMonitorHarmToSelfInit(){
	loadgriddata();
	
	//Tiny Text changes
	initTinytextChanges($("#monitorHalfToSelfUserAccesslevel").val());
}

function loadgriddata(){
	
	var grade="Grade";
	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	if(userDefaultAssessmentProgram!=null && userDefaultAssessmentProgram!=undefined && userDefaultAssessmentProgram!=""){
		if(userDefaultAssessmentProgram=='CPASS') {
			grade="Pathway";
		}
	}
	
	var $gridAuto = $("#monitorselftoharmtable");
	$("#monitorselftoharmtable").jqGrid('clearGridData');
	$("#monitorselftoharmtable").jqGrid("GridUnload");
	var gridWidthForVO = 893;
	
	if ($gridAuto[0].grid && $gridAuto[0]['clearToolbar']) {
		$gridAuto[0].clearToolbar();
	}
	var colModel = [
			{
				name : 'gridRowId',
				index : 'gridRowId',
				key:true,
				hidden : true,
				hidedlg : true,
				sortable : false
			},
			{
				name : 'legalFirstName',
				index : 'legalFirstName',
				align : 'center',
				search:true,
				sorttype : 'text'
			},
			{
				name : 'districtName',
				index : 'districtName',
				align : 'center',
				search:true,
				sorttype : 'text'
			},
			{
				name : 'schoolName',
				index : 'schoolName',
				align : 'center',
				search:true,
				sorttype : 'text'
			},
			{
				name : 'name',
				index : 'name',
				align : 'center',
				search:false,
				sorttype : 'text'
			}, {
				name : 'testName',
				index : 'testName',
				search:true,
				sorttype : 'text'
			},
			 {
				name : 'item',
				index : 'item',
				search:false,
				sorttype : 'int'
			},
			{
				name : 'scorer',
				index : 'scorer',
				align : 'center',
				search:true,
				sorttype : 'text'
			}
	]
	
	$("#monitorselftoharmtable").scb(
			{
				url : "getMonitorHarmToSelfDetails.htm",
				mtype : "POST",
				datatype : "json",
				width : gridWidthForVO,
				rowNum : 5,
				rowList : [ 5, 10, 20, 30, 40, 60, 90 ],
				colNames : [ '','Student', 'District','School', grade, 'Test','Item','Scorer'],
				colModel : colModel,
				search: false, 
				sortname : 'legalFirstName',
				sortorder : 'asc',
				loadonce : false,
				viewable : false,
				columnChooser : false,
				pager : '#monitorSelfToHarmPager',
				jsonReader : {
					repeatitems : false,
					page : function(obj) {
						return obj.page !== undefined ? obj.page : "0";
					},
					root : function(obj) {
						return obj.rows;
					}
				},
				beforeRequest : function() {
					var currentPage = $(this).getGridParam('page');
					var lastPage = $(this).getGridParam('lastpage');

					if (lastPage != 0 && currentPage > lastPage) {
						$(this).setGridParam('page', lastPage);
						$(this).setGridParam({
							postData : {
								page : lastPage
							}
						});
					}
					
				},
				   loadComplete:function(data){
					   var tableid=$(this).attr('id'); 		           
			            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			             
			             $.each(objs, function(index, value) {         
			              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
			                    $(value).attr('title',$(nm).text()+' filter');                          
			                    }); 
				   }
			});
	$gridAuto.jqGrid('setGridParam', {
		postData : {
			"filters" : ""
			
		}
	}).trigger("reloadGrid", [ {
		page : 1
	} ]);
	$('#refresh_monitorselftoharmtable').hide();
}

