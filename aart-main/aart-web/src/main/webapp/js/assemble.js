var newArray = [];	
function newOperationalTestWindowGridInitMethod(){
	
	  $(function() {
		    $( "#sortable1, #sortable2" ).sortable({
		      connectWith: ".connectedSortable"
		    }).disableSelection();
		  });
	  
	  $('#sortable1').mouseover(function(event) {
		    createTooltip(event);               
		}).mouseout(function(){
		    // create a hidefunction on the callback if you want
		   // hideTooltip(); 
		});

		function createTooltip(event){          
		    //$('<div class="tooltip">test</div>').appendTo('body');
		    positionTooltip(event);
		    //console.log($(".table-data-section").find('li'));
		};
		
//		function hideTooltip(){
//			$('body').find();
//		}
		
		function positionTooltip(event){
		    var tPosX = event.pageX - 10;
		    var tPosY = event.pageY - 10;
		    $('div.tooltip').css({'position': 'absolute', 'top': tPosY, 'left': tPosX});
		};
//	var $gridAuto = $("#selectTestGridTableId");
//
//$gridAuto.scb({
//	 	datatype: "local",
//		rowNum: 100000,
// 	rows: 100000,
////	rowList:[10,20,30,100000000],
// 	colModel:[
//	 	      {label: 'Test Name', name: 'testName', index: 'testCollectionId',width:100,sortable : true,key:true,search:false,hidedlg: true},
//          {label: 'Test Description', name: 'testDescription',index:'name', width:270,sortable : true,hidedlg: true,search:false}             
//         ],
//             loadonce : true,
//	 	          viewrecords: true,
//	 	          scroll:false,
//	 	          //pager : '#pselectTestGridTableId',
//	 	          multiselect:true,
//	 	          shrinkToFit: false,
//	 	          sortname : 'name',
//	 	          rowList: [],        // disable page size dropdown
//	 	          pgbuttons: false,     // disable page control like next, back button
//	 	          pgtext: null,         // disable pager text like 'Page 0 of 10'
//	 	          viewrecords: false,
//	 	          ignoreCase: true,
//	 	          loadComplete: function () {
//		 	        	gridName = this.id;
//		 	        	reSizeTestWindowGridBasedOnContent(gridName);
//	 	            }
// 	        });
//$gridAuto.jqGrid('setGridWidth', '350');
//$gridAuto.jqGrid('setGridHeight', '200');
//$('div[id$=selectTestGridTableId] div:contains("View Test Session Detail"):last').html('<div></div');
//
//$("#SelectedCollectionId").scb({
//	datatype: "local",
//	rowNum: 100000,
// 	rows: 100000,
////	rowList:[10,20,30,100000000],
//// 	colNames:['Collection ID','Test Collection Name'],
//  	colModel:[      
//   		{label: 'Test Name', name:'testName', index:'testCollectionId', width:100, sortable: true, hidedlg: true,search:false, key:true},
//   		{label: 'Test Description', name:'testDescription', index:'name', width:270, sortable: true,hidedlg: true,search:false,key: true}
//   	],
//   loadonce : true,
//   viewrecords: true,
//   scroll:false,
//   //pager : '#pSelectedCollectionId',
//   multiselect:true,
//   ignoreCase: true,
//   shrinkToFit:false,
//   rowList: [],        // disable page size dropdown
//  pgbuttons: false,     // disable page control like next, back button
//  pgtext: null,         // disable pager text like 'Page 0 of 10'
//  viewrecords: false,
//  loadComplete: function () {
//	  		gridName = this.id;
//	  		reSizeTestWindowGridBasedOnContent(gridName);
//	  }
//});
//
//$("#SelectedCollectionId").jqGrid('setGridWidth', '350');
//$("#SelectedCollectionId").jqGrid('setGridHeight', '200');
////$("#SelectedCollectionId").jqGrid('filterToolbar');
//$('div[id$=SelectedCollectionId] div:contains("View Test Session Detail"):last').html('<div></div');
//
//function reSizeTestWindowGridBasedOnContent(gridName){
//	// Test - resize cols based on content
//		
//     $('#parent').append('<span id="widthTest" />');
//
//     $('#gbox_' + gridName + ' .ui-jqgrid-htable,#' + gridName).css('width', '100%');
//     $('#' + gridName).parent().css('width', 'inherit');
//
//     var columnNames = $("#" + gridName).jqGrid('getGridParam', 'colModel');
//     var thisWidth;
//
//     // Loop through Cols
//     for (var itm = 0, itmCount = columnNames.length; itm < itmCount; itm++) {
//
//         var curObj = $('[aria-describedby=' + gridName + '_' + columnNames[itm].name + ']');
//         
//         var thisCell = $('#' + gridName + '_' + columnNames[itm].name + ' div');
//         $('#widthTest').html(thisCell.text()).css({
//                 'font-family': thisCell.css('font-family'),
//                 'font-size': thisCell.css('font-size'),
//                 'font-weight': thisCell.css('font-weight')
//             });
//         var maxWidth = Width = $('#widthTest').width() + 24;                    
//         //var maxWidth = 0;                      
//
//         // Loop through Rows
//         for (var itm2 = 0, itm2Count = curObj.length; itm2 < itm2Count; itm2++) {
//             var thisCell = $(curObj[itm2]);
//
//             $('#widthTest').html(thisCell.html()).css({
//                 'font-family': thisCell.css('font-family'),
//                 'font-size': thisCell.css('font-size'),
//                 'font-weight': thisCell.css('font-weight')
//             });
//
//             thisWidth = $('#widthTest').width();
//             if (thisWidth > maxWidth) maxWidth = thisWidth;                        
//         }                    
//
//         $('#' + gridName + ' .jqgfirstrow td:eq(' + itm + '), #' + gridName + '_' + columnNames[itm].name).width(maxWidth).css('min-width', maxWidth);
//
//     }
//
//     $('#widthTest').remove();
//     
//   
//     
//     $("#AddInoperationalTestcollection").off("click").on('click',function(){
//    	 alert("llll");
//			
//			var rowIds = $("#selectTestGridTableId").jqGrid('getGridParam','selarrrow');
//			
//			var rowIds1 = rowIds;
//			
//			if (rowIds != null && rowIds.length > 0) {
//				
//				for ( var i = 0;  i < rowIds.length; i++) {
//					var rowObj = $("#selectTestGridTableId").jqGrid('getRowData',
//							rowIds[i]);					
//					$("#SelectedCollectionId").addRowData(rowIds[i], rowObj);
//				}	
//				
//				while(rowIds1.length > 0){
//					$("#selectTestGridTableId").delRowData(rowIds1[rowIds1.length-1]);
//				}
//				
//			}
//			$("#SelectedCollectionId").trigger('reloadGrid');
//			$("#selectTestGridTableId").trigger('reloadGrid');
//			$("#cb_selectTestGridTableId").prop('checked',false);
//		});
//
//}
}
$("#movingToNext").click(function() {
	$('#firstPage').hide();
	$('#midPage').show();
	
	$.ajax({
        url: 'interimgrade.htm',
        data: { },
        dataType: 'json',
        type: "GET",
        success:function(grade){
        	console.log(grade);
        	  for(var i=0; i<grade.length; i++){
        		  gradenum = '<li class="ui-state-default">' + grade[i].name + '</li>';
        		  $('#sortable1').append(gradenum);
        		}
        }
	})
	
	//<li class="ui-state-default">Mini Test 1<span>---</span><span1>description of test1...dvfjdvf</span1></li>
	
	  var $gridAuto = $("#selectTestGridTableId");
	 	//Clear the previous error messages
	 		//setTimeout("aart.clearMessages()", 0);
	 	
//	 	if($('#managedbyCodeSelect').val()=='MANUAL_DEFINED_ENROLLMENT_TO_TEST')
//	 	{
//	 		$('.group2').hide();
//	 	} else {
//	 		$('.group2').show();
//	 	}
	 	$('#SelectedCollectionId').jqGrid('clearGridData');
	 	$('#selectTestGridTableId').jqGrid('clearGridData');
	 	var assessmentProgramId = '0';
	 	var organizationTypeId = '0';
	 	var randomizationType = "null";
	 	var categoryCode = "null";
	 	var windowId = 0;
	 	
//	 	 $gridAuto.jqGrid('setGridParam',{
//			 	type: 'POST',
//				datatype:"json", 
//				search: false,
//				url : 'newOperationalTestWindowAPTCDTOList.htm',
//				postData : {
//					filters: null,
//					assessmentProgramId:assessmentProgramId,
//					randomizationType:randomizationType,
//					organizationTypeId:organizationTypeId,
//					categoryCode:categoryCode,
//					windowId :windowId
//				},
//			}).trigger("reloadGrid"); 
	 	
	 	$.ajax({
	        url: 'interimgrade.htm',
	        data: { },
	        dataType: 'json',
	        type: "GET",
	        success:function(grade){
	        	newArray.push(grade);
	        	console.log(newArray);
	        }
		})
 			
 		});

newOperationalTestWindowGridInitMethod();


$("#movingToNext1").click(function() {
	$('#firstPage').hide();
	$('#midPage').hide();
	$('#lastPage').show();
	
});
